package fairy.core.managers.transaction;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fairy.core.net.communicator.Linker;
import fairy.core.utils.Debugger;
import fairy.core.utils.Network;
import fairy.valueobject.managers.block.Block;
import fairy.valueobject.managers.transaction.Transaction;

public class TransactionManager extends Thread 
{
	private Queue<Transaction> transactionQueue = null;
	
	private static TransactionManager instance = null;
	
	public static final int MAX_SIZE = 5;
	
	private TransactionManager()
	{
		try {
			transactionQueue = new LinkedList<Transaction>();

			this.start();
		}catch(Exception e) {
			Debugger.Log(this, e);
		}
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			try {
				
				// 블록 생성 주기는 5분
				Thread.sleep(30000);
				
				synchronized(this) {
					// Transaction Queue를 가지고 Block을 생성
					
					List<Transaction> txlist = new ArrayList<Transaction>();
					
					while(!transactionQueue.isEmpty())
					{
						txlist.add(transactionQueue.poll());
					}
					
					Block block = new Block(Network.getLocalIP(), txlist);
					
					if(block.Create())
					{
						// 블록 생성 성공 시 전파
						Linker.getInstance().broadcastingBlock(block);
						
						// 만약 보상을 지급하려고 하면 if를 넣어 생성 성공시 지급하면 됨
					}
				}
				
			}catch(Exception e) {
				Debugger.Log(this, e);
			}
		}
	}
	
	public static TransactionManager getInstance() {
		if(instance == null) {
			instance = new TransactionManager();
		}
		return instance;	
	}
	
	public boolean Push(Transaction tx, PublicKey key)
	{
		try {
			if(Verify(tx, key))
			{
				return transactionQueue.add(tx);
			}
			else return false;
			
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
	
	public byte[] Sign(byte[] data, PrivateKey key) {
		try {
			Signature dsa = Signature.getInstance("SHA1withECDSA");

	        dsa.initSign(key);

	        dsa.update(data);

	        return dsa.sign();
		}catch(Exception e) {
			return null;
		}
	}
	
	private boolean Verify(Transaction tx, PublicKey key) {
		try {
			Signature dsa = Signature.getInstance("SHA1withECDSA");

			dsa.initVerify(key);
	        
	        dsa.update(tx.getBytes());
	        
	        return dsa.verify(tx.getSignature());
		}catch(Exception e) {
			return false;
		}
	}
}
