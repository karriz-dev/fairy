package fairy.api.get;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.api.Handler;

public class BlockListHandler extends Handler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		String response = "";
		
		exchange.sendResponseHeaders(200, response.length());
	    
	    OutputStream os = exchange.getResponseBody();
	    
	    if(response != "")
	    {
	    	 os.write(response.toString().getBytes());
	    }
	    
	    os.close();
	}

}
