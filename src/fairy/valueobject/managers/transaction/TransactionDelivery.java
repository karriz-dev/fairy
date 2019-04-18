package fairy.valueobject.managers.transaction;

import fairy.core.utils.Convert;

public class TransactionDelivery extends Transaction{

	private static final long serialVersionUID = -3459009091708040959L;

	private String startPoisition = null;
	private String endPosition = null;
	private double hydrogenValue = 0.0;
	
	public TransactionDelivery() {
		super(TransactionType.DELIVERY);
	}

	public TransactionDelivery(String startPoisition, String endPosition, double hydrogenValue) {
		super(TransactionType.DELIVERY);
		this.startPoisition = startPoisition;
		this.endPosition = endPosition;
		this.hydrogenValue = hydrogenValue;
	}

	public String getStartPoisition() {
		return startPoisition;
	}

	public String getEndPosition() {
		return endPosition;
	}

	public double getHydrogenValue() {
		return hydrogenValue;
	}

	@Override
	protected byte[] getDatasBytes() {
		byte[] startPoisitionBytes = startPoisition.getBytes();
		byte[] endPositionBytes = endPosition.getBytes();
		
		byte[] result = new byte[16 + startPoisitionBytes.length + endPositionBytes.length];
		
		int offset = 0;
		System.arraycopy(Convert.intToByteArray(startPoisitionBytes.length), 0, result, offset, 4);

		offset+=4;
		
		System.arraycopy(startPoisitionBytes, 0, result, offset, startPoisitionBytes.length);
		
		offset += startPoisitionBytes.length;

		System.arraycopy(Convert.intToByteArray(endPositionBytes.length), 0, result, offset, 4);

		offset += 4;
		
		System.arraycopy(endPositionBytes, 0, result, offset, endPositionBytes.length);
		
		offset += endPositionBytes.length;
		
		System.arraycopy(Convert.doubleToBytes(hydrogenValue), 0, result, offset, 8);

		return result;
	}

	@Override
	public String toString() {
		return "TransactionDelivery [startPoisition=" + startPoisition + ", endPosition=" + endPosition
				+ ", hydrogenValue=" + hydrogenValue + "]";
	}
	
	
}
