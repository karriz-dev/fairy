package fairy.valueobject.managers.transaction;

public class TransactionOrderConfirm extends Transaction{

	private static final long serialVersionUID = -2888654177011239289L;
	
	private String buyerAddress = null;
	
	private double totalHydrogenCount = 0.0;
	private double value = 0.0;
	
	private String targetTransactionID = null;
	private String sellerAddress = null;
	
	public TransactionOrderConfirm(String id, String buyer, double total, double value, String seller) 
	{
		super(TransactionType.ORDER_CONFIRM);
		
		this.buyerAddress = buyer;
		this.totalHydrogenCount = total;
		this.value = value;
		this.targetTransactionID = id;
		this.sellerAddress = seller;
	}

	@Override
	protected byte[] getDatasBytes() {
		return null;
	}
}
