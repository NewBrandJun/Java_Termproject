package gui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import source.Images;

public class ExitPanel extends JPanel{
	// From Wait Frame
	private Images img;
	
	private JLabel l_hint1;
	private JLabel l_hint2;
	private JLabel l_hint3;
	private JLabel l_exit;

	private ImageIcon ic_hint;
    private ImageIcon ic_no_hint;
    private ImageIcon ic_exit;
    
    // True : hint available
    private boolean hint1;
    private boolean hint2;
    private boolean hint3;
    
	public ExitPanel(Images img){
		this.img = img;
		
		// Panel Attribute
		setBounds(660,0,240,60);		
		setLayout(new GridLayout(0,4));
		
		ic_hint = new ImageIcon(img.getHintImage());
		ic_no_hint = new ImageIcon(img.getNoHintImage());
		ic_exit = new ImageIcon(img.getExitImage());
		
		l_hint1 = new JLabel(ic_hint);
		l_hint2 = new JLabel(ic_hint);
		l_hint3 = new JLabel(ic_hint);
		l_exit = new JLabel(ic_exit);
		
		// Add to Panel
		add(l_hint1);
		add(l_hint2);
		add(l_hint3);
		add(l_exit);
			
		hint1 = true;
		hint2 = true;
		hint3 = true;		
	}	
	
	public JLabel getLabelExit() {
		return l_exit;
	}
	
	public JLabel getLabelHint1() {
		return l_hint1;
	}
	
	public JLabel getLabelHint2() {
		return l_hint2;
	}
	
	public JLabel getLabelHint3() {
		return l_hint3;
	}
	
	public boolean getHint1() {
		return hint1;
	}
	
	public void setHint1(boolean h) {
		hint1 = h;
	}
	
	
	public boolean getHint2() {
		return hint2;
	}
	
	public void setHint2(boolean h) {
		hint2 = h;
	}
	
	public boolean getHint3() {
		return hint3;
	}
	
	public void setHint3(boolean h) {
		hint3 = h;
	}
	
	public ImageIcon getHintIcon() {
		return ic_hint;
	}
	public ImageIcon getNoHintIcon() {
		return ic_no_hint;
	}
	
}
