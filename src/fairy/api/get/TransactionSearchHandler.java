package fairy.api.get;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.api.Handler;
import fairy.core.managers.ledger.LedgerManager;
import fairy.valueobject.managers.block.Block;
import fairy.valueobject.managers.transaction.Transaction;

public class TransactionSearchHandler extends Handler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(exchange.getRequestMethod().toUpperCase().equals(Handler.GET))
		{
			Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery()); 
			
	        if(params.keySet().size() > 0)
	        {
	        	short type = (short)Integer.parseInt(params.get("type"),16);
	        	
	        	exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
				exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
				exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "x-requested-with");
				exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

				Gson gson = new Gson();
				
				String response = "";
				
				List<Block> blockList = LedgerManager.getInstance().getBlockList();
				
				List<Transaction> searchList = new ArrayList<Transaction>();
				
				for(Block b : blockList)
				{
					for(Transaction tx: b.getTransactionList())
					{
						if(tx.isMatched(type))
						{
							searchList.add(tx);
						}
					}
				}

				response += gson.toJson(searchList);
				
			    OutputStream os = exchange.getResponseBody();
			    
			    if(response != "")
			    {
			    	exchange.sendResponseHeaders(200, response.length());
			    	os.write(response.toString().getBytes());
			    }else
			    {
			    	response = "block type is not defined...(" + type + ")";
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
		else {
			exchange.sendResponseHeaders(400, 0);
			exchange.close();
		}
	}
}
