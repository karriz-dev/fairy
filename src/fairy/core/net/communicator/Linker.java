package fairy.core.net.communicator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fairy.core.utils.Debugger;
import fairy.core.utils.Network;
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
		String localIP = Network.getIP();
		
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
				
				if(!Network.getIP().equals(ipAddress)) {
					
					Debugger.Log(this, "send connection request to " + ipAddress);
					
					try {
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress(ipAddress, 10080), 35);
						sockList.add(socket);
						Debugger.Log(this, "success connection to " + ipAddress);
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
				if(!Network.getIP().equals(ipAddress)) {
					
					Debugger.Log(this, "send connection request to " + ipAddress);
					
					try {
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress(ipAddress, 10080), 35);
						sockList.add(socket);
						Debugger.Log(this, "success connection to " + ipAddress);
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
	
	public boolean broadcastingTransactrion(Transaction tx)
	{
		for(Socket clnt: sockList)
		{
			try {
				clnt.getOutputStream().write(tx.getBytes());
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
	
	public boolean broadcastingBlock(short type)
	{
		switch(type)
		{
		case Network.TCP:
			break;
		case Network.UDP:
			break;
		}
		return false;
	}
	
	public static Linker getInstance() {
		if(instance == null) {
			instance = new Linker();
		}
		return instance;
	}
}
