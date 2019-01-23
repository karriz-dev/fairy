package fairy.demo;

import java.util.HashMap;
import java.util.Map;

import fairy.core.command.CommandLayout;
import fairy.core.managers.key.KeyManager;
import fairy.core.net.communicator.Linker;
import fairy.core.net.communicator.Session;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class FairyClient {
	public static void main(String[] args) {
		Session.getInstance();
		
		// waiting session is opened
		while(Session.getInstance().isOpened());

		// Linker Call
		Linker.getInstance();
		
		//CommandLineInterface Layout Call
		CommandLayout.getInstance();
		
		KeyManager.getInstance().Create(100);
		
		String ftxid = "0x000000000000000000000000000000";
		String ftxaddress = "0x000000000000000000000000000000";
		Map<String, Double> output = new HashMap<String ,Double>();
		output.put("", 10081008.0);
		
		Transaction tx = new TokenTransaction(ftxid, ftxaddress, output);
		
		KeyManager.getInstance().Sign(tx.getBytes(),"");
		KeyManager.getInstance().Verify(tx.getSignature(),"");
	}
}
