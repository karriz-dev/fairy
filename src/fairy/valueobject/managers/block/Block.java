package fairy.valueobject.managers.block;

import java.util.List;
import java.util.Queue;

import fairy.core.security.MerkleTree;
import fairy.valueobject.managers.transaction.Transaction;

public class Block {
	//Header
	private String blockid = null;
	private String prevblockid = null;
	private Long timestamp = 0L;
	private String merkleroot = null;
	
	private String creator = null;
	
	//Body
	private Queue<Transaction> txqueue = null;
	
	public Block(String blockPath){
		
	}

	public Block(String creator, Queue<Transaction> txqueue) {
		this.creator = creator;
		this.timestamp = System.currentTimeMillis();
		this.merkleroot = MerkleTree.getMerkleRoot(txqueue);
		this.txqueue = txqueue;
	}

	public boolean saveBlock() {
		return false;
	}

	public boolean loadBlock() {
		return false;
	}
	
	public double getFees() {
		return 0.0f;
	}
}
