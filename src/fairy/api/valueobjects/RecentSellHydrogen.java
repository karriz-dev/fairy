package fairy.api.valueobjects;

public class RecentSellHydrogen implements Recent {
	private double biddingPrice = 0.0;
	private double biddingCount = 0.0;
	public RecentSellHydrogen(double biddingPrice, double biddingCount) {
		this.biddingPrice = biddingPrice;
		this.biddingCount = biddingCount;
	}
}
