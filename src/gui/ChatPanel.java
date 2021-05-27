package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ChatPanel extends JPanel{
	private JTextArea ta;
	private JTextField tf;
	private JButton bt_send;
	private JScrollPane sp;
	
	public ChatPanel() {
		setBounds(660, 60, 240, 540);
		setLayout(null);

		ta = new JTextArea();		
		tf = new JTextField(15);
		bt_send = new JButton("a");
		sp = new JScrollPane(ta);
		
		ta.setEditable(false);
		ta.setLineWrap(true);
						
		add(sp);				
		add(tf);
		add(bt_send);

		sp.setBounds(0,0, 240, 480);
		tf.setBounds(0, 480, 200, 60);
		bt_send.setBounds(200, 480, 40, 60);
		
		tf.requestFocus();
	}
	
	public JTextArea getTextArea() {
		return ta;
	}

	public JTextField getTextField() {
		return tf;
	}

	public JButton getBtnSend() {
		return bt_send;
	}

	public JScrollPane getScrollPane() {
		return sp;
	}

}
