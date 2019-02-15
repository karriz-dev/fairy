package fairy.core.security.merkletree;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import fairy.valueobject.managers.transaction.Transaction;

public class MerkleTree {
	private List<Transaction> txlist = null;
	
	private MerkleNode merkleRoot = null;
	
	public MerkleTree(Queue<Transaction> txqueue)
	{
		txlist = new ArrayList<Transaction>();
		
		if(txqueue.size()%2 == 0)
		{
			for(Transaction tx: txqueue)
			{
				txlist.add(tx);
			}
		}
		else
		{
			Transaction lasttx = null;
			
			for(Transaction tx: txqueue)
			{
				txlist.add(tx);
				lasttx = tx;
			}
			
			txlist.add(lasttx);
		}
	}
	
	public boolean Generate()
	{
		MerkleNode node = new MerkleNode(txlist.get(0).getBytes());
		
		return false;
	}
}
