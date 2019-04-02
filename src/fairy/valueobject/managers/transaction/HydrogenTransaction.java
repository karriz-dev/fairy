package fairy.valueobject.managers.transaction;

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
		return null;
	}

	@Override
	public String toString() {
		return "HydrogenTransaction [address=" + address + ", hydrogen=" + hydrogen + "]";
	}
}
