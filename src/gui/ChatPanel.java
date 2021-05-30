package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPanel extends JPanel{
	private JTextArea ta;
	private JTextField tf;
	private JButton bt_send;
	private JScrollPane sp;
	
	public ChatPanel() {
		this.setBounds(660, 60, 240, 540);
		this.setLayout(null);

		ta = new JTextArea();		
		tf = new JTextField(15);
		bt_send = new JButton("a");
		sp = new JScrollPane(ta);
		
		ta.setEditable(false);
		ta.setLineWrap(true);
						
		this.add(sp);				
		this.add(tf);
		this.add(bt_send);

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
