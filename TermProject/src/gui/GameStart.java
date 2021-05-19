package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// game start frame
public class GameStart extends JFrame{
	JButton start;
	JPanel panel;
	public GameStart(String title) {
		setTitle(title);
		setBounds(50, 50, 300,300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel = new JPanel();	
		start = new JButton("START");
		
		// start button click
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameFrame run = new GameFrame(title);
				setVisible(false);
				dispose();
			}
		});
		
		// add start button to panel
		panel.add(start, BorderLayout.CENTER);
		
		// add panel to frame
		add(panel, BorderLayout.CENTER);
		
		setVisible(true);
	}
}
