package fairy.api.get;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.api.Handler;
import fairy.core.managers.ledger.LedgerManager;

public class TokenBalanceHandler extends Handler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(exchange.getRequestMethod().toUpperCase().equals(Handler.GET))
		{
			Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery()); 
			
	        if(params.keySet().size() > 0)
	        {
	        	String address = params.get("address");
	        	
	        	exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
				exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
				exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "x-requested-with");
				exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

				Gson gson = new Gson();
				
				String response = "";
				
				double balance = LedgerManager.getInstance().getBalance(address);

			    OutputStream os = exchange.getResponseBody();
			    
			    if(balance >= 0.0)
			    {
			    	response = gson.toJson(balance);
			    	exchange.sendResponseHeaders(200, response.length());
			    	os.write(response.toString().getBytes());
			    }else
			    {
			    	response = "address(" + address + ")isn't exist address...!";
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
