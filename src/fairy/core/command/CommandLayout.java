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
					addMessage("[Fairy]: 지갑 생성에 성공하였습니다. assets\\wallet\\ 경로를 확인해주세요.");
					textField.setBackground(new Color(159, 244, 180));
					textField.setText("");
					break;
					
				case CommandList.WALLET_GENERATOR_FAILED:
					addMessage("[Fairy]: 지갑 생성에 실패하였습니다. 명령어 확인후 다시 시도해주시길바랍니다.");
					textField.setBackground(new Color(255, 149, 149));
					textField.setText("");
					break;
					
				case CommandList.WALLET_GENERATOR_FAILED_OVERFLOW:
					addMessage("[Fairy]: 지갑은 0~100개 사이의 키만 생성 가능합니다.");
					textField.setBackground(new Color(255, 149, 149));
					textField.setText("");
					break;
					
				case CommandList.WALLET_LIST_SUCCESS:
					textField.setBackground(new Color(159, 244, 180));
					textField.setText("");
					break;
					
				case CommandList.WALLET_LIST_FAILED:
					addMessage("[Fairy]: 지갑을 불러오지 못했습니다. 지갑 경로를 다시한번 확인해주세요.");
					textField.setBackground(new Color(255, 149, 149));
					textField.setText("");
					break;
					
				case CommandList.COMMAND_EXCEPTION:
					addMessage("[Fairy]: 잘못된 커맨드를 입력하거나 커맨드의 문제가 발생하였습니다.");
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
		lblCommand.setFont(new Font("굴림", Font.PLAIN, 12));
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
