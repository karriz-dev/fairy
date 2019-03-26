package fairy.valueobject.managers.transaction;

public class HydrogenTransaction extends Transaction {

	private int hydrogen = 0;
	
	public HydrogenTransaction() {
		super(TransactionType.HYDROGEN);
		// for testing...
	}

	@Override
	protected byte[] getDatasBytes() {
		return null;
	}

}
