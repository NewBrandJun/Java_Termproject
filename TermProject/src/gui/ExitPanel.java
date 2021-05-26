package gui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import source.Images;

public class ExitPanel extends JPanel{
	private Images img;
	
	private JLabel l_hint1;
	private JLabel l_hint2;
	private JLabel l_hint3;
	private JLabel l_exit;


	private ImageIcon ic_hint;
    private ImageIcon ic_no_hint;
    private ImageIcon ic_exit;
    
	public ExitPanel(Images img){
		this.img = img;
		
		setBounds(660,0,240,60);		
		setLayout(new GridLayout(0,4));
		
		ic_hint = new ImageIcon(img.getBulbLightImage());
		ic_no_hint = new ImageIcon(img.getBulbNoLightImage());
		ic_exit = new ImageIcon(img.getExitImage());
		
		l_hint1 = new JLabel(ic_hint);
		l_hint2 = new JLabel(ic_hint);
		l_hint3 = new JLabel(ic_hint);
		l_exit = new JLabel(ic_exit);
		
		add(l_hint1);
		add(l_hint2);
		add(l_hint3);
		add(l_exit);
	
		
		l_hint1.addMouseListener(new MouseAdapter() {		 
	        public void mouseClicked(MouseEvent e)   
	        {   
	              l_hint1.setIcon(ic_no_hint);
	        }   
		});
	}	
	
	public JLabel getLabelExit() {
		return l_exit;
	}
}
