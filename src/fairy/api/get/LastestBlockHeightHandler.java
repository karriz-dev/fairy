package fairy.api.get;

import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.api.Handler;
import fairy.core.managers.ledger.LedgerManager;

public class LastestBlockHeightHandler extends Handler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(exchange.getRequestMethod().toUpperCase().equals(Handler.GET))
		{
			exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
			exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
			exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");


			Gson gson = new Gson();
		
			Long height = LedgerManager.getInstance().getLatestBlock().getHeight();
			String response = gson.toJson(height);
			
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
