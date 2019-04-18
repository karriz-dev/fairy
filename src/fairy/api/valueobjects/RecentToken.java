package fairy.api.valueobjects;

import java.util.Map;

public class RecentToken implements Recent{
	private String ftxaddress = null;
	private long transactionTimestamp = 0L;
	private Map<String, Double> outputList = null;
	
	public RecentToken(String ftxaddress, long transactionTimestamp, Map<String, Double> outputList) {
		this.ftxaddress = ftxaddress;
		this.transactionTimestamp = transactionTimestamp;
		this.outputList = outputList;
	}
}
