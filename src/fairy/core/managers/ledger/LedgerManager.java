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
		 *  ���� �����ڴ� �۷ι��� ����(BlockHeight)�� ������ �ִ´�.
		 * 
		 *  ��� ������ BlockHeight�� ����ؼ� ���Ź޴ٰ� BlockHeight�� ���� ���ŵǴ� ���� 
		 *  
		 *  �� ������ ������ ����ȭ�ϴ� �۾��� �����Ѵ�.
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
