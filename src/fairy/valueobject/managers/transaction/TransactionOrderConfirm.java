package fairy.valueobject.managers.transaction;

public class TransactionOrderConfirm extends Transaction{
	
	
	String targetTransactionID = null;
	String buyerAddress = null;
	double totalHydrogenCount = 0.0;
	double value = 0.0;
	String sellerAddress = null;
	
	public TransactionOrderConfirm() {
		super(TransactionType.ORDER_CONFIRM);
	}

	@Override
	protected byte[] getDatasBytes() {
		// TODO Auto-generated method stub
		return null;
	}

}
