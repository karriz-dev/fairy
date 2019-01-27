package fairy.core.command;

public class CommandList {
	public final static String TRANSACTION_MAKE = "tx";
	public final static int TRANSACTION_MAKE_SUCCESS = 0x00000001;
	public final static int TRANSACTION_MAKE_FAILED = 0x00000002;
	
	public final static String WALLET_GENERATOR = "wg";
	public final static int WALLET_GENERATOR_SUCCESS = 0x00000011;
	public final static int WALLET_GENERATOR_FAILED = 0x00000012;
	public final static int WALLET_GENERATOR_FAILED_OVERFLOW = 0x00000013;
	
	public final static String WALLET_LIST = "wls";
	public final static int WALLET_LIST_SUCCESS = 0x00000021;
	public final static int WALLET_LIST_FAILED = 0x00000022;
	
	
	public final static int COMMAND_EXCEPTION = 0xFFFFFFFF;
}
