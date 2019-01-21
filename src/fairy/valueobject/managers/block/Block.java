package fairy.valueobject.managers.block;

import java.util.List;

import fairy.valueobject.managers.transaction.Transaction;

public class Block {
	//Header
	private String blockid = null;
	private Long timestamp = 0L;
	private String merkleroot = null;
	
	//Body
	private List<Transaction> txlist = null;

	
	public Block(String blockid, Long timestamp, String merkleroot, List<Transaction> txlist) {
		super();
		this.blockid = blockid;
		this.timestamp = timestamp;
		this.merkleroot = merkleroot;
		this.txlist = txlist;
	}

	public Block(String filePath) {
		
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
