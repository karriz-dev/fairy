package fairy.core.managers.transaction;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fairy.core.managers.ledger.LedgerManager;
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
	
	private int difficulty = 10;
	private final double targetTime = 10.0;
	
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
				List<Transaction> txlist = new ArrayList<Transaction>();
				
				while(!transactionQueue.isEmpty())
				{
					txlist.add(transactionQueue.poll());
				}
			
				synchronized(this) {
						
					if(txlist.size() > 0)
					{
						Debugger.Log(this, "create challge using " + txlist.size() + "transactions..");
						Block block = new Block(Network.getLocalIP(), txlist);
						
						double currentTime = block.isProof(difficulty);
						
						if(currentTime >= 0.0)
						{
							/*
							 * Proof-of-work
							 * 
							 * isProof(difficulty)
							 * 
							 * 1. 블록 생성시 hash(merkle + nonce) 가 target보다 작을 경우까지 반복 연산을 함
							 * 
							 * 2. target값 보다 작은 값이 등장하게 되면 블록이 검증되고 전파를 진행하게 된다.
							 * 
							 * 3. 채굴자의 address를 기록하여 보상을 지급한다.(future work)
							 * 
							 * */
							
							Debugger.Log(this, "block(" + block.getBid() +") is proved");
							
							if(!block.isGenesis())
							{
								// Genesis Block이 아닐경우에는 이전 블록 해시 ID를 기록함
								LedgerManager.getInstance().setPrevBlockID(block);
							}
							
							if(block.Create())
							{
								// 이미 존재 하는 블록이면 만들어 지지않고 넘어감
								Linker.getInstance().broadcastingBlock(block);
								Debugger.Log(this, "block(" + block.getBid() +") is broadcasted");
								Debugger.Log(this, block.toString());
							}
						}
						
						if(currentTime < targetTime)
						{
							difficulty = difficulty + 1;
						}else{
							difficulty = difficulty - 1;
						}
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
