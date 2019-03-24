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
import fairy.core.net.communicator.Session;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class TokenTransactionHandler extends Handler implements HttpHandler {
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
            
        	String ftxid = "";
        	String ftxaddress = "";
        	
            for (String key : parameters.keySet()) {
            	switch(key){
            	case "ftxid":
            		ftxid = (String)parameters.get(key);
            		break;
            	case "ftxaddress":
            		ftxaddress = (String)parameters.get(key);
            		break;
            	default:
            		outputList.put(key, Double.valueOf((String)parameters.get(key)));
            		break;
            	}
            }
            
            
            System.out.println("============================= API HANDLER WORKED !! =============================");
            System.out.println("FTXID: " + ftxid);
            System.out.println("FTXADDRESS: " + ftxaddress);            
            System.out.println("OUTPUT LIST: " + outputList);
            System.out.println("============================= API HANDLER WORKED !! =============================");
            
            Transaction tx = new TokenTransaction(ftxid, ftxaddress, outputList);
            
            KeyPair pair = KeyManager.getInstance().getKeyPair();
            
            tx.setSignature(TransactionManager.getInstance().Sign(tx.getBytes(), pair.getPrivate()));
            
            response = tx.toString();
            
            Session.getInstance().broadcastingTransaction(tx);
            
    		exchange.sendResponseHeaders(200, response.length());
        }
        else
        {
        	exchange.sendResponseHeaders(500, response.length());
        
        	response = "transaction not send... check the arguments !!";
        }
        
        OutputStream os = exchange.getResponseBody();
        
        if(response != "")
        {
        	 os.write(response.toString().getBytes());
        }
        
        os.close();
	}
}
