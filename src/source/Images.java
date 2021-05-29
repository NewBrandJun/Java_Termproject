package source;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Images {
	private Dimensions dim;
	
	private ImageIcon ic_board;
	private Image i_board;

	private ImageIcon ic_sun;
	private Image i_sun;
 
	private ImageIcon ic_rain;
	private Image i_rain;
	
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
	
    public Images(Dimensions _dim) {
    	dim = _dim;
    	
    	ic_board = new ImageIcon("board.jpg");
    	i_board = ic_board.getImage().getScaledInstance(660, 540, Image.SCALE_DEFAULT);
    	
    	ic_sun = new ImageIcon("sun.png");
    	i_sun = ic_sun.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_rain = new ImageIcon("rain.png");
    	i_rain = ic_rain.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_right_arrow = new ImageIcon("right_arrow.png");
    	i_right_arrow = ic_right_arrow.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_left_arrow = new ImageIcon("left_arrow.png");
    	i_left_arrow = ic_left_arrow.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_hint = new ImageIcon("bulb_light.png");
    	i_hint = ic_hint.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_no_hint = new ImageIcon("bulb_no_light.png");
    	i_no_hint = ic_no_hint.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_ready = new ImageIcon("ready.jpg");
    	i_ready = ic_ready.getImage().getScaledInstance(100, 50,Image.SCALE_DEFAULT);
    	
    	ic_ready_press = new ImageIcon("ready_press.jpg");
    	i_ready_press = ic_ready_press.getImage().getScaledInstance(100, 50,Image.SCALE_DEFAULT);
    	
    	ic_exit = new ImageIcon("exit.png");
    	i_exit = ic_exit.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	ic_star = new ImageIcon("star.png");
    	i_star = ic_star.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    }
    
    public Image getBoardImage() {
    	return i_board;
    }
    
    public Image getSunImage() {
    	return i_sun;
    }
    
    public Image getRainImage() {
    	return i_rain;
    }
    
    public ImageIcon getSunImageIcon() {
    	return ic_sun;
    }
    
    public ImageIcon getRainImageIcon() {
    	return ic_rain;
    }
    
    public Image getRightArrowImage() {
    	return i_right_arrow;
    }
    
    public Image getLeftArrowImage() {
    	return i_left_arrow;
    }
    
    public ImageIcon getRightArrowImageIcon() {
    	return ic_right_arrow;
    }
    
    public ImageIcon getLeftArrowImageIcon() {
    	return ic_left_arrow;
    }
    
    public Image getHintImage() {
    	return i_hint;
    }
    
    public Image getNoHintImage() {
    	return i_no_hint;
    }
    
    public ImageIcon getHintImageIcon() {
    	return ic_hint;
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
