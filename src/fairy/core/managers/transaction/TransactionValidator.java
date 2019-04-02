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
			
			String tokenMerkleRoot = token.getMerkleroot();
			
			if(tokenMerkleRoot != null)
			{
				for(Block b: LedgerManager.getInstance().getBlockList())
				{
					if(tokenMerkleRoot.equals(b.getMerkleroot()))
					{
						double result = b.getBalance(token.getFtxid(), token.getFtxaddress());

						if(result == 0.0)
							return null;
						else {
							if(token.getBalance() <= result)
							{
								return result;
							}
						}
					}
				}
				return null;
			}
			else return null;
			
		case TransactionType.HYDROGEN:
			return null;
			
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
