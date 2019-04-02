package fairy.core.utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import fairy.valueobject.managers.transaction.Transaction;

public class Convert {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static byte[] ShortToByteArray(short s) {
		return new byte[]{(byte)((s >> 8) & 0xff), (byte)(s & 0xff)};
	}
	
	public static byte[] intToByteArray(int i) {
		return new byte[] {(byte)(i >> 24), (byte)(i >> 16), (byte)(i >> 8), (byte)(i)};
	}
	
	public static byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}
	
	public static byte[] doubleToBytes(double x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
	    buffer.putDouble(x);
	    return buffer.array();
	}
	
	public static byte booleanToByte(boolean b) {
		return (byte) (b ? 1 : 0 );
	}
	
	public static boolean byteToBoolean(byte b) {
		if(b==0)return false;
		else return true;
	}
	
	public static byte[] transactionListToBytes(List<Transaction> txlist)
	{
		int offset = 0;
		
		for(Transaction tx: txlist)
		{
			offset = tx.getBytes().length + 4;
		}
		
		byte[] result = new byte[offset];
		
		offset = 0;
		
		for(Transaction tx: txlist)
		{
			int size = tx.getBytes().length;
			
			System.arraycopy(Convert.intToByteArray(size), 0, result, offset, 4);
			
			offset += 4;
			
			System.arraycopy(tx.getBytes(), 0, result, offset, size);
			
			offset += size;
		}
		
		return result;
	}
	
	public static List<Transaction> bytesToTransactionList(int max, byte[] bytes)
	{
		List<Transaction> txlist = new ArrayList<Transaction>();
		
		for(int i=0;i<max;i++)
		{
			
		}
		
		return txlist;
	}

	public static double bytesToDouble(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
	    buffer.put(bytes);
	    buffer.flip();//need flip 
	    return buffer.getDouble();
	}
	
	public static long bytesToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();//need flip 
	    return buffer.getLong();
	}
	
	public static short bytesToShort(byte[] bytes) {
	     return (short) ((((short)bytes[0] & 0xff) << 8) | 
	    		 (((short)bytes[1] & 0xff)));  
	}
	
	public static int byteArrayToInt(byte bytes[]) {
		return ((((int)bytes[0] & 0xff) << 24) |
				(((int)bytes[1] & 0xff) << 16) |
				(((int)bytes[2] & 0xff) << 8) |
				(((int)bytes[3] & 0xff)));
	} 
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}
