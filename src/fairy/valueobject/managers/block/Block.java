package fairy.valueobject.managers.block;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import fairy.core.security.MerkleTree;
import fairy.core.security.Shield;
import fairy.core.utils.Convert;
import fairy.core.utils.Debugger;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class Block {
	//Header
	public String bid = null;
	private String prevbid = null;
	private Long timestamp = 0L;
	private String merkleroot = null;
	
	private Long height = 0L;
	
	private String creator = null;
	
	private boolean isGenesis = false;
	
	private int status = 0;
	
	//Body
	private List<Transaction> txlist = null;
	
	public Block(File blockFile){
		Load(blockFile);
	}
	
	public Block(String blockPath){
		Load(new File(blockPath));
	}

	public Block(String creator, List<Transaction> txlist) {
		this.creator = creator;
		this.timestamp = System.currentTimeMillis();
		this.merkleroot = MerkleTree.getMerkleRoot(txlist);
		this.txlist = txlist;
		
		this.bid = Shield.SHA256(this.merkleroot);
	}

	public boolean Create() {
		try {
			File file = new File("assets/blocks/" + bid + ".block");
			if(!file.exists())
			{
				if(file.createNewFile())
				{
					FileOutputStream output = new FileOutputStream(file);
					output.write(this.getBytes());
					output.flush();
					output.close();
					return true;
				}
				else return false;
			}else return false;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
	
	private boolean Load(File blockFile)
	{
		try {
			FileInputStream fis = new FileInputStream(blockFile);
			
			byte[] datas = fis.readAllBytes();
			
			this.isGenesis = Convert.byteToBoolean(datas[0]);
			
			if(isGenesis)
			{
				// 64 + 8 + 64 + 8 + 4 + creatorBuffer.length + 4
				int offset = 1;
				
				byte[] temp = new byte[64];
				for(int i=0;i<64;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.bid = new String(temp);
				
				offset += 64;
				temp = new byte[8];
				for(int i=0;i<8;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.timestamp = Convert.bytesToLong(temp);
				
				offset += 8;
				temp = new byte[64];
				for(int i=0;i<64;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.merkleroot = new String(temp);
				
				offset += 64;
				temp = new byte[8];
				for(int i=0;i<8;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.height = Convert.bytesToLong(temp);
				
				offset += 8;
				temp = new byte[4];
				for(int i=0;i<4;i++)
				{
					temp[i] = datas[i+offset];
				}
				int creatorLength = Convert.byteArrayToInt(temp);
				
				offset += 4;
				temp = new byte[creatorLength];
				for(int i=0;i<creatorLength;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.creator = new String(temp);
			}
			else
			{
				// 64 + 64 + 8 + 64 + 8 + 4 + creatorBuffer.length + 4
				byte[] temp = new byte[64];
				int offset = 1;
				for(int i=0;i<64;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.bid = new String(temp);
				
				offset += 64;
				temp = new byte[64];
				for(int i=0;i<64;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.prevbid = new String(temp);
				
				offset += 64;
				temp = new byte[8];
				for(int i=0;i<8;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.timestamp = Convert.bytesToLong(temp);
				
				offset += 8;
				temp = new byte[64];
				for(int i=0;i<64;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.merkleroot = new String(temp);
				
				offset += 64;
				temp = new byte[8];
				for(int i=0;i<8;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.height = Convert.bytesToLong(temp);
				
				offset += 8;
				temp = new byte[4];
				for(int i=0;i<4;i++)
				{
					temp[i] = datas[i+offset];
				}
				int creatorLength = Convert.byteArrayToInt(temp);
				
				offset += 4;
				temp = new byte[creatorLength];
				for(int i=0;i<creatorLength;i++)
				{
					temp[i] = datas[i+offset];
				}
				this.creator = new String(temp);
			}
			
			fis.close();
			return true;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
	
	public double getBalance() {
		double balance = 0.0;
		for(Transaction tx: txlist)
		{
			TokenTransaction token = (TokenTransaction)tx;
			
			balance += token.getBalance();
		}
		return balance;
	}
	
	public byte[] getBytes()
	{
		byte[] header = getBytesToHeader();
		byte[] body = getBytesToBody();
		
		byte[] buffer = new byte[header.length + body.length];
		
		System.arraycopy(header, 0, buffer, 0, header.length);
		System.arraycopy(body, 0, buffer, header.length, body.length);

		return buffer;
	}
	
	public byte[] getBytesToHeader()
	{
		byte[] creatorBuffer = creator.getBytes();
		
		byte[] buffer = null;
		
		if(this.prevbid != null)
		{
			buffer = new byte[1 + 64 + 64 + 8 + 64 + 8 + 4 + creatorBuffer.length + 4];
			
			int offset = 0;
			
			System.arraycopy(Convert.booleanToByte(this.isGenesis), 0, buffer, offset, 1);
			
			offset += 1;

			System.arraycopy(this.bid.getBytes(), 0, buffer, offset, 64);
			
			offset += 64;
			
			System.arraycopy(this.prevbid.getBytes(), 0, buffer, offset, 64);

			offset += 64;
			
			System.arraycopy(Convert.longToBytes(this.timestamp), 0, buffer, offset, 8);

			offset += 8;
			
			System.arraycopy(this.merkleroot.getBytes(), 0, buffer, offset, 64);

			offset += 64;
			
			System.arraycopy(Convert.longToBytes(this.height), 0, buffer, offset, 8);
			
			offset += 8;
			
			System.arraycopy(Convert.intToByteArray(this.creator.length()), 0, buffer, offset, 4);
			
			offset += 4;
			
			System.arraycopy(this.creator.getBytes(), 0, buffer, offset, this.creator.length());
			
			offset += this.creator.length();
			
			System.arraycopy(Convert.intToByteArray(txlist.size()), 0, buffer, offset, 4);
			
			offset += 4;
		}
		else
		{
			buffer = new byte[1 + 64 + 8 + 64 + 8 + 4 + creatorBuffer.length + 4];
			
			int offset = 0;
			
			buffer[0] = Convert.booleanToByte(this.isGenesis);
			
			offset += 1;

			System.arraycopy(this.bid.getBytes(), 0, buffer, offset, 64);
			
			offset += 64;

			System.arraycopy(Convert.longToBytes(this.timestamp), 0, buffer, offset, 8);

			offset += 8;

			System.arraycopy(this.merkleroot.getBytes(), 0, buffer, offset, 64);

			offset += 64;
			
			System.arraycopy(Convert.longToBytes(this.height), 0, buffer, offset, 8);
			
			offset += 8;
			
			System.arraycopy(Convert.intToByteArray(this.creator.length()), 0, buffer, offset, 4);
			
			offset += 4;
			
			System.arraycopy(this.creator.getBytes(), 0, buffer, offset, this.creator.length());
			
			offset += this.creator.length();
			
			System.arraycopy(Convert.intToByteArray(txlist.size()), 0, buffer, offset, 4);
			
			offset += 4;
		}
		
		return buffer;
	}
	
	public byte[] getBytesToBody()
	{
		int offset = 0;
		
		for(Transaction tx: this.txlist)
		{
			offset += 4;
			
			offset += tx.getBytes().length;
		}
		
		byte[] buffer = new byte[offset];
		offset = 0;
		
		for(Transaction tx: this.txlist)
		{
			System.arraycopy(Convert.intToByteArray(tx.getBytes().length), 0, buffer, offset, 4);
			
			offset += 4;
			
			System.arraycopy(Convert.intToByteArray(tx.getBytes().length), 0, buffer, offset, 4);
			
			offset += tx.getBytes().length;
		}
		
		return buffer;
	}
	
	public void setGenesis(boolean isGen) {
		this.isGenesis = isGen;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public synchronized double isProof(double difficulty)
	{
		double nonce = 0.0;
		
		BigDecimal t = BigDecimal.valueOf(256.0-difficulty);
		
		t = t.multiply(BigDecimal.valueOf(0.30102999566398119521373889472449));
		
		BigDecimal target = new BigDecimal(Math.pow(10.0, t.doubleValue()));
		
		long start = System.currentTimeMillis();
		
		while(true)
		{
			
			BigInteger resultInteger = new BigInteger(Shield.SHA256((merkleroot + nonce)),16);
			BigDecimal resultDecimal= new BigDecimal(resultInteger);
			
			if(resultDecimal.compareTo(target) < 1)
			{
				System.out.println("elapsed time: " + ((System.currentTimeMillis()-start) /1000.0) + " s");
				return ((System.currentTimeMillis()-start) /1000.0);
			}else { 
				System.out.println("NONCE("+nonce +"): " + resultDecimal.toString());
				nonce = nonce + 1;
			}
		}
	}
	
	@Override
	public String toString() {
		if(isGenesis) {
			return "Genesis Block [bid=" + bid + ", prevbid=" + prevbid + ", timestamp=" + timestamp + ", merkleroot=" + merkleroot
					+ ", height=" + height + ", creator=" + creator + ", txlist=" + txlist + ", status=" + status + "]";
		}
		return "Block [bid=" + bid + ", prevbid=" + prevbid + ", timestamp=" + timestamp + ", merkleroot=" + merkleroot
				+ ", height=" + height + ", creator=" + creator + ", txlist=" + txlist + ", status=" + status + "]";
	}
}
