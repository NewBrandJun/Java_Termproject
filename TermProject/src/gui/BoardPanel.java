package gui;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import source.Dimensions;
import source.Images;

public class BoardPanel extends JPanel{
	private Dimensions dim;
	private Rule rule;
	private Images img;
		
	public BoardPanel(Dimensions dim, Rule rule, Images img) {
		this.dim = dim;
		this.rule = rule;
		this.img = img;
		
		setBounds(0, 60, 660,540);
		setLayout(null);
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
				}
			}		
	}		
}
