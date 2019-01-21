package fairy.core.managers.block;

import java.io.File;

import fairy.core.utils.Debugger;

public class BlockManager {
	private BlockManager() {
		
	}
	
	public boolean generatorGenesis()
	{
		try {
			
			File file = new File("assets/block/genesis.fairyblock");
			if(!file.exists())
			{
				file.createNewFile();
				return true;
			}else return false;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
}
