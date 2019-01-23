package fairy.demo;

import fairy.core.command.CommandLayout;
import fairy.core.command.CommandList;
import fairy.core.net.communicator.Linker;
import fairy.core.net.communicator.Session;

public class FairyClient {
	public static void main(String[] args) {
		
		CommandList.test();
		
		/*Session.getInstance();
		
		// waiting session is opened
		while(Session.getInstance().isOpened());

		// Linker Call
		Linker.getInstance();
		
		//CommandLineInterface Layout Call
		CommandLayout.getInstance();*/
	}
}
