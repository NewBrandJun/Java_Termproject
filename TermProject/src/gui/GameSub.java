package gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Source.Images;
import Source.Size;
import game.GameInfo;

public class GameSub extends JPanel {
	private Size size;
	private GameInfo game_info;
	private Images images;
	
	private GridBagLayout grid_bag_layout;
    
	private JLabel turn_label;
	private JLabel my_score_label;
	private JLabel op_score_label;
	private JLabel turn_text;
	private JLabel my_score;
	private JLabel op_score;
    
    private ImageIcon sun_image_icon;
    private ImageIcon rain_image_icon;
    
    private int my_count;
    private int op_count;
    
	public GameSub(Size _size, GameInfo _game_info, Images _images) {
		size = _size;
		game_info = _game_info;
		images = _images;
		
		setBackground(Color.gray);
		
		grid_bag_layout = new GridBagLayout();
		setLayout(grid_bag_layout);

		sun_image_icon = new ImageIcon(images.getSunImage());
		rain_image_icon = new ImageIcon(images.getRainImage());
		
		turn_label = new JLabel(sun_image_icon);
		my_score_label = new JLabel(sun_image_icon);
		op_score_label = new JLabel(rain_image_icon);
		turn_text = new JLabel("TURN", SwingConstants.CENTER);
		my_score = new JLabel(Integer.toString(my_count), SwingConstants.CENTER);
		op_score = new JLabel(Integer.toString(op_count), SwingConstants.CENTER);

        JButton chatting = new JButton("chat");
        JButton exit = new JButton("exit");

        gbInsert(turn_text, 0, 0, 2, 2, 1);
        gbInsert(turn_label, 2, 0, 2, 2, 1);
        gbInsert(my_score_label, 0, 2, 2, 2, 1);
        gbInsert(my_score, 2, 2, 2, 2, 1);
        gbInsert(op_score_label, 0, 4, 2, 2, 1);
        gbInsert(op_score, 2, 4, 2, 2, 1);
        gbInsert(chatting, 0, 6, 1, 1, 0);
        gbInsert(exit, 2, 6, 1, 1, 0);
        
        chatting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ChatFrame chat = new ChatFrame();
			}
		});
        
        exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
        
        my_count = 0;
        op_count = 0;
  	}
	
	public void changeTurn() {
		if(game_info.getCurPlayer() == 1) {
			turn_label.setIcon(sun_image_icon);
		}else {
			turn_label.setIcon(rain_image_icon);
		}
	}
	
	public void changeCount() {
		if(game_info.getCurPlayer() == 1) {
			op_count = 0;
			for (int i = 0; i < size.getSize(); i++) {
				for (int j = 0; j < size.getSize(); j++) {
					if(game_info.getMap()[i][j] == 1) {
						op_count++;
					}
				}
			}
			op_score.setText(Integer.toString(op_count));
		}else {
			my_count = 0;
			for (int i = 0; i < size.getSize(); i++) {
				for (int j = 0; j < size.getSize(); j++) {
					if(game_info.getMap()[i][j] == 1) {
						my_count++;
					}
				}
			}
			my_score.setText(Integer.toString(my_count));
		}
	}
	
	
	 public void gbInsert(Component c, int x, int y, int w, int h, int pack){
	        GridBagConstraints gbc = new GridBagConstraints();
	        
	        if(pack == 1) {
	        	gbc.fill= GridBagConstraints.BOTH;	        	
	        }else {
	        	gbc.fill= GridBagConstraints.NONE;	
	        }
	        
	        gbc.gridx = x;
	        gbc.gridy = y;
	        gbc.gridwidth = w;
	        gbc.gridheight = h;
	        gbc.weightx = 1.0;
	        gbc.weighty = 1.0;
	        
	        grid_bag_layout.setConstraints(c,gbc);
	        this.add(c);
	 }
	
}
