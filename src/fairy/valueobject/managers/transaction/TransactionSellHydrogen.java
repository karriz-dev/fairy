package fairy.valueobject.managers.transaction;

import fairy.core.utils.Convert;

public class TransactionSellHydrogen extends Transaction{

	private static final long serialVersionUID = -8746706525486310716L;
	
	private String hydrogenTransactionID = null;
	
	private String buyerAddress = null;
	
	private String sellerAddress = null;
	
	private double biddingPrice = 0.0;
	private double biddingCount = 0.0;
	
	public TransactionSellHydrogen(String hydrogenTransactionID, String buyerAddress, String sellerAddress,
			double biddingPrice, double biddingCount) {
		super(TransactionType.SELL_HYDROGEN);
		this.hydrogenTransactionID = hydrogenTransactionID;
		this.buyerAddress = buyerAddress;
		this.sellerAddress = sellerAddress;
		this.biddingPrice = biddingPrice;
		this.biddingCount = biddingCount;
	}

	@Override
	protected byte[] getDatasBytes() {
		byte[] hydrogenTransactionIDBytes = hydrogenTransactionID.getBytes();
		byte[] buyerAddressBytes = buyerAddress.getBytes();
		byte[] sellerAddressBytes = sellerAddress.getBytes();
		
		byte[] result = new byte[28 + hydrogenTransactionIDBytes.length + buyerAddressBytes.length + sellerAddressBytes.length];
		
		int offset = 0;
		
		System.arraycopy(Convert.intToByteArray(hydrogenTransactionIDBytes.length), 0, result, offset, 4);
		
		offset += 4;
		
		System.arraycopy(hydrogenTransactionIDBytes, 0, result, offset, hydrogenTransactionIDBytes.length);
		
		offset += hydrogenTransactionIDBytes.length;
		
		System.arraycopy(Convert.intToByteArray(buyerAddressBytes.length), 0, result, offset, 4);
		
		offset += 4;
		
		System.arraycopy(buyerAddressBytes, 0, result, offset, buyerAddressBytes.length);
		
		offset += buyerAddressBytes.length;
		
		System.arraycopy(Convert.intToByteArray(sellerAddressBytes.length), 0, result, offset, 4);
		
		offset += 4;
		
		System.arraycopy(sellerAddressBytes, 0, result, offset, sellerAddressBytes.length);
		
		offset += sellerAddressBytes.length;
		
		System.arraycopy(Convert.doubleToBytes(biddingPrice), 0, result, offset, 8);
		
		offset += 8;
		
		System.arraycopy(Convert.doubleToBytes(biddingCount), 0, result, offset, 8);
		
		return result;
	}

	@Override
	public String toString() {
		return "TransactionSellHydrogen [hydrogenTransactionID=" + hydrogenTransactionID + ", buyerAddress="
				+ buyerAddress + ", sellerAddress=" + sellerAddress + ", biddingPrice=" + biddingPrice
				+ ", biddingCount=" + biddingCount + "]";
	}

	public String getHydrogenTransactionID() {
		return hydrogenTransactionID;
	}

	public void setHydrogenTransactionID(String hydrogenTransactionID) {
		this.hydrogenTransactionID = hydrogenTransactionID;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public double getBiddingPrice() {
		return biddingPrice;
	}

	public void setBiddingPrice(double biddingPrice) {
		this.biddingPrice = biddingPrice;
	}

	public double getBiddingCount() {
		return biddingCount;
	}

	public void setBiddingCount(double biddingCount) {
		this.biddingCount = biddingCount;
	}
}
