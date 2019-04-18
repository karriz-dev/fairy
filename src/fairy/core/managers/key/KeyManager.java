package fairy.core.managers.key;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fairy.core.security.Shield;
import fairy.core.utils.Debugger;
import fairy.valueobject.managers.key.WalletKey;

public class KeyManager 
{
	private List<WalletKey> wallet = null;
	
	private static KeyManager instance = null;
	
	private String path = null;
	
	private KeyManager()
	{
		this.wallet = new ArrayList<WalletKey>();
	}
	
	private KeyManager(String walletPath)
	{
		this.path = walletPath;
		
		if(walletPath != null)
		{
			File walletFile = new File(walletPath);
			if(walletFile.exists())
			{
				try {
					FileInputStream walletData = new FileInputStream(walletFile);
					ObjectInputStream object = new ObjectInputStream(walletData);
					
					this.wallet = (List<WalletKey>)object.readObject();
					
				}catch(Exception e) {
					this.wallet = null;
				}	
			}else{
				this.wallet = new ArrayList<WalletKey>();
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
			File file = new File("assets/wallet/wallet.fairy");
			
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileOutputStream output = new FileOutputStream(file);
			ObjectOutputStream object = new ObjectOutputStream(output);
			
			object.writeObject(this.wallet);
			
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
	        
	        wallet.add(new WalletKey(pair, Shield.Address(pair.getPublic())));
	        
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
				
				wallet.add(new WalletKey(pair, Shield.Address(pair.getPublic())));
			}
	        return Save();
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}	
	
	public WalletKey Get()
	{
		try {
			int size = wallet.size();
			
			if(size > 0)
			{
				Random r = new Random();
				int rand = r.nextInt(size);
				
				return wallet.get(rand);
			}
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	
	public List<WalletKey> GetAll()
	{
		return this.wallet;
	}
	
	public int Count() {
		return wallet.size();
	}
	
	@Override
	public String toString()
	{
		String result = "[Fairy]: wallet(" + path + ")'s list\r\n";
		
		int count = 1;
		for(WalletKey key: wallet)
		{
			result += key.toString() +"\r\n";
            count++;
		}
		
		result += "=========================================================================================================\r\n";
		result += "[Fairy]: wallet(" + path + ")'s list\r\n";
		result += "[Fairy]: Total count is " + (count-1) + "\r\n";
		result += "=========================================================================================================\r\n";

		return result;
	}
}
