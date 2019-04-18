package fairy.api.valueobjects;

public class RecentHydrogen implements Recent {
	
	private String from = null;
	private String to = null;
	private long transactionTimestamp = 0L;
	private double value = 0.0;
	private double max = 0.0;

	public RecentHydrogen(String from, String to, long transactionTimestamp, double value, double max) {
		this.from = from;
		this.to = to;
		this.transactionTimestamp = transactionTimestamp;
		this.value = value;
		this.max = max;
	}
}
