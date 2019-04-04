package fairy.api.valueobjects;

import java.util.Map;

public class RecentToken implements Recent{
	private String ftxaddress = null;
	private Map<String, Double> outputList = null;
	private long timestamp = 0L;
	private long height = 0L;
	
	public RecentToken(String ftxaddress, Map<String, Double> outputList, long timestamp, long height) {
		this.ftxaddress = ftxaddress;
		this.outputList = outputList;
		this.timestamp = timestamp;
		this.height = height;
	}
}
