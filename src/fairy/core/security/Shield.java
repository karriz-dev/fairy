package fairy.core.security;

import java.security.MessageDigest;
import java.security.PublicKey;

public class Shield {
	public static String SHA256(byte[] data){
		try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            StringBuffer hexString = new StringBuffer();
 
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }		
	}
	public static String SHA256(String data){
		try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
 
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }		
	}
	public static String Address(PublicKey key)
	{
		Base58 base58 = new Base58();
		return "0x" + base58.encode(key.getEncoded()).substring(0, 32);
	}
}
