package fairy.core.security;

import java.util.Queue;

import fairy.valueobject.managers.transaction.Transaction;

public class MerkleTree {
	public static String getMerkleRoot(Queue<Transaction> txqueue) {
		String root = Shield.SHA256(txqueue.poll().getBytes());
		
		while(!txqueue.isEmpty())
		{
			root = Shield.SHA256(txqueue.poll().getBytes());
		}
		
		return root;
	}
}
