package fairy.core.command;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import fairy.core.managers.key.KeyManager;
import fairy.core.utils.Debugger;

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
				textField.setEnabled(false);
				String[] commandWithArgs = textField.getText().split(" ");
				
				switch(ExcuteCommand(commandWithArgs))
				{
				case CommandList.WALLET_GENERATOR_SUCCESS:
					addMessage("[Fairy]: ���� ������ �����Ͽ����ϴ�. assets\\wallet\\ ��θ� Ȯ�����ּ���.");
					textField.setBackground(new Color(159, 244, 180));
					textField.setText("");
					break;
					
				case CommandList.WALLET_GENERATOR_FAILED:
					addMessage("[Fairy]: ���� ������ �����Ͽ����ϴ�. ��ɾ� Ȯ���� �ٽ� �õ����ֽñ�ٶ��ϴ�.");
					textField.setBackground(new Color(255, 149, 149));
					textField.setText("");
					break;
					
				case CommandList.WALLET_GENERATOR_FAILED_OVERFLOW:
					addMessage("[Fairy]: ������ 0~100�� ������ Ű�� ���� �����մϴ�.");
					textField.setBackground(new Color(255, 149, 149));
					textField.setText("");
					break;
					
				case CommandList.WALLET_LIST_SUCCESS:
					textField.setBackground(new Color(159, 244, 180));
					textField.setText("");
					break;
					
				case CommandList.WALLET_LIST_FAILED:
					addMessage("[Fairy]: ������ �ҷ����� ���߽��ϴ�. ���� ��θ� �ٽ��ѹ� Ȯ�����ּ���.");
					textField.setBackground(new Color(255, 149, 149));
					textField.setText("");
					break;
					
				case CommandList.COMMAND_EXCEPTION:
					addMessage("[Fairy]: �߸��� Ŀ�ǵ带 �Է��ϰų� Ŀ�ǵ��� ������ �߻��Ͽ����ϴ�.");
					textField.setBackground(new Color(255, 149, 149));
					textField.setText("");
					break;
				}
				
				textArea.setCaretPosition(textArea.getDocument().getLength());
				textField.setEnabled(true);
			}
		});
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		textField.setBorder(BorderFactory.createCompoundBorder(border, 
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		JLabel lblCommand = new JLabel("Command");
		lblCommand.setFont(new Font("����", Font.PLAIN, 12));
		lblCommand.setBounds(12, 611, 73, 17);
		getContentPane().add(lblCommand);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 907, 586);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	
		setVisible(true);
	}
	
	private int ExcuteCommand(String[] commandWithArgs)
	{
		String command = commandWithArgs[0];
		String[] args = new String[commandWithArgs.length-1];
		
		if(commandWithArgs.length >=2)
		{
			for(int i=1;i<commandWithArgs.length;i++)
			{
				args[i-1] = commandWithArgs[i];
			}
		}else args = null;
		
		try {
			switch(command)
			{
			case CommandList.TRANSACTION_MAKE:
				return CommandList.TRANSACTION_MAKE_SUCCESS;
				/*Transaction tx = new StatusTransaction();
				if(Linker.getInstance().broadcastingTransactrion(tx)) {
					return CommandList.TRANSACTION_MAKE_SUCCESS;
				}else {
					return CommandList.TRANSACTION_MAKE_FAILED;
				}*/
			case CommandList.WALLET_GENERATOR:
				addMessage("[Fairy]: Wallet Generator Load...");
				if(args == null) {
					addMessage("[Fairy]: Wallet Generating 100 private keys");
					if(KeyManager.getInstance().Create(100)) {
						return CommandList.WALLET_GENERATOR_SUCCESS;
					}else {
						return CommandList.WALLET_GENERATOR_FAILED;
					}
				}
				else {
					int c = Integer.parseInt(args[0]);
					if(c > 0 && c <= 100)
					{
						addMessage("[Fairy]: Wallet Generating " + c + " private keys");
						if(KeyManager.getInstance().Create(Integer.parseInt(args[0])))
						{
							return CommandList.WALLET_GENERATOR_SUCCESS;
						}
					}
					return CommandList.WALLET_GENERATOR_FAILED_OVERFLOW;
				}
			case CommandList.WALLET_LIST:
				addMessage(KeyManager.getInstance().toString());
				return CommandList.WALLET_LIST_SUCCESS;
				default:
					return CommandList.WALLET_GENERATOR_FAILED;
			}
		}catch(Exception e) {
			Debugger.Log(this, e);
			return CommandList.COMMAND_EXCEPTION;
		}
	}
	
	public boolean addMessage(String message)
	{
		try {
			textArea.append(message + "\r\n");
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
