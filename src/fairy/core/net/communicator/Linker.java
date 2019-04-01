 package fairy.core.net.communicator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fairy.core.utils.Debugger;
import fairy.core.utils.Network;
import fairy.valueobject.managers.block.Block;
import fairy.valueobject.managers.transaction.Transaction;

public class Linker extends Thread{
	private static Linker instance = null;
	
	private List<Socket> sockList = null;
	
	private boolean isBoosted = false;
	
	private Linker() {
		sockList = new ArrayList<Socket>();
		this.start();
	}
	
	@Override
	public void run()
	{
		String localIP = Network.getLocalIP();
		
		String[] ips = localIP.split("\\.");

		short a = Short.parseShort(ips[0]);
		short b = Short.parseShort(ips[1]);
		short c = Short.parseShort(ips[2]);
		short d = Short.parseShort(ips[3]);
		
		short counter = 0;
		
		String ipAddress = readLinkerPool();
		
		if(ipAddress != null)
			isBoosted = true;
		
		while(localIP != null)
		{
			if(counter >= 255) {
				c++;
				counter = 0;
				if(c>=256) {
					c=0;
				}
			}
			
			if(!isBoosted){
				d++;
				
				if(d>255)
					d = 0;
				
				ipAddress = a + "." + b + "." + c + "." + d;
				
				if(!Network.getLocalIP().equals(ipAddress)) {
					
					Debugger.Log(this, "send connection request to " + ipAddress, 1);
					
					try {
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress(ipAddress, 10080), 35);
						sockList.add(socket);
						Debugger.Log(this, "success connection to " + ipAddress, 1);
					}catch(Exception e) {
						try {
							Thread.sleep(100);
							counter++;
						}catch(Exception ex) {
							Debugger.Log(this, ex);
						}
					}	
				}
			}else{
				if(!Network.getLocalIP().equals(ipAddress)) {
					
					Debugger.Log(this, "send connection request to " + ipAddress, 1);
					
					try {
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress(ipAddress, 10080), 3000);
						sockList.add(socket);
						Debugger.Log(this, "success connection to " + ipAddress, 1);
						isBoosted = false;
					}catch(Exception e) {
						try {
							Thread.sleep(100);
							counter++;
						}catch(Exception ex) {
							Debugger.Log(this, ex);
						}
					}	
				}
			}
		}
	}

	private String readLinkerPool() {
		try {
			File path = new File("assets/pool.fairy");
			if(path.exists()) {
				FileInputStream in = new FileInputStream(path);
				return new String(in.readAllBytes());
			}else return null;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return null;
		}
	}
	
	public boolean broadcastingTransactrionUsingSerialization(Transaction tx)
	{
		for(Socket clnt: sockList)
		{
			try {
				ObjectOutputStream out = new ObjectOutputStream(clnt.getOutputStream());
				
				out.writeObject(tx);
			}catch(Exception e) {
				Debugger.Log(this, e);
				try {
					clnt.close();
				} catch (IOException ioe) {}
				return false;
			}
		}
		return true;
	}
	
	public boolean broadcastingTransactrion(Transaction tx)
	{
		for(Socket clnt: sockList)
		{
			try {
				ObjectOutputStream oos = new ObjectOutputStream(clnt.getOutputStream());
				oos.writeObject(tx);
			}catch(Exception e) {
				Debugger.Log(this, e);
				try {
					clnt.close();
				} catch (IOException ioe) {}
				return false;
			}
		}
		return true;
	}
	
	public boolean broadcastingBlock(Block block)
	{
		for(Socket clnt: sockList)
		{
			try {
				ObjectOutputStream oos = new ObjectOutputStream(clnt.getOutputStream());
				oos.writeObject(block);
			}catch(Exception e) {
				Debugger.Log(this, e);
				try {
					clnt.close();
				} catch (IOException ioe) {}
				return false;
			}
		}
		return true;
	}
	
	public static Linker getInstance() {
		if(instance == null) {
			instance = new Linker();
		}
		return instance;
	}
}
