package fairy.core.managers.transaction;

import java.util.LinkedList;
import java.util.Queue;

import fairy.core.command.CommandLayout;
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
				synchronized(this) {
					if(transactionQueue.size() >= MAX_SIZE)
					{
						// Transaction Queue를 가지고 Block을 생성
						
						Block block = new Block(Network.getLocalIP(), transactionQueue);
						block.saveBlock();
						
						// 전파를 하기 전에 이미 생성 되어있는 블록이라면 전파를 진행하지 않음
					
						// 블록은 생성된 순간 다른 노드들에게 전파를 진행함(전파를 진행하면 보상 지급)
						
						// 작업이 끝난 이후에는 트랜잭션 큐를 비워 줌
						CommandLayout.getInstance().addMessage("block is created(reward : 50.0 BASE)");
						transactionQueue.clear();
					}
				}
				Thread.sleep(1);
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
	
	public boolean Push(Transaction tx)
	{
		try {
			if(transactionQueue.size() + 1 == MAX_SIZE)
				return false;
			else {
				return transactionQueue.add(tx);
			}
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
}
