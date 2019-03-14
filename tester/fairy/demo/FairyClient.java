package fairy.demo;

import fairy.api.APIServer;
import fairy.core.command.CommandLayout;
import fairy.core.managers.block.BlockManager;
import fairy.core.managers.key.KeyManager;
import fairy.core.net.communicator.Linker;
import fairy.core.net.communicator.Session;
import fairy.core.utils.Network;

public class FairyClient {
	public static void main(String[] args) {
		
		// KeyManager Call
		KeyManager.getInstance("assets/wallet/wallet.fairy");

		// BlockManager Call
		BlockManager.getInstance();
						
		// API SERVER OPEN
		APIServer.getInstance();
		
		// SESSION OPEN
		Session.getInstance();
		
		// waiting session is opened
		while(Session.getInstance().isOpened());

		// Linker Call
		Linker.getInstance();
	}
}
