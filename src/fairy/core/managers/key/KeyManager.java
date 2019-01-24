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
import java.util.Random;

import fairy.core.utils.Debugger;
import fairy.valueobject.managers.transaction.Transaction;

public class KeyManager 
{
	private Map<PrivateKey, PublicKey> keypairMap = null;
	
	private static KeyManager instance = null;
	
	private KeyManager()
	{
		keypairMap = new HashMap<PrivateKey, PublicKey>();
	}
	
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
		if(instance == null) {
			instance = new KeyManager();
		}
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
	
	public boolean Create(int count) {
		try {
			for(int i=0;i<count;i++)
			{
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				KeyPairGenerator.getInstance("EC").initialize(256, random);
				
				KeyPair pair = KeyPairGenerator.getInstance("EC").generateKeyPair();
				
				keypairMap.put(pair.getPrivate(), pair.getPublic());
			}
	        return Save();
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}	
	
	public KeyPair getKeyPair() {
		try {
			Random r = new Random();
			int size = keypairMap.size();
			int count = 0;
			int rand = r.nextInt(size);
			if(size > 0)
			{
				for(PrivateKey key: keypairMap.keySet())
				{
					if(count == rand)
						return new KeyPair(keypairMap.get(key), key);
					count++;
				}
			}
			return null;
		}catch(Exception e) {
			System.out.println(e.getMessage() + "/ " + e.getCause());
			return null;
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
	
	public boolean Verify(Transaction tx, PublicKey key) {
		try {
			Signature dsa = Signature.getInstance("SHA1withECDSA");

			dsa.initVerify(key);
	        
	        dsa.update(tx.getBytes());
	        
	        return dsa.verify(tx.getSignature());
		}catch(Exception e) {
			return false;
		}
	}
	
	public int Count() {
		return keypairMap.size();
	}
	
	@Override
	public String toString()
	{
		return keypairMap.toString();
	}
}
