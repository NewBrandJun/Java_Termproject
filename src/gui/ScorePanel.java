package gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import source.Dimensions;
import source.Images;

public class ScorePanel extends JPanel {
	private Dimensions size;
	private Rule game_info;
	private Images images;
	
	private JLabel right_arrow_label;
	private JLabel my_score_label;
	private JLabel my_score;
	private JLabel vs;
	private JLabel op_score;
	private JLabel op_score_label;
	private JLabel left_arrow_label;

	private ImageIcon right_arrow_image_icon;
    private ImageIcon left_arrow_image_icon;
    private ImageIcon sun_image_icon;
    private ImageIcon rain_image_icon;
    
    private int my_count;
    private int op_count;
    
	public ScorePanel(Dimensions _size, Rule _game_info, Images _images) {
		size = _size;
		game_info = _game_info;
		images = _images;
		
		setBackground(Color.gray);
		
		setBounds(0,0,660,60);

		setLayout(new GridLayout(0,7));

		sun_image_icon = new ImageIcon(images.getSunImage());
		rain_image_icon = new ImageIcon(images.getRainImage());
		right_arrow_image_icon = new ImageIcon(images.getRightArrowImage());
		left_arrow_image_icon = new ImageIcon(images.getLeftArrowImage());
		
		right_arrow_label = new JLabel(right_arrow_image_icon);
		my_score_label = new JLabel(sun_image_icon);
		my_score = new JLabel(Integer.toString(my_count), SwingConstants.CENTER);
		vs = new JLabel("VS", SwingConstants.CENTER);
		op_score = new JLabel(Integer.toString(op_count), SwingConstants.CENTER);
		op_score_label = new JLabel(rain_image_icon);
		left_arrow_label = new JLabel(left_arrow_image_icon);
		
		add(right_arrow_label);
        add(my_score_label);
        add(my_score);
        add(vs);
        add(op_score);
        add(op_score_label);
		add(left_arrow_label);

		left_arrow_label.setVisible(false);
        my_count = 0;
        op_count = 0;
  	}
	
	public void changeCount() {
		my_count = 0;
        op_count = 0;
		for (int i = 0; i < size.getSize(); i++) {
			for (int j = 0; j < size.getSize(); j++) {
				if(game_info.getMap()[i][j] == 2) {
					op_count++;
				}else if(game_info.getMap()[i][j] == 1){
					my_count++;
				}
			}
		}
		op_score.setText(Integer.toString(op_count));			
		my_score.setText(Integer.toString(my_count));

	}
	
	public void changeTurn() {
		if(game_info.getTurn() % 2 == 1) {
			right_arrow_label.setVisible(true);
			left_arrow_label.setVisible(false);			
		}else {
			right_arrow_label.setVisible(false);
			left_arrow_label.setVisible(true);	
		}
	}
		
	
}
