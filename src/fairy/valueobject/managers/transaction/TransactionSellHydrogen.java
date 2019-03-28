package fairy.valueobject.managers.transaction;

public class TransactionSellHydrogen extends Transaction{

	String targetTransactionID = null;
	double totalHydrogenCount = 0.0;
	double value = 0.0;
	String sellerAddress = null;
	
	public TransactionSellHydrogen() {
		super(TransactionType.SELL_HYDROGEN);
	}
	
	@Override
	protected byte[] getDatasBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
