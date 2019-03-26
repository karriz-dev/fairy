package fairy.core.managers.ledger;

import java.io.File;
import java.io.FileFilter;

import fairy.core.utils.Debugger;
import fairy.valueobject.managers.block.Block;

public class LedgerManager {
	private static LedgerManager instance = null;
	
	private LedgerManager()
	{
		/*
		 * 	LedgerManager
		 * 
		 *  렛저 관리자는 글로벌한 상태(BlockHeight)를 가지고 있는다.
		 * 
		 *  모든 노드들의 BlockHeight를 계속해서 수신받다가 BlockHeight가 새로 갱신되는 순간 
		 *  
		 *  각 노드들은 렛저를 동기화하는 작업을 진행한다.
		 * */
	}
	
	public void setPrevBlockID(Block currentBlock)
	{
		currentBlock.setBid(getLatestBlock().getBid());
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
		    File choice = null;
		    for (File file : files) {
		        if (file.lastModified() > lastMod) {
		            choice = file;
		            lastMod = file.lastModified();
		        }
		    }
		    
			return new Block(choice);
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
