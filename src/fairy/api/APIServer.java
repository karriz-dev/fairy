package fairy.api;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import fairy.api.get.BlockListHandler;
import fairy.api.post.TokenTransactionHandler;
import fairy.core.utils.Debugger;

public class APIServer {
	public static APIServer instance = null;
	
	private HttpServer httpServer = null;
	
	private boolean isOpened = false;
	
	private APIServer() {
		try {
			httpServer = HttpServer.create(new InetSocketAddress(12075), 0);
			
			httpServer.createContext("/transaction/token",new TokenTransactionHandler());
			
			httpServer.createContext("/transaction/list",new BlockListHandler());
			
			httpServer.createContext("/block/list",new BlockListHandler());
			
			httpServer.setExecutor(null);
			httpServer.start();
			
			Debugger.Log(this, "API server(" + 12075 + ") is started !!");
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
