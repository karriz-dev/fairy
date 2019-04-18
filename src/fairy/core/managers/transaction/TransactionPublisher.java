package fairy.core.managers.transaction;

import java.security.PrivateKey;
import java.security.Signature;

public class TransactionPublisher {
	
	private static TransactionPublisher instance = null;
	
	private TransactionPublisher(){}
	
	public static TransactionPublisher getInstance() {
		if(instance == null) {
			instance = new TransactionPublisher();
		}
		return instance;	
	}
	
	public byte[] Sign(byte[] data, PrivateKey key) {
		try {
			Signature dsa = Signature.getInstance("SHA1withECDSA");
			
	        dsa.initSign(key);

	        dsa.update(data);

	        return dsa.sign();
		}catch(Exception e) {
			return null;
		}
	}
}
