package fairy.valueobject.managers.transaction;

public class TransactionDelivery extends Transaction{

	private String startPoisition = null;
	private String endPosition = null;
	private double hydrogenValue = 0.0;
	
	public TransactionDelivery() {
		super(TransactionType.DELIVERY);
	}

	@Override
	protected byte[] getDatasBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
