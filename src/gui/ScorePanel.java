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
	// From Wait Frame
	private Dimensions dim;
	private Rule rule;
	private Images img;
	
	private JLabel l_right_arrow;
	private JLabel l_black_score;
	private JLabel black_score;
	private JLabel vs;
	private JLabel white_score;
	private JLabel l_white_score;
	private JLabel l_left_arrow;

	private ImageIcon ic_right_arrow;
    private ImageIcon ic_left_arrow;
    private ImageIcon ic_white;
    private ImageIcon ic_black;
    
    private int black_count;
    private int white_count;
    
	public ScorePanel(Dimensions dim, Rule rule, Images img) {
		this.dim = dim;
		this.rule = rule;
		this.img = img;
		
		setBackground(Color.gray);
		
		setBounds(0,0,660,60);

		setLayout(new GridLayout(0,7));

		ic_white = new ImageIcon(img.getWhiteImage());
		ic_black = new ImageIcon(img.getBlackImage());
		ic_right_arrow = new ImageIcon(img.getRightArrowImage());
		ic_left_arrow = new ImageIcon(img.getLeftArrowImage());
		
		l_right_arrow = new JLabel(ic_right_arrow);
		l_black_score = new JLabel(ic_white);
		black_score = new JLabel(Integer.toString(black_count), SwingConstants.CENTER);
		vs = new JLabel("VS", SwingConstants.CENTER);
		white_score = new JLabel(Integer.toString(white_count), SwingConstants.CENTER);
		l_white_score = new JLabel(ic_black);
		l_left_arrow = new JLabel(ic_left_arrow);
		
		add(l_right_arrow);
        add(l_black_score);
        add(black_score);
        add(vs);
        add(white_score);
        add(l_white_score);
		add(l_left_arrow);

		l_left_arrow.setVisible(false);
        black_count = 0;
        white_count = 0;
  	}
	
	public void changeCount() {
		black_count = 0;
        white_count = 0;
		for (int i = 0; i < dim.getSize(); i++) {
			for (int j = 0; j < dim.getSize(); j++) {
				if(rule.getMap()[i][j] == 2) {
					white_count++;
				}else if(rule.getMap()[i][j] == 1){
					black_count++;
				}
			}
		}
		white_score.setText(Integer.toString(white_count));			
		black_score.setText(Integer.toString(black_count));

	}
	
	public void changeTurn() {
		if(rule.getTurn() % 2 == 1) {
			l_right_arrow.setVisible(true);
			l_left_arrow.setVisible(false);			
		}else {
			l_right_arrow.setVisible(false);
			l_left_arrow.setVisible(true);	
		}
	}
		
	
}
