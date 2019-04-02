package fairy.valueobject.managers.transaction;

public class TransactionOrderConfirm extends Transaction{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2888654177011239289L;
	private String targetTransactionID = null;
	private String buyerAddress = null;
	private double totalHydrogenCount = 0.0;
	private double value = 0.0;
	private String sellerAddress = null;
	
	public TransactionOrderConfirm() {
		super(TransactionType.ORDER_CONFIRM);
	}

	@Override
	protected byte[] getDatasBytes() {
		// TODO Auto-generated method stub
		return null;
	}

}
