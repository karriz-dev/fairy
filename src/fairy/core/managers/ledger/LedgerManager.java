package fairy.core.managers.ledger;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fairy.core.managers.block.BlockManager;
import fairy.core.utils.Debugger;
import fairy.valueobject.managers.block.Block;
import fairy.valueobject.managers.transaction.TokenTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class LedgerManager{
	
	private Long currentBlockHeight = 0L;

	private static LedgerManager instance = null;

	private LedgerManager()
	{
		if(generateGenesisBlock())
		{
			Debugger.Log(this, "genesis block is generate !!");
		}
	}

	public boolean generateBlock(Block block)
	{
		if(setPrevBlockID(block) > 0)
		{
			if(block.getHeight() > currentBlockHeight)
			{
				if(BlockManager.getInstance().Generate(block))
				{
					block.setStatus(0xB0000002);
					currentBlockHeight = currentBlockHeight + 1;
					return true;
				}
				else return false;
			}
			else return false;
		}
		else return false;
	}
	
	private boolean generateGenesisBlock()
	{
		try {
			List<Transaction> txlist = new ArrayList<Transaction>();
			
			Map<String, Double> tokenMap = new HashMap<String, Double>();
			tokenMap.put("GENESIS_TRANSACTION", 100810081008.0);
			
			Transaction tx = new TokenTransaction("GENESIS_MERKLE_ROOT", "GENESIS_TRANSACTION_ID", "GENESIS_TRANSACTION_ADDRESS", tokenMap);
			
			txlist.add(tx);
			
			Block block = new Block("GENESIS BLOCK", txlist);
			
			block.setGenesis(true);
			block.setStatus(0xB0000004);

			return BlockManager.getInstance().Generate(block);
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}	
	}
	
	public long getCurrentBlockHeight()
	{
		return this.getCurrentBlockHeight();
	}
	
	private Long setPrevBlockID(Block currentBlock)
	{
		Block latestBlock = getLatestBlock();
		
		Debugger.Log(this, "PREV BLOCK ID: " + latestBlock.getBid());
		
		currentBlock.setPrevBid(getLatestBlock().getBid());
		
		currentBlock.setHeight(latestBlock.getHeight() + 1);
		
		return currentBlock.getHeight();
	}
	
	public List<Block> getBlockList()
	{
		List<Block> blockList = new ArrayList<Block>();
		
		try {
			File folder = new File("assets/blocks/");
			
			for (final File fileEntry : folder.listFiles()) {
		        if (!fileEntry.isDirectory()) {
		        	blockList.add(BlockManager.getInstance().getBlock(fileEntry));
		        }
		    }

			return blockList;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return null;
		}
	}
	
	public Block getLatestBlock()
	{
		try {
			File fl = new File("assets/blocks/");
						
		    File[] files = fl.listFiles(new FileFilter() {          
		        public boolean accept(File file) {
		            return file.isFile();
		        }
		    });
		    
		    long lastMod = Long.MIN_VALUE;
		    
		    File choice = null
		    		;
		    for (File file : files) {
		        if (file.lastModified() > lastMod) {
		            choice = file;
		            lastMod = file.lastModified();
		        }
		    }
		 
			return BlockManager.getInstance().getBlock(choice);
		}catch(Exception e) {
			Debugger.Log(this, e);
			return null;
		}
	}
	
	public static LedgerManager getInstance() {
		if(instance==null) {
			instance = new LedgerManager();
		}
		return instance;
	}
}
