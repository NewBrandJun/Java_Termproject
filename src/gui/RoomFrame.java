package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import source.Dimensions;
import source.Images;

// ∞‘¿”πÊ Frame
public class RoomFrame extends JFrame{
	// From Wait Frame
	private Dimensions dim;
	private Images img;	
	private Rule rule;
	
	public RoomFrame(Dimensions dim, Images img, Rule rule) 
	{
		this.dim = dim;		
		this.img = img;
		this.rule = rule;
				
		// Frame Setting
		setBackground(Color.gray);
		setBounds(0, 0, dim.getFrameRow(), dim.getFrameCol());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		setVisible(false);
		setResizable(false);
		
		// Board map init
		rule.init();	
	}
	 
}
