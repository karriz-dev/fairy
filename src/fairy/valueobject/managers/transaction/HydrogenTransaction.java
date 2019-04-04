package fairy.valueobject.managers.transaction;

import fairy.core.utils.Convert;

public class HydrogenTransaction extends Transaction {

	private static final long serialVersionUID = -7646520847139737802L;
	
	private String toAddress = null;
	private String fromAddress = null;
	private double hydrogen = 0.0;
	private double max = 0.0;
	
	public HydrogenTransaction() {
		super(TransactionType.HYDROGEN);
	}
	
	public HydrogenTransaction(String toAddress, String fromAddress, double hydrogen, double max) {
		super(TransactionType.HYDROGEN);
		this.toAddress = toAddress;
		this.fromAddress = fromAddress;
		this.hydrogen = hydrogen;
		this.max = max;
	}

	public String getToAddress() {
		return toAddress;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public double getHydrogen() {
		return hydrogen;
	}

	public double getMax() {
		return max;
	}

	@Override
	protected byte[] getDatasBytes() {
		byte[] toAddressBytes = toAddress.getBytes();
		byte[] fromAddressBytes = fromAddress.getBytes();
		
		byte[] result = new byte[toAddressBytes.length + fromAddressBytes.length + 16];

		int offset = 0;
		
		System.arraycopy(Convert.intToByteArray(toAddressBytes.length), 0, result, offset, 4);

		offset += 4;
		
		System.arraycopy(toAddressBytes, 0, result, offset, toAddressBytes.length);

		offset += toAddressBytes.length;
		
		System.arraycopy(Convert.intToByteArray(fromAddressBytes.length), 0, result, offset, 4);
		
		offset += 4;

		System.arraycopy(fromAddressBytes, 0, result, offset, fromAddressBytes.length);
		
		offset += fromAddressBytes.length;
		
		System.arraycopy(Convert.doubleToBytes(this.hydrogen), 0, result, offset, 8);
		
		offset += 8;

		System.arraycopy(Convert.doubleToBytes(this.max), 0, result, offset, 8);
		
		offset += 8;

		return result;
	}

	@Override
	public String toString() {
		return "HydrogenTransaction [toAddress=" + toAddress + ", fromAddress=" + fromAddress + ", hydrogen=" + hydrogen
				+ ", max=" + max + "]";
	}
}
