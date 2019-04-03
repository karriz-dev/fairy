package fairy.valueobject.managers.block;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import fairy.core.security.MerkleTree;
import fairy.core.security.Shield;
import fairy.core.utils.Debugger;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class Block implements Serializable {

	public String getMerkleroot() {
		return merkleroot;
	}

	private static final long serialVersionUID = 2925905777153845523L;

	//Header
	private String bid = null;
	private String prevbid = null;
	private Long timestamp = 0L;
	private String merkleroot = null;
	
	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	private Long height = 0L;
	
	private String creator = null;
	
	private boolean isGenesis = false;
	
	private int status = 0;
	
	//Body
	private List<Transaction> txlist = null;

	public Block(String creator, List<Transaction> txlist) {
		this.creator = creator;
		this.timestamp = System.currentTimeMillis();
		this.merkleroot = MerkleTree.getMerkleRoot(txlist);
		this.txlist = txlist;
		
		this.bid = Shield.SHA256(this.merkleroot);
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public boolean isGenesis() {
		return isGenesis;
	}

	public void setPrevBid(String prevbid) {
		this.prevbid = prevbid;
	}

	public double getBalance(String address) {
		for(Transaction tx: txlist)
		{
			TokenTransaction token = (TokenTransaction)tx;
			
			for(String key: token.getOutputList().keySet())
			{
				if(key.equals(address)) {
					try {
						return token.getOutputList().get(address);
					}catch(Exception e) {
						return 0.0;
					}
				}
			}
		}
		return 0.0;
	}
	
	public double getBalance(String tid, String address) {
		for(Transaction tx: txlist)
		{
			if(tx.getTransactionID().equals(tid))
			{
				TokenTransaction token = (TokenTransaction)tx;
				
				for(String key: token.getOutputList().keySet())
				{
					if(key.equals(address)) {
						try {
							return token.getOutputList().get(address);
						}catch(Exception e) {
							return 0.0;
						}
					}
				}
			}
		}
		return 0.0;
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
				Debugger.Log(this, "elapsed time: " + ((System.currentTimeMillis()-start) /1000.0) + " s");
				return ((System.currentTimeMillis()-start) /1000.0);
			}else { 
				Debugger.Log(this, "NONCE("+nonce +"): " + resultDecimal.toString(), 1);
				nonce = nonce + 1;
			}
		}
	}
	
	public List<Transaction> getTransactionList(){
		return this.txlist;
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
