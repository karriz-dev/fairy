package fairy.demo;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import fairy.core.managers.key.KeyManager;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class FairyClient {
	public static void main(String[] args) {
		/*Session.getInstance();
		
		// waiting session is opened
		while(Session.getInstance().isOpened());

		// Linker Call
		Linker.getInstance();
		
		//CommandLineInterface Layout Call
		CommandLayout.getInstance();*/
		
		KeyManager.getInstance().Create(100);
		
		String ftxid = "12345678123456781234567812345678";
		String ftxaddress = "12345678123456781234567812345678";
		Map<String, Double> output = new HashMap<String ,Double>();
		output.put("", 10081008.0);
		
		Transaction tx = new TokenTransaction(ftxid, ftxaddress, output);
		
		KeyPair pair = KeyManager.getInstance().getKeyPair();
		
		tx.setSignature(KeyManager.getInstance().Sign(tx.getBytes(),pair.getPrivate()));
		
		if(KeyManager.getInstance().Verify(tx, pair.getPublic()))
		{
			System.out.println("OK");
		}
	}
}
