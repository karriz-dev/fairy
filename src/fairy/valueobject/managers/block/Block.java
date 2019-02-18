package fairy.valueobject.managers.block;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Queue;

import fairy.core.security.MerkleTree;
import fairy.core.security.Shield;
import fairy.core.utils.Convert;
import fairy.core.utils.Debugger;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class Block {
	//Header
	public String bid = null;				// 64자리 숫자
	private String prevbid = null;
	private Long timestamp = 0L;
	private String merkleroot = null;
	
	private Long height = 0L;
	
	private String creator = null;
	
	private boolean isGenesis = false;
	
	//Body
	private Queue<Transaction> txqueue = null;
	
	//File 
	private File blockFile = null;
	
	public Block(String blockPath){
		blockFile = new File(blockPath);
	}

	public Block(String creator, Queue<Transaction> txqueue) {
		this.creator = creator;
		this.timestamp = System.currentTimeMillis();
		this.merkleroot = MerkleTree.getMerkleRoot(txqueue);
		this.txqueue = txqueue;
		
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
	
	public double getBalance() {
		double balance = 0.0;
		for(Transaction tx: txqueue)
		{
			TokenTransaction token = (TokenTransaction)tx;
			
			balance += token.getBalance();
		}
		return balance;
	}
	
	public byte[] getBytes()
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
			
			System.arraycopy(Convert.intToByteArray(txqueue.size()), 0, buffer, offset, 4);
			
			offset += 4;
		}
		else
		{
			buffer = new byte[1 + 64 + 8 + 64 + 8 + 4 + creatorBuffer.length + 4];
			
			int offset = 0;
			
			System.arraycopy(Convert.booleanToByte(this.isGenesis), 0, buffer, offset, 1);
			
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
			
			System.arraycopy(Convert.intToByteArray(txqueue.size()), 0, buffer, offset, 4);
			
			offset += 4;
		}
		
		return buffer;
	}
	
	public void setGenesis(boolean isGen) {
		this.isGenesis = isGen;
	}
	
	public byte[] getFileBytes() {
		try {
			FileInputStream fis = new FileInputStream(blockFile);
			return fis.readAllBytes();
		}catch(Exception e) {
			return null;
		}	
	}
	
	
	@Override
	public String toString() {
		return "Block [bid=" + bid + ", prevbid=" + prevbid + ", timestamp=" + timestamp + ", merkleroot=" + merkleroot
				+ ", height=" + height + ", creator=" + creator + ", txqueue=" + txqueue + ", blockFile=" + blockFile
				+ "]";
	}
}
