package fairy.core.managers.transaction;

import fairy.core.managers.ledger.LedgerManager;
import fairy.valueobject.managers.block.Block;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;
import fairy.valueobject.managers.transaction.TransactionType;

public class TransactionValidator {
	
	private static TransactionValidator instance = null;
	
	private TransactionValidator(){}
	
	public synchronized Object Excute(Transaction tx)
	{
		switch(tx.getType())
		{
		case TransactionType.TOKEN:
			TokenTransaction token = (TokenTransaction)tx;
			
			String tokenMerkleRoot = token.getMerkleRoot();
			
			if(tokenMerkleRoot != null)
			{
				for(Block b: LedgerManager.getInstance().getBlockList())
				{
					if(tokenMerkleRoot.equals(b.getMerkleRoot()))
					{
						return b.getBalance(token.getFtxid(), token.getFtxaddress());
					}
				}
				return null;
			}
			else return null;
			
			default:
				return null;
		}
	}
	
	public synchronized static TransactionValidator getInstance() {
		if(instance == null) {
			instance = new TransactionValidator();
		}
		return instance;
	}
}
