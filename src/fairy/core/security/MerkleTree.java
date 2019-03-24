package fairy.core.security;

import java.util.List;

import fairy.valueobject.managers.transaction.Transaction;

public class MerkleTree {
	
	public static String getMerkleRoot(List<Transaction> txlist) {
		
		String root = Shield.SHA256(txlist.get(0).toString().getBytes());
	
		for(int i=0;i<txlist.size()-1;i++)
		{
			root = Shield.SHA256(root + txlist.get(i+1).toString().getBytes());
		}
		
		return root;
	}
}
