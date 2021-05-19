package Source;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Images {
	Size size;
	private ImageIcon board_img_icon;
	private Image board_img;

	private ImageIcon sun_img_icon;
	private Image sun_img;
 
	private ImageIcon rain_img_icon;
	private Image rain_img;
    
    public Images(Size _size) {
    	size = _size;
    	
    	board_img_icon = new ImageIcon("board.jpg");
    	board_img = board_img_icon.getImage();
    	
    	sun_img_icon = new ImageIcon("sun.png");
    	sun_img = sun_img_icon.getImage().getScaledInstance(size.getIcon(), size.getIcon(),Image.SCALE_DEFAULT);
    	
    	rain_img_icon = new ImageIcon("rain.png");
    	rain_img = rain_img_icon.getImage().getScaledInstance(size.getIcon(), size.getIcon(),Image.SCALE_DEFAULT);
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
    
}
