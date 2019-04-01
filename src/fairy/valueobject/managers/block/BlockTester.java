package fairy.valueobject.managers.block;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fairy.valueobject.managers.transaction.Transaction;

public class BlockTester {
	private static double difficulty = 19.7;
	private static double targetTime = 3.0;
	
	public static void main(String[] args) {
		Queue<Transaction> txqueue = new LinkedList<Transaction>();
		//Transaction tx = new StatusTransaction();
		
		//txqueue.add(tx);
		
		List<Transaction> txlist = new ArrayList<Transaction>();
		
		while(!txqueue.isEmpty()) {
			txlist.add(txqueue.poll());
		}
		
		Block block = new Block("127.0.0.1", txlist);
		block.setGenesis(true);
		block.setStatus(0xB0000004);
		
		while(true)
		{
			double currentTime = block.isProof(difficulty);
			
			if(currentTime >= 0.0)
			{
				// 블록 파일 생성 !
			}
			
			if(currentTime < targetTime)
			{
				difficulty = difficulty + 0.01;
			}else{
				difficulty = difficulty - 0.01;
			}
			
			System.out.println("현재 난이도: " + difficulty);
			try {
				Thread.sleep(1500);
			}catch(Exception e) {
				
			}
		}
	}
}
