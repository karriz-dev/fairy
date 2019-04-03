package fairy.valueobject.managers.transaction;

import java.io.Serializable;
import java.security.PublicKey;

import fairy.core.net.communicator.Session;
import fairy.core.security.Shield;
import fairy.core.utils.Convert;

public abstract class Transaction implements Serializable {
	
	private static final long serialVersionUID = 757735063156842L;
	
	protected String tid = null;
	private short type = 0x01;
	private long timestamp = 0L;
	
	protected int versionlength = 0;
	protected String version = null;
	
	protected int length = 0;
	protected byte[] datas = null;
	
	private byte[] signature = null;
	protected PublicKey key = null;
	
	public Transaction(short type)
	{
		this.type = type;
		this.timestamp = System.currentTimeMillis();
		this.datas = "{fairy://0.1.0.0v}".getBytes();
		this.length = datas.length;
		
		this.tid = Shield.SHA256(this.getHeaderBytesExceptID());
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
	
	public short getType() {
		return this.type;
	}
	public void setSignature(byte[] sign) {
		this.signature = sign;
	}
	
	public void setPublicKey(PublicKey key) {
		this.key = key;
	}
	
	public byte[] getSignature() {
		return signature;
	}
	
	public String getTransactionID() {
		return this.tid;
	}
	
	public boolean isMatched(short type) {
		return this.type == type;
	}
	
	public byte[] getBytes() {
		byte[] header = getHeaderBytes();
		byte[] body = getDatasBytes();
		
		byte[] result = new byte[header.length + body.length];
		
		System.arraycopy(header, 0, result, 0, header.length);
		
		System.arraycopy(body, 0, result, header.length, body.length);
		
		return result;
	}
	
	public PublicKey getPublicKey() {
		return this.key;
	}
	
	public boolean setGenesisTransaction() {
		this.tid = "GENESIS_TRANSACTION_ID";
		return true;
	}
	
	private byte[] getHeaderBytesExceptID() {
		String version = Session.getInstance().getSessionVersion();
	
		byte[] versiondata = version.getBytes();
		versionlength = versiondata.length;
		
		byte[] result = new byte[2 + 8 + 4 + versionlength];
		
		System.arraycopy(Convert.ShortToByteArray(type), 0, result, 0, 2);
		
		System.arraycopy(Convert.longToBytes(timestamp), 0, result, 2, 8);
		
		System.arraycopy(Convert.intToByteArray(versionlength), 0, result, 10, 4);
		
		System.arraycopy(versiondata, 0, result, 14, versionlength);
		
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