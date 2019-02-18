package fairy.valueobject.managers.block;

import java.util.LinkedList;
import java.util.Queue;

import fairy.valueobject.managers.transaction.StatusTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class BlockTester {
	public static void main(String[] args) {
		Queue<Transaction> txqueue = new LinkedList<Transaction>();
		Transaction tx = new StatusTransaction();
		
		txqueue.add(tx);
		txqueue.add(tx);
		txqueue.add(tx);
		txqueue.add(tx);
		txqueue.add(tx);
		txqueue.add(tx);
		
		Block block = new Block("127.0.0.1", txqueue);
		block.setGenesis(true);
		
		if(block.Create()) {
			System.out.println(block.toString());
			System.out.println(block.getFileBytes().length);
			//String bid = block.bid;
			//Block block2 = new Block("assets/blocks/" + bid + ".block");
		
			//byte[] read = block2.getFileBytes();
		
			//System.out.println("Block Size: " + read.length);
		
		}else System.out.println("블록 생성에 실패하였습니다. 이미 생성된 블록이 존재합니다.");
	}
}
