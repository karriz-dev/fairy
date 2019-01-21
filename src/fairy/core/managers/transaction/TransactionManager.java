package fairy.core.managers.transaction;

import java.util.LinkedList;
import java.util.Queue;

import fairy.core.command.CommandLayout;
import fairy.core.utils.Debugger;
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
				if(transactionQueue.size() > 0)
				{
					CommandLayout.getInstance().addMessage("transaction(" + transactionQueue.poll().toString() + ")recv ! \r\n");
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
