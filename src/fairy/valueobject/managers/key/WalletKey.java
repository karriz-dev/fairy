package fairy.valueobject.managers.key;

import java.io.Serializable;
import java.security.KeyPair;

public class WalletKey implements Serializable {

	private static final long serialVersionUID = -5252856728146061474L;

	private KeyPair pair = null;
	private String address = null;
	
	public WalletKey(KeyPair pair, String address) {
		this.pair = pair;
		this.address = address;
	}

	public KeyPair getPair() {
		return pair;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "WalletKey [pair=" + pair + ", address=" + address + "]";
	}
}
