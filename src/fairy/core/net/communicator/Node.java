package fairy.core.net.communicator;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import fairy.core.managers.transaction.TransactionManager;
import fairy.core.utils.Convert;
import fairy.core.utils.Debugger;
import fairy.valueobject.managers.transaction.StatusTransaction;
import fairy.valueobject.managers.transaction.Transaction;
import fairy.valueobject.managers.transaction.TransactionType;

public class Node extends Thread {
	private Socket nodeSock = null;
	
	private String version = null;
	
	private InputStream nodeInputStream = null;
	private OutputStream nodeOutputStream = null;
	
	private boolean isDead = false;
	
	public Node(Socket nodeSock) {
		try {
			this.nodeSock = nodeSock;
			
			this.nodeInputStream = nodeSock.getInputStream();
			this.nodeOutputStream = nodeSock.getOutputStream();
		
			this.start();
		}catch(Exception e) {
			Debugger.Log(this, e);
		}
	}
	
	@Override
	public void run()
	{
		Debugger.Log(this, "node(" + nodeSock.getInetAddress().getHostAddress() +":10080) is connected !"); 
		while(!isDead)
		{
			try {
				
				int lastestLength = 0;
				
				if(nodeInputStream.available() > 0)
				{
					int[] lengthList = new int[] {32, 2, 8, 4, -1, 4, -1};
					
					byte[][] datas = new byte[8][];

					for(int i = 0 ; i < 7; i++)
					{
						int count = 0;
						
						if(lengthList[i] == -1)
						{
							datas[i] = new byte[lastestLength];
							
							while(count < lastestLength)
							{
								int r = nodeInputStream.read();
								if(r != -1) {
									datas[i][count] = (byte)r;
									count++;
								}
							}
						}
						else
						{
							datas[i] = new byte[lengthList[i]];

							while(count < lengthList[i])
							{
								int r = nodeInputStream.read();
								if(r != -1) {
									datas[i][count] = (byte)r;
									count++;
								}
							}

							if(lengthList[i+1] == -1) {
								lastestLength = Convert.byteArrayToInt(datas[i]);
							}
						}
					}

					Transaction transaction = null;
					
					switch(Convert.bytesToShort(datas[1]))
					{
					case TransactionType.STATUS:
						transaction = new StatusTransaction(datas);
						break;
					}
					
					//TransactionManager.getInstance().Push(transaction);
				}
			} catch (Exception e) {
				Debugger.Log(this, e);
				
				try {
					this.nodeInputStream.close();
					this.nodeOutputStream.close();
					this.nodeSock.close();
					isDead = true;	
				}catch(Exception socketErr) {
					Debugger.Log(this, socketErr);
				}
			}
		}
	}
	
	public boolean sendTransaction(Transaction tx)
	{
		try {
			nodeOutputStream.write(tx.getBytes());
			return true;
		} catch (Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
	
	public String getNodeIP()
	{
		return nodeSock.getInetAddress().getHostAddress(); 
	}
	
	public int getNodePort()
	{
		return nodeSock.getPort();
	}
}
