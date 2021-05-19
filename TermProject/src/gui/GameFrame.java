package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Source.Images;
import Source.Size;
import game.GameInfo;

// GAME BOARD & SCORE FRAME
public class GameFrame extends JFrame{
	// BOARD & SCORE에 관한 모든 size를 담은 클래스
	Size size = new Size();
	// 오셀로 게임의 규칙 및 게임 정보를 담은 클래스
	GameInfo game_info = new GameInfo(size);
	// 게임 이미지 클래스
	Images images = new Images(size);
	
	GridBagLayout grid_bag_layout;
	
	public GameFrame(String title) {
		// Frame Background
		setBackground(Color.white);
		// Frame Title
		setTitle(title);
		// (posX, posY, sizeRow, sizeCol)
		setBounds(0, 0, size.getFrameRow(), size.getFrameCol());
		// Frame Exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Frame Menu
		createMenu();

		// Layout 설정
		grid_bag_layout = new GridBagLayout();
		setLayout(grid_bag_layout);
		
		// Game Board Panel
		GameBoard board = new GameBoard(size, game_info, images);
		// add to Frame
		gridBagInsert(board, 0, 0, (float)1.0, (float)1.0);
		
		// Game Sub Panel
		GameSub sub = new GameSub(size, game_info, images);
		// add to Frame
		gridBagInsert(sub, 1, 0, (float)0.2, (float)1.0);
		
		// Mouse
		MouseAction mouse_action = new MouseAction(this, board, size, game_info, sub);
		addMouseListener(mouse_action);
		
		setVisible(true);
		setResizable(false);
		game_info.init();
	}
	
	public void gridBagInsert(Component c, int x, int y, float ww, float wy){
        GridBagConstraints gbc = new GridBagConstraints();
    	gbc.fill= GridBagConstraints.BOTH;	  
    	
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = ww;
        gbc.weighty = wy;
        
        grid_bag_layout.setConstraints(c,gbc);
        this.add(c);
    }
	
	 
	public void createMenu() {
		JMenuBar menu_bar = new JMenuBar();
		MenuActionListener listener = new MenuActionListener();
		
		JMenu options = new JMenu("Options");
		JMenu help = new JMenu("Help");
		
		JMenuItem restart = new JMenuItem("restart");
		JMenuItem exit = new JMenuItem("exit");

		JMenuItem rule = new JMenuItem("rule");
		
		options.add(restart);
		restart.addActionListener(listener);
		options.add(exit);
		exit.addActionListener(listener);

		help.add(rule);
		rule.addActionListener(listener);
		
		menu_bar.add(options);
		menu_bar.add(help);
		
		setJMenuBar(menu_bar);
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			switch (cmd) { 
				case "restart":
					game_info.init();
					break;
				case "rule":
					break;
				case "exit":
					break;
			}
				
		}
	}
}
