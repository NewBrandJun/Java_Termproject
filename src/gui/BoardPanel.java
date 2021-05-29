package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import source.Dimensions;
import source.Images;

public class BoardPanel extends JPanel{
	private Dimensions dim;
	private Rule rule;
	private Images img;
		
    private ImageIcon ic_ready;
    private ImageIcon ic_ready_press;
    
	private JButton btn_ready;
	
	private int ready;
	
	public BoardPanel(Dimensions dim, Rule rule, Images img) {
		this.dim = dim;
		this.rule = rule;
		this.img = img;
		
		setBounds(0, 60, 660,540);
		setLayout(null);
		
		ic_ready = new ImageIcon(img.getReadyImage());
		ic_ready_press = new ImageIcon(img.getReadyPressImage());
		
		btn_ready = new JButton(ic_ready);
		// 1 : ready
		// 2 : unready
		ready = 1;
		add(btn_ready);
		btn_ready.setBounds(280,250,100,50);		
		
	}
	
	public JButton getBtnReady() {
		return btn_ready;
	}
	
	public void setReadyImageIcon() {
		btn_ready.setIcon(ic_ready);
	}

	public void setReadyPressImageIcon() {
		btn_ready.setIcon(ic_ready_press);
	}
	
	public int getReady() {
		return ready;
	}
	
	public void setReady(int num) {
		ready = num;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw board
		g.drawImage(img.getBoardImage(), 0, 0, this);
		
		// Draw icon 
		drawIcon(g); 
	}

	public void drawIcon(Graphics g) {
		for(int y=0;y<dim.getSize();y++){
			for(int x=0;x<dim.getSize();x++){
				if(rule.getMap()[y][x]==1)
					g.drawImage(img.getSunImage(), (int)(155+(x*dim.getCellRow())), (int)(84+(y*dim.getCellCol())), this);
				else if(rule.getMap()[y][x]==2)
					g.drawImage(img.getRainImage(), (int)(155+(x*dim.getCellRow())), (int)(84+(y*dim.getCellCol())), this);		
				else if(rule.getMap()[y][x]==3)
					g.drawImage(img.getStarImage(), (int)(155+(x*dim.getCellRow())), (int)(84+(y*dim.getCellCol())), this);
			}
		}		
	}		
}
