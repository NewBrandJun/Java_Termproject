package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatFrame extends JFrame{
	JTextArea textArea;
	JTextField tfMsg;
	JButton btnSend;
	
	public ChatFrame() {
		setTitle("Chatting");
		setBounds(850, 0, 350, 450);		
		
		textArea = new JTextArea();		
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane);				

		JPanel msgPanel = new JPanel();
		msgPanel.setLayout(new BorderLayout());
		tfMsg = new JTextField();
		btnSend = new JButton("send");
		msgPanel.add(tfMsg, BorderLayout.CENTER);
		msgPanel.add(btnSend, BorderLayout.EAST);
		add(msgPanel,BorderLayout.SOUTH);
		
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});

		tfMsg.addKeyListener(new KeyAdapter()  {
			@Override
			public void keyPressed(KeyEvent e) {				
				super.keyPressed(e);

				int keyCode = e.getKeyCode();
				
				switch(keyCode) {
				case KeyEvent.VK_ENTER:
					sendMessage();
					break;
				}
			}
		});		
		
		setVisible(true);
		tfMsg.requestFocus();
	}
	
	void sendMessage() {	
		String msg = tfMsg.getText(); 
		
		tfMsg.setText(""); 
		textArea.append("ME : " + msg + "\n");
		textArea.setCaretPosition(textArea.getText().length()); 
	}
}
