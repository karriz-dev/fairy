package fairy.api.get;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.api.Handler;
import fairy.api.valueobjects.RecentSellHydrogen;
import fairy.core.managers.ledger.LedgerManager;
import fairy.valueobject.managers.block.Block;
import fairy.valueobject.managers.transaction.Transaction;
import fairy.valueobject.managers.transaction.TransactionSellHydrogen;

public class TransactionSellHydrogenBalanceHandler extends Handler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(exchange.getRequestMethod().toUpperCase().equals(Handler.GET))
		{
			Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery()); 
			
			if(params != null)
			{
	        	String tid = params.get("tid");
	        	
	        	if(tid != null)
	        	{
	        		exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
					exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
					exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "x-requested-with");
					exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
					
					Gson gson = new Gson();
					
					double result = 10000000.0;
					RecentSellHydrogen sendResult = null;
					
					for(Block b: LedgerManager.getInstance().getBlockList())
					{
						for(Transaction tx: b.getTransactionList())
						{
							if(tx.getType() == (short)0xc002)
							{
								TransactionSellHydrogen sell = (TransactionSellHydrogen)tx;
								if(sell.getHydrogenTransactionID().equals(tid))
								{
									double total = sell.getBiddingCount() * sell.getBiddingPrice();
									
									if(total < result)
									{
										result = total;
										sendResult = new RecentSellHydrogen(sell.getBiddingPrice(), sell.getBiddingCount());
									}
								}
							}
						}
					}
					
					String response = gson.toJson(sendResult);
					
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
	        	else
	        	{
	        		exchange.sendResponseHeaders(400, 0);
	    			exchange.close();
	        	}
			}
			else
			{
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
