package fairy.api;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import fairy.api.get.BlockListHandler;
import fairy.api.get.LastestBlockHeightHandler;
import fairy.api.post.TokenTransactionHandler;
import fairy.core.utils.Debugger;

public class APIServer {
	public static APIServer instance = null;
	
	private HttpServer httpServer = null;
	
	private boolean isOpened = false;
	
	private APIServer() {
		try {
			httpServer = HttpServer.create(new InetSocketAddress(12075), 0);
			
			//transaction
			httpServer.createContext("/transaction/token",new TokenTransactionHandler());
			httpServer.createContext("/transaction/list",new BlockListHandler());
			
			//block
			httpServer.createContext("/block/list",new BlockListHandler());
			httpServer.createContext("/block/lastest/height",new LastestBlockHeightHandler());
			
			
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
