package gui;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import source.Images;

public class ChatPanel extends JPanel{
	// From Wait Frame
	private Images img;
	
	private JTextArea ta;
	private JTextField tf;
	private JButton bt_send;
	private JScrollPane sp;
	
    private ImageIcon ic_send;

	public ChatPanel(Images img) {
		this.img = img;		
		this.setBounds(660, 60, 240, 540);
		this.setLayout(null);

		ic_send = new ImageIcon(img.getSendImage());

		ta = new JTextArea();		
		tf = new JTextField(15);
		bt_send = new JButton(ic_send);
		sp = new JScrollPane(ta);
		
		Font font = new Font("SansSerif", Font.PLAIN, 16);
		ta.setFont(font);
		tf.setFont(font);
		
		ta.setEditable(false);
		ta.setLineWrap(true);
						

		this.add(sp);				
		this.add(tf);
		this.add(bt_send);

		sp.setBounds(0,0, 240, 490);
		tf.setBounds(0, 490, 200, 50);
		bt_send.setBounds(200, 490, 40, 50);
		
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
