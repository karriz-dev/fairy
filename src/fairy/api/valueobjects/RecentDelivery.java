package fairy.api.valueobjects;

public class RecentDelivery implements Recent {
	private String startPosition = null;
	private String endPosition = null;
	private long transactionTimestamp = 0L;
	private double hydrogenValue = 0.0;
	public RecentDelivery(String startPosition, String endPosition, long transactionTimestamp, double hydrogenValue) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.transactionTimestamp = transactionTimestamp;
		this.hydrogenValue = hydrogenValue;
	}
}
