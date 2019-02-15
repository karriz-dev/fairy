package fairy.valueobject.managers.block;

import java.io.File;
import java.io.FileInputStream;
import java.util.Queue;

import fairy.core.security.MerkleTree;
import fairy.core.utils.Debugger;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class Block {
	//Header
	private String bid = null;
	private String prevbid = null;
	private Long timestamp = 0L;
	private String merkleroot = null;
	
	private Long height = 0L;
	
	private String creator = null;
	
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
	}

	public boolean Create() {
		try {
			File file = new File(bid + ".block");
			if(!file.exists())
			{
				// block header 저장 
				
				// block body 저장
			}else return false;
			return true;
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
	
	public byte[] getBytes() {
		try {
			FileInputStream fis = new FileInputStream(blockFile);
			return fis.readAllBytes();
		}catch(Exception e) {
			return null;
		}
		
	}
}
