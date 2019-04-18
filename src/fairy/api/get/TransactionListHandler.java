package fairy.api.get;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.api.Handler;
import fairy.core.managers.ledger.LedgerManager;
import fairy.valueobject.managers.block.Block;
import fairy.valueobject.managers.transaction.Transaction;

public class TransactionListHandler extends Handler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(exchange.getRequestMethod().toUpperCase().equals(Handler.GET))
		{
			exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
			exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "x-requested-with");
			exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

			Gson gson = new Gson();
		
			List<Block> blockList = LedgerManager.getInstance().getBlockList();
			List<Transaction> transactionList = new ArrayList<Transaction>();
			
			for(Block b : blockList)
			{
				for(Transaction tx: b.getTransactionList())
				{
					transactionList.add(tx);
				}
			}
			
			String response = gson.toJson(transactionList);
			
		    OutputStream os = exchange.getResponseBody();
		    
		    if(response != "")
		    {
		    	exchange.sendResponseHeaders(200, response.length());
		    	os.write(response.toString().getBytes());
		    }else
		    {
		    	response = "block is not load wating for create block..";
		    	exchange.sendResponseHeaders(200, response.length());
		    	os.write(response.toString().getBytes());
		    }
		    
		    os.close();
		}
		else {
			exchange.sendResponseHeaders(400, 0);
			exchange.close();
		}
	}
}