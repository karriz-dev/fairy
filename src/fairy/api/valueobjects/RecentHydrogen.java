package fairy.api.valueobjects;

public class RecentHydrogen implements Recent {
	
	private String from = null;
	private String to = null;
	
	private double value = 0.0;
	private double max = 0.0;
	private long timestamp = 0L;
	private long height = 0L;
	
	public RecentHydrogen(String from, String to, double value, double max, long timestamp, long height) {
		this.from = from;
		this.to = to;
		this.value = value;
		this.max = max;
		this.timestamp = timestamp;
		this.height = height;
	}
}
