package fairy.valueobject.managers.transaction;

import java.util.Map;

import fairy.core.utils.Convert;

public class TokenTransaction extends Transaction {
	
	// INPUT
	private String ftxid = null;
	private String ftxaddress = null;
		
	// OUTPUT
	private Map<String, Double> outputList = null;
	
	public TokenTransaction(String ftxid, String ftxaddress, Map<String, Double> outputList) {
		super(TransactionType.TOKEN);
		
		this.ftxid = ftxid;
		this.ftxaddress = ftxaddress;
		this.outputList = outputList;
	}
	
	public double getBalance() {
		double balance = 0.0;
		for(String key: outputList.keySet())
		{
			balance += outputList.get(key);	
		}
		return balance;
	}

	@Override
	protected byte[] getDatasBytes() {
		
		byte[] ftxidByte = ftxid.getBytes();
		byte[] ftxidAddressByte = ftxaddress.getBytes();
		
		byte[] result = new byte[8 + ftxidByte.length + ftxidAddressByte.length];
		
		System.arraycopy(Convert.intToByteArray(ftxidByte.length), 0, result, 0, 4);

		System.arraycopy(ftxidByte, 0, result, 4, ftxidByte.length);

		System.arraycopy(Convert.intToByteArray(ftxidAddressByte.length), 0, result, 4 + ftxidByte.length, 4);

		System.arraycopy(ftxidAddressByte, 0, result, 8 + ftxidByte.length, ftxidAddressByte.length);

		return result;
	}

}
