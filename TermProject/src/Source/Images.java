package source;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Images {
	private Dimensions dim;
	
	private ImageIcon board_img_icon;
	private Image board_img;

	private ImageIcon sun_img_icon;
	private Image sun_img;
 
	private ImageIcon rain_img_icon;
	private Image rain_img;
	
	private ImageIcon right_arrow_img_icon;
	private Image right_arrow_img;
	
	private ImageIcon left_arrow_img_icon;
	private Image left_arrow_img;
	
	private ImageIcon bulb_light_img_icon;
	private Image bulb_light_img;
	
	private ImageIcon bulb_no_light_img_icon;
	private Image bulb_no_light_img;
    
	private ImageIcon exit_img_icon;
	private Image exit_img;
	
    public Images(Dimensions _dim) {
    	dim = _dim;
    	
    	board_img_icon = new ImageIcon("board.jpg");
    	board_img = board_img_icon.getImage().getScaledInstance(660, 540, Image.SCALE_DEFAULT);
    	
    	sun_img_icon = new ImageIcon("sun.png");
    	sun_img = sun_img_icon.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	rain_img_icon = new ImageIcon("rain.png");
    	rain_img = rain_img_icon.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	right_arrow_img_icon = new ImageIcon("right_arrow.png");
    	right_arrow_img = right_arrow_img_icon.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	left_arrow_img_icon = new ImageIcon("left_arrow.png");
    	left_arrow_img = left_arrow_img_icon.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	bulb_light_img_icon = new ImageIcon("bulb_light.png");
    	bulb_light_img = bulb_light_img_icon.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	bulb_no_light_img_icon = new ImageIcon("bulb_no_light.png");
    	bulb_no_light_img = bulb_no_light_img_icon.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    	
    	exit_img_icon = new ImageIcon("exit.png");
    	exit_img = exit_img_icon.getImage().getScaledInstance(dim.getIcon(), dim.getIcon(),Image.SCALE_DEFAULT);
    }
    
    public Image getBoardImage() {
    	return board_img;
    }
    
    public Image getSunImage() {
    	return sun_img;
    }
    
    public Image getRainImage() {
    	return rain_img;
    }
    
    public ImageIcon getSunImageIcon() {
    	return sun_img_icon;
    }
    
    public ImageIcon getRainImageIcon() {
    	return rain_img_icon;
    }
    
    public Image getRightArrowImage() {
    	return right_arrow_img;
    }
    
    public Image getLeftArrowImage() {
    	return left_arrow_img;
    }
    
    public ImageIcon getRightArrowImageIcon() {
    	return right_arrow_img_icon;
    }
    
    public ImageIcon getLeftArrowImageIcon() {
    	return left_arrow_img_icon;
    }
    
    public Image getBulbLightImage() {
    	return bulb_light_img;
    }
    
    public Image getBulbNoLightImage() {
    	return bulb_no_light_img;
    }
    
    public ImageIcon getBulbLightImageIcon() {
    	return bulb_light_img_icon;
    }
    
    public ImageIcon getBulbNoLightImageIcon() {
    	return bulb_no_light_img_icon;
    }
    
    public Image getExitImage() {
    	return exit_img;
    }
   
    public ImageIcon getExitImageIcon() {
    	return exit_img_icon;
    }
    
    
}
