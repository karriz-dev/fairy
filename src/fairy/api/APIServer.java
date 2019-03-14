package fairy.api;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class APIServer {
	public static APIServer instance = null;
	
	private HttpServer httpServer = null;
	
	private boolean isOpened = false;
	
	private APIServer() {
		try {
			System.out.println("server open");
			
			httpServer = HttpServer.create(new InetSocketAddress(12075), 0);
			
			httpServer.createContext("/transaction/token",new TokenTransactionHandler());
			
			httpServer.createContext("/transaction/list",new TransactionListHandler());
			
			httpServer.setExecutor(null);
			httpServer.start();
			System.out.println("server start");
			
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
