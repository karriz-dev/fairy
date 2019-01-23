package fairy.valueobject.managers.transaction;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import fairy.core.net.communicator.Session;
import fairy.core.utils.Convert;

public abstract class Transaction {
	
	protected String tid = null;
	private short type = 0x01;
	private long timestamp = 0L;
	
	private byte[] signature = null;
	
	protected int versionlength = 0;
	protected String version = null;
	
	protected int length = 0;
	protected byte[] datas = null;
	
	public Transaction(short type)
	{
		this.type = type;
		this.timestamp = System.currentTimeMillis();
		
		this.tid = "";
		
		this.datas = "Test Transaction".getBytes();
		this.length = datas.length;
	}

	public Transaction(byte[][] stream)
	{
		this.tid = new String(stream[0]);
		this.type = Convert.bytesToShort(stream[1]);
		this.timestamp = Convert.bytesToLong(stream[2]);
		this.versionlength = Convert.byteArrayToInt(stream[3]);
		this.version = new String(stream[4]);
		this.length = Convert.byteArrayToInt(stream[5]);
		this.datas = stream[6];
	}
	
	public byte[] getBytes() {
		byte[] header = getHeaderBytes();
		byte[] body = getDatasBytes();
		
		byte[] result = new byte[header.length + body.length];
		
		System.arraycopy(header, 0, result, 0, header.length);
		
		System.arraycopy(body, 0, result, header.length, body.length);
		
		return result;
	}
	
	private byte[] getHeaderBytes() {
		String version = Session.getInstance().getSessionVersion();
		
		byte[] versiondata = version.getBytes();
		versionlength = versiondata.length;
		
		byte[] result = new byte[32 + 2 + 8 + 4 + versionlength];
		
		System.arraycopy(tid.getBytes(), 0, result, 0, 32);
		
		System.arraycopy(Convert.ShortToByteArray(type), 0, result, 32, 2);
		
		System.arraycopy(Convert.longToBytes(timestamp), 0, result, 34, 8);
		
		System.arraycopy(Convert.intToByteArray(versionlength), 0, result, 42, 4);
		
		System.arraycopy(versiondata, 0, result, 46, versionlength);
		
		return result;
	}
	
	protected abstract byte[] getDatasBytes();
}