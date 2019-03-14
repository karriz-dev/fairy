package fairy.api;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class APIServer {
	public static APIServer instance = null;
	
	private HttpServer httpServer = null;
	
	private boolean isOpened = false;
	
	private APIServer() {
		try {
			httpServer = HttpServer.create(new InetSocketAddress(12075), 0);
		
			httpServer.createContext("/transaction/token",new TokenTransactionHandler());
		
			httpServer.setExecutor(null);
			httpServer.start();
		}catch(Exception e) {
			
		}
	}
	
	public static APIServer getInstance() {
		if(instance == null) {
			instance = new APIServer();
		}
		return instance;
	}
	
	public boolean isOpened() {
		return this.isOpened;
	}
}
