package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import Source.Images;
import Source.Size;
import game.GameInfo;

public class GameBoard extends JPanel{
	private Size size;
	private GameInfo game_info;
	private Images images;
	
	public GameBoard(Size _size, GameInfo _game_info, Images _images) {
		size = _size;
		game_info = _game_info;
		images = _images;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw board
		g.drawImage(images.getBoardImage(), 0, 0, this);
		
		// draw icon 
		drawIcon(g); 
	}

	public void drawIcon(Graphics g) {
			for(int y=0;y<size.getSize();y++){
				for(int x=0;x<size.getSize();x++){
					if(game_info.getMap()[y][x]==1)
						g.drawImage(images.getSunImage(), (int)(147+(x*size.getCell())), (int)(69+(y*size.getCell())), this);
					else if(game_info.getMap()[y][x]==2)
						g.drawImage(images.getRainImage(), (int)(147+(x*size.getCell())), (int)(69+(y*size.getCell())), this);		
				}
			}		
	}		
}
