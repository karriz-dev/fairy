package fairy.api.get;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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
			exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
			exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "x-requested-with");
			exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
			
			Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery()); 

			String response = "";
			
			Gson gson = new Gson();
			
			List<Block> blockList = LedgerManager.getInstance().getBlockList();
			
			List<Transaction> searchList = new ArrayList<Transaction>();
			
	        if(params.keySet().size() > 0)
	        {
	        	for(String key: params.keySet())
	        	{
	        		if(key.equals("type"))
	        		{
	        			short type = (short)Integer.parseInt(params.get("type"),16);
	
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
	        		}
	        		else if(key.equals("super_type"))
	        		{
	        			String type = params.get("super_type");

	        			switch(type) {
	        			case "a":
		    				for(Block b : blockList)
		    				{
		    					for(Transaction tx: b.getTransactionList())
		    					{
		    						
		    						if((tx.getType() == (short)0xa001) || (tx.getType() == (short)0xa002))
		    						{
		    							searchList.add(tx);
		    						}
		    					}
		    				}
		    				response += gson.toJson(searchList);
	        				break;
	        			}
	        		}
	        	}

			    OutputStream os = exchange.getResponseBody();
			    
			    if(response != "")
			    {
			    	exchange.sendResponseHeaders(200, response.length());
			    	os.write(response.toString().getBytes());
			    }else
			    {
			    	response = "block type is not defined.. (unavailable type error)";
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
