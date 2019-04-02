package fairy.valueobject.managers.transaction;

public class TransactionDelivery extends Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3459009091708040959L;
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
