package fairy.valueobject.managers.transaction;

public class HydrogenTransaction extends Transaction {

	private String address = null;
	private double hydrogen = 0;
	
	public HydrogenTransaction() {
		super(TransactionType.HYDROGEN);
	}
	
	public HydrogenTransaction(String address, double hydrogen) {
		super(TransactionType.HYDROGEN);
		this.address = address;
		this.hydrogen = hydrogen;
	}

	@Override
	protected byte[] getDatasBytes() {
		return null;
	}

	@Override
	public String toString() {
		return "HydrogenTransaction [address=" + address + ", hydrogen=" + hydrogen + "]";
	}
}
