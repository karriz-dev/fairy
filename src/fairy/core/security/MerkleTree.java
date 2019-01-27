package fairy.core.security;

import java.util.List;

import fairy.valueobject.managers.transaction.Transaction;

public class MerkleTree {
	public static String getMerkleRoot(List<Transaction> txlist) {
		
		String root = Shield.SHA256(txlist.get(0).getBytes());
		
		for(int i=1;i<txlist.size();i++)
		{
			root = Shield.SHA256(root + txlist.get(0).getBytes());
		}
		
		return root;
	}
}
