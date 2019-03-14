package fairy.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fairy.core.managers.key.KeyManager;
import fairy.core.managers.transaction.TransactionManager;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class TransactionListHandler extends Handler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        parseQuery(query, parameters);
        
        String response = "";
        
        if(!parameters.isEmpty())
        {
        	Map<String, Double> outputList = new HashMap<String, Double>();
            
            for (String key : parameters.keySet()) {
            	outputList.put(key, (Double)parameters.get(key));
            }
            
            Transaction tx = new TokenTransaction("0x00", "0x00", outputList);
            
            KeyPair pair = KeyManager.getInstance().getKeyPair();
            
            String signature = new String(TransactionManager.getInstance().Sign(tx.getBytes(), pair.getPrivate()));
            
            response = pair.getPublic().toString() + "\r\n" + signature;
            
    		exchange.sendResponseHeaders(200, response.length());
        }
        else
        {
        	// Input이 없을 때에는 모든 트랜잭션의 값을 조회한다.
        	exchange.sendResponseHeaders(200, response.length());
        	
        	
        }
        
        OutputStream os = exchange.getResponseBody();
        
        if(response != "")
        {
        	 os.write(response.toString().getBytes());
        }
        
        os.close();
	}
}
