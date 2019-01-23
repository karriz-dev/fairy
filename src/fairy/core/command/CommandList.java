package fairy.core.command;

public class CommandList {
	
	// tx make arg1
	// arg1 = transaction type
	public final static String TRANSACTION_MAKE = "tx";
	
	public final static String WALLET_CREATE = "wc";
	
	public final static String WALLET_LIST = "wls";
	
	public CommandList(String commanmd, String... args)
	{
		// tx -m key_index target_address 
		
		// w -g key_count
		
		// w -ls
	}
	
	public static boolean GeneratorCommandList()
	{
		// Options transactionOption = new Options();
		// options.add("m", "make", "transaction make & broadcasting");
		// options.add("");
		// Command transactionCommand = new Command(Options options);
		
		return false;
	}
	
	public static String[] parseCommand(String strLine)
	{
		String[] args = strLine.split(" ");
		String[] result = new String[args.length+1];
		
		result[0] = "transaction type";
		
		int count = 0;
		
		for(String arg: args)
		{
			result[count++] = arg;
		}
		
		return result;
	}
}
