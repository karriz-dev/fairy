package fairy.api.get;

import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.api.Handler;
import fairy.core.managers.ledger.LedgerManager;

public class BlockListHandler extends Handler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(exchange.getRequestMethod().toUpperCase().equals(Handler.GET))
		{
			Gson gson = new Gson();
		
			String response = gson.toJson(LedgerManager.getInstance().getLatestBlock());
			
			System.out.println(response);
			
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
