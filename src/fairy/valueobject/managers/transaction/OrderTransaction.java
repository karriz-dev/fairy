package fairy.valueobject.managers.transaction;

import fairy.core.utils.Convert;

public class OrderTransaction extends Transaction{

	private static final long serialVersionUID = -3277318114493116366L;

	private String buyerAddress = null;
	private short hydrogenType = 0;
	private long maxTime = 0;
	
	public OrderTransaction(String buyerAddress, short hydrogenType, long maxTime) {
		super(TransactionType.ORDER);
		
		this.buyerAddress = buyerAddress;
		this.hydrogenType = hydrogenType;
		this.maxTime = maxTime;
	}
	
	public String getBuyerAddress() {
		return buyerAddress;
	}

	public short getHydrogenType() {
		return hydrogenType;
	}

	public long getMaxTime() {
		return maxTime;
	}

	@Override
	protected byte[] getDatasBytes() {
		byte[] buyerAddressBytes = buyerAddress.getBytes();

		byte[] result = new byte[14 + buyerAddressBytes.length];
		
		int offset = 0;
		System.arraycopy(Convert.intToByteArray(buyerAddressBytes.length), 0, result, offset, 4);

		offset+=4;
		
		System.arraycopy(buyerAddressBytes, 0, result, offset, buyerAddressBytes.length);
		
		offset += buyerAddressBytes.length;

		System.arraycopy(Convert.ShortToByteArray(hydrogenType), 0, result, offset, 2);

		offset += 2;
		
		System.arraycopy(Convert.longToBytes(maxTime), 0, result, offset, 8);

		return result;
	}
	
}
