package fairy.core.utils;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Network {
	
	public static final short TCP = 0x01;
	
	public static final short UDP = 0x02;
	
	public static String getIP()
	{
		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
		  	return socket.getLocalAddress().getHostAddress();
		}catch(Exception e) {
			return null;
		}
	}
}
