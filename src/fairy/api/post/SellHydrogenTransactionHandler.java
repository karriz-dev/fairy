package fairy.api.post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.api.Handler;
import fairy.core.managers.key.KeyManager;
import fairy.core.managers.transaction.TransactionManager;
import fairy.core.net.communicator.Linker;
import fairy.valueobject.managers.transaction.OrderTransaction;
import fairy.valueobject.managers.transaction.Transaction;
import fairy.valueobject.managers.transaction.TransactionSellHydrogen;

public class SellHydrogenTransactionHandler extends Handler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(exchange.getRequestMethod().toUpperCase().equals(Handler.POST))
		{
			exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
			exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
			exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

			Map<String, Object> parameters = new HashMap<String, Object>();
	        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
	        BufferedReader br = new BufferedReader(isr);
	        String query = br.readLine();
	        parseQuery(query, parameters);
	        
	        String response = "";

	        if(!parameters.isEmpty())
	        {
	        	String privatekey = (String)parameters.get("privatekey");
	        	String buyeraddress = (String)parameters.get("buyeraddress");
	        	String selleraddress = (String)parameters.get("selleraddress");
	        	double biddingprice = Double.parseDouble((String)parameters.get("biddingprice"));
	        	double biddingcount = Double.parseDouble((String)parameters.get("biddingcount"));
	        	
	        	Transaction tx = new TransactionSellHydrogen(privatekey, buyeraddress, selleraddress, biddingprice, biddingcount);
	            
	            KeyPair pair = KeyManager.getInstance().Get().getPair();

	            tx.setSignature(TransactionManager.getInstance().Sign(tx.getBytes(), pair.getPrivate()));
	            tx.setPublicKey(pair.getPublic());
	            
	            if(Linker.getInstance().broadcastingTransactrionUsingSerialization(tx))
	            {
	            	response = "Transaction was broadcasted !! \r\n" + tx.toString();
	            }else{
	            	response = "Transaction wasn't broadcasted !! \r\n";
	            }
	            
	            exchange.sendResponseHeaders(200, response.length());
	        }
	        else
	        {
	        	exchange.sendResponseHeaders(400, 0);
	        	exchange.close();
	        }
	        
	        OutputStream os = exchange.getResponseBody();
	        
	        os.write(response.toString().getBytes());
	        
	        os.close();
		}
		else {
			exchange.sendResponseHeaders(400, 0);
			exchange.close();
		}
	}
}
