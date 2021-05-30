package source;

import java.awt.Image;
import javax.swing.ImageIcon;

// ImageIcon & Image Class
public class Images {
	// From Main Class
	private Dimensions dim;
	
	private ImageIcon ic_board;
	private Image i_board;

	private ImageIcon ic_white;
	private Image i_white;
 
	private ImageIcon ic_black;
	private Image i_black;
	
	private ImageIcon ic_right_arrow;
	private Image i_right_arrow;
	
	private ImageIcon ic_left_arrow;
	private Image i_left_arrow;
	
	private ImageIcon ic_hint;
	private Image i_hint;
	
	private ImageIcon ic_no_hint;
	private Image i_no_hint;
    
	private ImageIcon ic_exit;
	private Image i_exit;
	
	private ImageIcon ic_ready;
	private Image i_ready;
	
	private ImageIcon ic_ready_press;
	private Image i_ready_press;
	
	private ImageIcon ic_star;
	private Image i_star;
	
	// Constructor
    public Images(Dimensions dim) {
    	this.dim = dim;
    	
    	ic_board = new ImageIcon("board.jpg");
    	i_board = ic_board.getImage().getScaledInstance(dim.getBoardRow(), dim.getBoardCol(), Image.SCALE_DEFAULT);
    	
    	ic_white = new ImageIcon("white.png");
    	i_white = ic_white.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_black = new ImageIcon("black.png");
    	i_black = ic_black.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_right_arrow = new ImageIcon("right_arrow.png");
    	i_right_arrow = ic_right_arrow.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_left_arrow = new ImageIcon("left_arrow.png");
    	i_left_arrow = ic_left_arrow.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_hint = new ImageIcon("hint.png");
    	i_hint = ic_hint.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_no_hint = new ImageIcon("no_hint.png");
    	i_no_hint = ic_no_hint.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_ready = new ImageIcon("ready.jpg");
    	i_ready = ic_ready.getImage().getScaledInstance(dim.getReadyRow(), dim.getReadyCol(),Image.SCALE_DEFAULT);
    	
    	ic_ready_press = new ImageIcon("ready_press.jpg");
    	i_ready_press = ic_ready_press.getImage().getScaledInstance(dim.getReadyRow(), dim.getReadyCol(),Image.SCALE_DEFAULT);
    	
    	ic_exit = new ImageIcon("exit.png");
    	i_exit = ic_exit.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_star = new ImageIcon("star.png");
    	i_star = ic_star.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);    	
    }
    
    // Getter     
    public Image getBoardImage() {
    	return i_board;
    }
    
    public Image getWhiteImage() {
    	return i_white;
    }
    
    public ImageIcon getWhiteImageIcon() {
    	return ic_white;
    }
    
    public Image getBlackImage() {
    	return i_black;
    }
    
    public ImageIcon getBlackImageIcon() {
    	return ic_black;
    }
    
    public Image getRightArrowImage() {
    	return i_right_arrow;
    }
    
    public ImageIcon getRightArrowImageIcon() {
    	return ic_right_arrow;
    }
    
    public Image getLeftArrowImage() {
    	return i_left_arrow;
    }
    
    public ImageIcon getLeftArrowImageIcon() {
    	return ic_left_arrow;
    }
    
    public Image getHintImage() {
    	return i_hint;
    }
    
    public ImageIcon getHintImageIcon() {
    	return ic_hint;
    }
    
    public Image getNoHintImage() {
    	return i_no_hint;
    }
    
    public ImageIcon getNoHintImageIcon() {
    	return ic_no_hint;
    }
    
    public Image getExitImage() {
    	return i_exit;
    }
   
    public ImageIcon getExitImageIcon() {
    	return ic_exit;
    }
    
    public Image getReadyImage() {
    	return i_ready;
    }
   
    public ImageIcon getReadyImageIcon() {
    	return ic_ready;
    }
    
    public Image getReadyPressImage() {
    	return i_ready_press;
    }
   
    public ImageIcon getReadyPressImageIcon() {
    	return ic_ready_press;
    }
    
    public Image getStarImage() {
    	return i_star;
    }
   
    public ImageIcon getStarImageIcon() {
    	return ic_star;
    }
    
}
