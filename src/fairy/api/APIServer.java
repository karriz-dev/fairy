package fairy.api;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import fairy.api.get.BlockListHandler;
import fairy.api.get.BlockListRecentHandler;
import fairy.api.get.LastestBlockHeightHandler;
import fairy.api.get.TokenBalanceHandler;
import fairy.api.get.TransactionListHandler;
import fairy.api.get.TransactionSearchHandler;
import fairy.api.get.TransactionSearchRecentHandler;
import fairy.api.post.DeliveryTransactionHandler;
import fairy.api.post.HydrogenTransactionHandler;
import fairy.api.post.OrderTransactionHandler;
import fairy.api.post.SellHydrogenTransactionHandler;
import fairy.api.post.TokenTransactionHandler;
import fairy.core.utils.Debugger;

public class APIServer {
	public static APIServer instance = null;
	
	private HttpServer httpServer = null;
	
	private boolean isOpened = false;
	
	private APIServer() {
		try {
			httpServer = HttpServer.create(new InetSocketAddress(5000), 0);
			
			//transaction
			httpServer.createContext("/transaction/token",new TokenTransactionHandler());
			httpServer.createContext("/transaction/hydrogen",new HydrogenTransactionHandler());
			httpServer.createContext("/transaction/delivery",new DeliveryTransactionHandler());
			httpServer.createContext("/transaction/order",new OrderTransactionHandler());
			httpServer.createContext("/transaction/sell", new SellHydrogenTransactionHandler());
			
			
			httpServer.createContext("/transaction/list",new TransactionListHandler());
			httpServer.createContext("/transaction/search", new TransactionSearchHandler());
			httpServer.createContext("/transaction/search/recent", new TransactionSearchRecentHandler());
			
			
			//block
			httpServer.createContext("/block/list",new BlockListHandler());
			httpServer.createContext("/block/list/recent",new BlockListRecentHandler());
			httpServer.createContext("/block/lastest/height",new LastestBlockHeightHandler());
			
			//token
			httpServer.createContext("/token/balance",new TokenBalanceHandler());
			
			//hydrogen
			httpServer.createContext("/hydrogen/balance",new TokenBalanceHandler());
			
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
