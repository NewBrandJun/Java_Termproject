package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import source.Dimensions;
import source.Images;

// ∞‘¿”πÊ Frame
public class RoomFrame extends JFrame{
	private Dimensions dim;
	private Images img;
	
	private Rule rule;
	private ScorePanel sp;
	private BoardPanel bp;
	private ChatPanel cp;
	private ExitPanel ep;
	
	public RoomFrame(Dimensions dim, Images img, Rule rule) 
	{
		this.dim = dim;		
		this.img = img;
		this.rule = rule;
				
		setBackground(Color.gray);
		setBounds(0, 0, dim.getFrameRow(), dim.getFrameCol());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		createMenu();			
		
		setVisible(false);
		setResizable(false);
		rule.init();	
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
					rule.init();
					break;
				case "rule":
					break;
				case "exit":
					break;
			}
				
		}
	}
	
	public Rule getRule() {
		return rule;
	}
	
	public ScorePanel getScorePanel() {
		return sp;
	}

	public BoardPanel getBoardPanel() {
		return bp;
	}

	public ChatPanel getChatPanel() {
		return cp;
	}

	public ExitPanel getExitPanel() {
		return ep;
	}

}
