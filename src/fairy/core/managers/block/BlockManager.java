package fairy.core.managers.block;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import fairy.core.utils.Debugger;
import fairy.valueobject.managers.block.Block;

public class BlockManager{

	private static BlockManager instance = null;
	
	private BlockManager() {}
	
	public boolean Generate(Block block)
	{
		try {
			File file = new File("assets/blocks/" + block.getBid() + ".block");
			
			block.setStatus(0xB0000001);
			
			if(!file.exists())
			{
				if(file.createNewFile())
				{
					FileOutputStream output = new FileOutputStream(file);
					ObjectOutputStream oos = new ObjectOutputStream(output);
					
					oos.writeObject(block);
					
					oos.close();
					output.flush();
					output.close();
					return true;
				}
				else return false;
			}else return false;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
	
	public Block getBlock(String blockID)
	{
		try {
			File file = new File("assets/blocks/" + blockID + ".block");
			if(file.exists())
			{
				FileInputStream input = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(input);
				
				Block result = (Block)ois.readObject();
				
				ois.close();
				
				return result;

			}else return null;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return null;
		}
	}
	
	public Block getBlock(File block)
	{
		try {
			if(block.exists())
			{
				FileInputStream input = new FileInputStream(block);
				ObjectInputStream ois = new ObjectInputStream(input);
				
				Block result = (Block)ois.readObject();
				
				ois.close();
				
				return result;

			}else return null;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return null;
		}
	}
	
	public static BlockManager getInstance()
	{
		if(instance==null) {
			instance = new BlockManager();
		}
		return instance;
	}
}
