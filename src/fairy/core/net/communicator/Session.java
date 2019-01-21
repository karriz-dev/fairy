package fairy.core.net.communicator;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import fairy.core.utils.Debugger;
import fairy.valueobject.managers.transaction.Transaction;

public class Session extends Thread {
	
	private static Session instance = null;
	
	private ServerSocket serverSock = null;
	
	private List<Node> nodeList = null;
	
	private boolean isOpened = false;
	
	private Session()
	{
		try {
			nodeList = new ArrayList<>();
		
			serverSock = new ServerSocket(10080);
		
			this.start();
		}catch(Exception e) {
			Debugger.Log(this, e);
		}
	}
	
	public static Session getInstance() {
		if(instance == null) {
			instance = new Session();
		}
		return instance;
	}
	
	@Override
	public void run()
	{
		Debugger.Log(this, "Session is Opend !");
		
		isOpened = true;
		
		while(true)
		{
			try {
				nodeList.add(new Node(serverSock.accept()));
			}catch(Exception e) {
				Debugger.Log(this, e);
			}
		}
	}

	public boolean broadcastingTransaction(Transaction tx)
	{
		try {
			for(Node clnt: nodeList)
			{
				clnt.sendTransaction(tx);
			}
			return true;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
	
	public boolean broadcastingTransaction(Transaction tx, Node target)
	{
		try {
			for(Node clnt: nodeList)
			{
				if(clnt == target)
					clnt.sendTransaction(tx);
			}
			return true;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
	
	public boolean broadcastingTransactionExceptTarget(Transaction tx, Node target)
	{
		try {
			for(Node clnt: nodeList)
			{
				if(clnt != target)
					clnt.sendTransaction(tx);
			}
			return true;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
	
	public boolean isOpened() {
		return this.isOpened;
	}
	
	public String getSessionVersion()
	{
		return "fairy://t.0.1";
	}
}
