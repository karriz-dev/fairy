package fairy.valueobject.managers.transaction;

import fairy.core.utils.Convert;

public class StatusTransaction extends Transaction {

	public StatusTransaction() {
		super(TransactionType.STATUS);
	}
	
	public StatusTransaction(byte[][] stream) {
		super(stream);
	}

	@Override
	protected byte[] getDatasBytes() {

		byte[] result = new byte[4 + this.length];
		
		System.arraycopy(Convert.intToByteArray(this.length), 0, result, 0, 4);
		
		System.arraycopy(this.datas, 0, result, 4, this.length);

		return result;
	}
	
	@Override
	public String toString()
	{
		return this.tid + "/" + this.version  + "(" +this.versionlength + ")" + "/" + new String(this.datas);
	}
}
