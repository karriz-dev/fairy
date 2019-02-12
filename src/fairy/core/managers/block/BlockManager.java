package fairy.core.managers.block;

import java.util.HashMap;
import java.util.Map;

public class BlockManager extends Thread {
	
	public final String genesisTokenAddress = "";
	public final Double genesisTokenAmount = 10081008.0;
	
	private Map<String, Double> genesisTokenMap = null;
	
	private static BlockManager instance = null;
	
	private BlockManager() {
		genesisTokenMap = new HashMap<String, Double>();
		if(generatorGenesis())
		{
			this.start();
		}
	}
	
	@Override
	public void run()
	{
		System.out.println("START OK");
	}
	
	private boolean generatorGenesis()
	{
		/*String blockid = "";
		
		Long timestamp = 1548065280L;
				
		genesisTokenMap.put("0x00", 10081008.0);
		
		List<Transaction> txlist = new ArrayList<Transaction>();

		Transaction tx = new TokenTransaction(genesisTokenMap);
		
		txlist.add(tx);
		
		String merkleroot = MerkleTree.getMerkleRoot(txlist);
				
		Block block = new Block(blockid, timestamp, merkleroot, txlist);*/
		
		return false;
	}
	
	public static BlockManager getInstance()
	{
		if(instance==null) {
			instance = new BlockManager();
		}
		return instance;
	}
}
