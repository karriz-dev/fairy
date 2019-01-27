package fairy.core.command;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import fairy.core.managers.key.KeyManager;
import fairy.core.net.communicator.Linker;
import fairy.core.utils.Debugger;
import fairy.valueobject.managers.transaction.StatusTransaction;
import fairy.valueobject.managers.transaction.Transaction;

public class CommandLayout extends JFrame {
	private JTextField textField = null;
	private JTextArea textArea = null;
	
	private static CommandLayout instance = null;
	
	private CommandLayout()
	{
		super("Fairy - Command Line Interface [test.v]");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);
		getContentPane().setLayout(null);
		
		setSize(947,681);
		
		textField = new JTextField();
		textField.setBounds(78, 606, 841, 26);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandWithArgs = textField.getText().split(" ");
				
				switch(ExcuteCommand(commandWithArgs))
				{
				case CommandList.WALLET_GENERATOR_SUCESS:
					textField.setBackground(new Color(159, 244, 180));
					textField.setText("");
					break;
					
				case CommandList.WALLET_GENERATOR_FAILED:
					textField.setBackground(new Color(255, 149, 149));
					textField.setText("");
				}
			}
		});
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(12, 10, 907, 586);
		getContentPane().add(textArea);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		textArea.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		textField.setBorder(BorderFactory.createCompoundBorder(border, 
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		JLabel lblCommand = new JLabel("Command");
		lblCommand.setFont(new Font("±¼¸²", Font.PLAIN, 12));
		lblCommand.setBounds(12, 611, 73, 17);
		getContentPane().add(lblCommand);
	
		setVisible(true);
	}
	
	private int ExcuteCommand(String[] commandWithArgs)
	{
		String command = commandWithArgs[0];
		String[] args = new String[commandWithArgs.length-1];
		
		int count = 0;
		
		for(String arg:commandWithArgs)
		{
			args[count++] = arg;
		}
		
		try {
			switch(command)
			{
			case CommandList.TRANSACTION_MAKE:
				Transaction tx = new StatusTransaction();
				if(Linker.getInstance().broadcastingTransactrion(tx)) {
					return CommandList.TRANSACTION_MAKE_SUCESS;
				}else {
					return CommandList.TRANSACTION_MAKE_FAILED;
				}
			case CommandList.WALLET_GENERATOR:
				if(args.length>0) {
					if(KeyManager.getInstance().Create(100)) {
						return CommandList.WALLET_GENERATOR_SUCESS;
					}else {
						return CommandList.WALLET_GENERATOR_FAILED;
					}
				}
				else {
					int c = Integer.parseInt(args[0]);
					if(c > 0 && c <= 100)
					{
						addMessage("[Fairy]: Wallet Generator Load...");
						
						if(KeyManager.getInstance().Create(Integer.parseInt(args[0])))
						{
							return CommandList.WALLET_GENERATOR_SUCESS;
						}
					}
					return CommandList.WALLET_GENERATOR_FAILED;
				}
				default:
					return CommandList.WALLET_GENERATOR_FAILED;
			}
		}catch(Exception e) {
			Debugger.Log(this, e);
			return CommandList.WALLET_GENERATOR_FAILED;
		}
	}
	
	public boolean addMessage(String message)
	{
		try {
			textArea.append(message);
			return true;
		}catch(Exception e) {
			Debugger.Log(this, e);
			return false;
		}
	}
	
	public static CommandLayout getInstance() {
		if(instance == null) {
			instance = new CommandLayout();
		}
		return instance;
	}
}
