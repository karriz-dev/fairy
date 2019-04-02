package fairy.valueobject.managers.transaction;

import fairy.core.utils.Convert;

public class HydrogenTransaction extends Transaction {

	private static final long serialVersionUID = -7646520847139737802L;
	
	private String address = null;
	private double hydrogen = 0.0;
	private double max = 0.0;
	
	public HydrogenTransaction() {
		super(TransactionType.HYDROGEN);
	}
	
	public HydrogenTransaction(String address, double hydrogen, double max) {
		super(TransactionType.HYDROGEN);
		
		this.address = address;
		this.hydrogen = hydrogen;
		this.max = max;
	}

	@Override
	protected byte[] getDatasBytes() {
		byte[] addressBytes = address.getBytes();
		
		byte[] result = new byte[addressBytes.length + 16];
		
		System.arraycopy(addressBytes, 0, result, 0, addressBytes.length);

		System.arraycopy(Convert.doubleToBytes(this.hydrogen), 0, result, addressBytes.length, 8);

		System.arraycopy(Convert.doubleToBytes(this.max), 0, result, addressBytes.length + 8, 8);

		return null;
	}

	@Override
	public String toString() {
		return "HydrogenTransaction [address=" + address + ", hydrogen=" + hydrogen + "]";
	}
}
