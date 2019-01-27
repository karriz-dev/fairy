package fairy.valueobject.managers.block;

import java.util.List;

import fairy.core.security.MerkleTree;
import fairy.valueobject.managers.transaction.Transaction;

public class Block {
	//Header
	private String blockid = null;
	private Long timestamp = 0L;
	private String merkleroot = null;
	
	private String creator = null;
	
	//Body
	private List<Transaction> txlist = null;
	
	public Block(String blockPath){
		
	}

	public Block(String creator, List<Transaction> txlist) {
		this.creator = creator;
		this.timestamp = System.currentTimeMillis();
		this.merkleroot = MerkleTree.getMerkleRoot(txlist);
		this.txlist = txlist;
		
		System.out.println(merkleroot);
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
