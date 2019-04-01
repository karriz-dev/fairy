package fairy.valueobject.managers.transaction;

public class TransactionSellHydrogen extends Transaction{

	private String targetTransactionID = null;
	private double totalHydrogenCount = 0.0;
	private double value = 0.0;
	private String sellerAddress = null;
	
	public TransactionSellHydrogen() {
		super(TransactionType.SELL_HYDROGEN);
	}
	
	@Override
	protected byte[] getDatasBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
}