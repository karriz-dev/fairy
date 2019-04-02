package fairy.api.post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.api.Handler;
import fairy.core.managers.key.KeyManager;
import fairy.core.managers.transaction.TransactionManager;
import fairy.core.net.communicator.Linker;
import fairy.valueobject.managers.transaction.HydrogenTransaction;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class HydrogenTransactionHandler extends Handler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if(exchange.getRequestMethod().toUpperCase().equals(Handler.POST))
		{
			exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
			exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "x-requested-with");
			exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

			Map<String, Object> parameters = new HashMap<String, Object>();
	        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
	        BufferedReader br = new BufferedReader(isr);
	        String query = br.readLine();
	        parseQuery(query, parameters);
	        
	        String response = "";

	        if(!parameters.isEmpty())
	        {
	        	List<Transaction> transactionList = new ArrayList<Transaction>();

	            for (String key : parameters.keySet()) {
	            	Transaction tx = new HydrogenTransaction(key, Double.valueOf((String)parameters.get(key)), 1000.0);

		            KeyPair pair = KeyManager.getInstance().Get().getPair();
		            
		            tx.setSignature(TransactionManager.getInstance().Sign(tx.getBytes(), pair.getPrivate()));
		            tx.setPublicKey(pair.getPublic());

		            transactionList.add(tx);
	            }

	            int count = 0;
	            
	            for(Transaction tx: transactionList)
	            {
	            	if(Linker.getInstance().broadcastingTransactrionUsingSerialization(tx))
		            {
			    		count++;
		            }else{
		            	response = "Transaction wasn't broadcasted !! \r\n" + tx.toString();
		            }
	            }
	            
	            if(count == transactionList.size())
	            {
	            	response = "All transaction was broadcasted !!";
	            }
	            else
	            {
	            	response = "transaction wasn't broadcasted !!(not send: " + (transactionList.size() - count) + ")";
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
