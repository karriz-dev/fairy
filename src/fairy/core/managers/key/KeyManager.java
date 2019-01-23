package fairy.core.managers.key;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;

import fairy.core.utils.Debugger;

public class KeyManager 
{
	private Map<PrivateKey, PublicKey> keypairMap = null;
	
	private static KeyManager instance = null;
	
	private KeyManager(String walletPath)
	{
		if(walletPath != null)
		{
			File wallet = new File(walletPath);
			if(wallet.exists())
			{
				try {
					FileInputStream walletData = new FileInputStream(wallet);
					ObjectInputStream object = new ObjectInputStream(walletData);
					
					keypairMap = (HashMap<PrivateKey, PublicKey>)object.readObject();
				}catch(Exception e) {
					keypairMap = null;
				}	
			}
		}
	}
	
	public static KeyManager getInstance()
	{
		return instance;
	}
	
	public static KeyManager getInstance(String walletPath)
	{
		if(instance == null) {
			instance = new KeyManager(walletPath);
		}
		return instance;
	}
	
	private boolean Save() {
		try {
			FileOutputStream output = new FileOutputStream("");
			ObjectOutputStream object = new ObjectOutputStream(output);
			object.writeObject(keypairMap);
			object.flush();
			object.close();
			output.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean Create() {
		try {
	        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	        KeyPairGenerator.getInstance("EC").initialize(256, random);
	        
	        KeyPair pair = KeyPairGenerator.getInstance("EC").generateKeyPair();
	        
	        keypairMap.put(pair.getPrivate(), pair.getPublic());
	        
	        return Save();
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
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
	
	public boolean Verify(byte[] sign, PublicKey key) {
		try {
			Signature dsa = Signature.getInstance("SHA1withECDSA");

			dsa.initVerify(key);
	        
	        dsa.update("This is string to sign".getBytes("UTF-8"));
	        
	        return dsa.verify(sign);
		}catch(Exception e) {
			return false;
		}
	}
}
