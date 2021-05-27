package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import source.Dimensions;
import source.Images;

// Client Side (´ë±â¹æ)
public class WaitFrame extends JFrame implements ActionListener, Runnable{
	// From Main
	private Dimensions dim;
	private Images img;
	
	// Game Room Frame
	private RoomFrame rf;
	
	// Game Rule & Information
	private Rule rule;
	// Game Score & Turn Panel
	private ScorePanel sp;
	// Game Board Panel
	private BoardPanel bp;
	// Game Chat Panel
	private ChatPanel cp;
	// Game Exit & Hint Panel
	private ExitPanel ep;
	
	// From Board Panel
	private JButton btn_ready;
	
	// From ChatPanel
	private JTextArea ta;
	private JTextField tf;
	private JButton btn_send;
	private JLabel l_exit;
		
	// Room & Wait Players List
	private JList<String> list_room, list_wait;
	// List Room & Players Scroll Pane
	private JScrollPane sp_list_room, sp_list_wait;
	// Buttons
	private JButton bt_create, bt_enter, bt_exit;
	// A Panel
	private JPanel p;
	
	// Data Streams
	private BufferedReader from_server = null;
	private OutputStream to_server = null;

	// Room Title
	private String r_title;
	
	// »ý¼ºÀÚ
	public WaitFrame(Dimensions dim, Images img) {
		this.dim = dim;
		this.img = img;
		
		this.setTitle("Othello");
		this.setBounds(300,200, 500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	    waitFrameGui();	    
		roomFrameGui();
	    getPanelItems();	    
	    
	    addMouseClickListener();
	    addClickListener();
	    	    	
	    connectServer();	    
	    
	    // Message Thread
	    new Thread(this).start();
	    
	    getPlayerName();	    
	    	    
	    this.setVisible(true);
	}
	
	private void waitFrameGui() {
		// Room List
		list_room = new JList<String>();
		list_room.setBorder(new TitledBorder("Game Room List"));
		
		// Wait Players List
		list_wait = new JList<String>();
		list_wait.setBorder(new TitledBorder("Wait Clients"));
		
		// Attach Lists to ScrollPane
		sp_list_room = new JScrollPane(list_room);
		sp_list_wait = new JScrollPane(list_wait);
		
		// Make Buttons
		bt_create = new JButton("Create");
		bt_enter = new JButton("Enter");
		bt_exit = new JButton("Exit"); 
		
		// Build Panel
		p = new JPanel();
		
		// Set Positions
		sp_list_room.setBounds(10, 10, 300, 300);
		sp_list_wait.setBounds(10, 320, 300, 130);
		bt_create.setBounds(320,330,150,30);
		bt_enter.setBounds(320,370,150,30);
		bt_exit.setBounds(320,410,150,30);		
		
		p.setLayout(null);
		p.setBackground(Color.gray);
		p.add(sp_list_room);
		p.add(sp_list_wait);
		p.add(bt_create);
		p.add(bt_enter);
		p.add(bt_exit);
		
		this.add(p);
	}
	
	private void roomFrameGui() {
		rule = new Rule(dim);
	    sp = new ScorePanel(dim, rule, img);
		bp = new BoardPanel(dim, rule, img);
		cp = new ChatPanel();
		ep = new ExitPanel(img);
		
		// Game Frame
		rf = new RoomFrame(dim, img, rule);
		
		rf.add(sp);
		rf.add(bp);
		rf.add(cp);
		rf.add(ep);
	}		
	
	private void getPanelItems() {
		// From ChatPanel
	    ta = cp.getTextArea();
	    tf = cp.getTextField();
	    btn_send = cp.getBtnSend();
	    l_exit = ep.getLabelExit();
	    
	    // From BoardPanel	
	    btn_ready = bp.getBtnReady();
	}
	
	private void addMouseClickListener() {
		// List Room Click Listener
		list_room.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String temp = list_room.getSelectedValue();
				if(temp == null)
					return;
				System.out.println("Select Room Title = " + temp);
				r_title = temp.substring(0, temp.indexOf("-"));
			}	 
		});
				
		// Game Frame Mouse Click Listener (ÁÂÇ¥)
		rf.addMouseListener(new MouseAdapter() {			
			@Override
			public void mousePressed(MouseEvent e) {
				int x = (int)e.getX();
				int y = (int)e.getY();
				if(rule.getStartFlag()) {
					// Send Position
					sendMessage("Position|" + Integer.toString(x) + "," + Integer.toString(y));							
					
				}
			}	 
		});
	
		
		l_exit.addMouseListener(new MouseAdapter() {			
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		// Send Exit Signal
				sendMessage("Exit|");
				
				// Go back to Wait
				rf.setVisible(false);
				setVisible(true); 	
	    	}	 
	    });
	}
	
	private void addClickListener() {
		bt_create.addActionListener(this);
		bt_enter.addActionListener(this);
		bt_exit.addActionListener(this);
		
	    btn_send.addActionListener(this);
		btn_ready.addActionListener(this);

	}
	
	private void connectServer() {
		// Connect to Server
	    try {
			Socket socket = new Socket("localhost", 8000);	  

			from_server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			to_server = socket.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	private void getPlayerName() {
		// Get Player Name
	    String player_name = JOptionPane.showInputDialog(this,"Player name:");
	    
	    // Send connect signal
	    sendMessage("Connect|");		    
	    // Send Player Name
	    sendMessage("PlayerName|" + player_name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
				
		if(object == bt_create){
			// Room Title
			String title_new_room = JOptionPane.showInputDialog(this,"Room Title:");
			
			// Send Room Title
			sendMessage("RoomTitle|" + title_new_room);		
			// Set room frame title
			rf.setTitle("[" + title_new_room + "]");

			// Enter Room Frame
			setVisible(false);
			rf.setVisible(true); 
			
		}else if(object == bt_enter){			
			if(r_title == null){
				JOptionPane.showMessageDialog(this, "Please Select Room...");
				return;
			}
		
			// Enter Room
			sendMessage("EnterRoom|"+ r_title);

			// Enter Room Frame
			setVisible(false);
			rf.setVisible(true);
			
		}else if(object == bt_exit){
			// Exit
			System.exit(0);
		}else if(object == btn_send){		
			String message = tf.getText();
			
			if(message.length() > 0){				
				sendMessage("Message|" + message); 				
				tf.setText("");				
			}			
		}else if(object == btn_ready) {
			if(bp.getReady() == 1) {
				bp.setReadyPressImageIcon();
				bp.setReady(2);
				sendMessage("Ready|");				
			}else {
				bp.setReadyImageIcon();
				bp.setReady(1);
				sendMessage("UnReady|");	
			}
			
		}	
	}	

	public void run(){
		try {
			while(true){
				// Read Message
				String message = from_server.readLine();
				String messages[] = message.split("\\|");
			
				String cmd = messages[0];

				switch(cmd){				
				case "Rooms":
					// Receive Rooms
					if(messages.length > 1){						
						String[] rooms = messages[1].split(",");
						list_room.setListData(rooms);
					}
					break;
				case "WaitPlayers":
					// Receive Wait Players
					list_wait.setListData(messages[1].split(","));
					break;
				case "RoomTitle":
					// Receive Room Title
					rf.setTitle("[" + messages[1] + "]");					
					break; 
				case "EnterRoom":	
					ta.append("<[" + messages[1] + "]´Ô ÀÔÀå>\n");					
					ta.setCaretPosition(ta.getText().length());					
					break;										
				case "ExitRoom"://´ëÈ­¹æ ÅðÀå				
					ta.append("<[" + messages[1] + "]´Ô ÅðÀå>\n");					
					ta.setCaretPosition(ta.getText().length());					
					break;								
				case "Message": 
					// Receive Message				
					ta.append(messages[1]+"\n");
					ta.setCaretPosition(ta.getText().length());
					break;
				case "Start":
					// Game Start					
					btn_ready.setVisible(false);
					rule.setStartFlag(true);
					break;
				case "Position":
					// Receive Position
					String pos[] = messages[1].split(",");
					int x = Integer.parseInt(pos[0]);
					int y = Integer.parseInt(pos[1]);
							
					if(rule.getMap()[y][x] == 0) {
						// Update Piece on the Board
						Piece piece = new Piece(y, x, rule.getCurPlayer());
						rule.inputWord(piece);
						rule.nextPlayer(rule.getCurPlayer());
						
						// Arrow 
						sp.changeTurn();
						// Score
						sp.changeCount();
						
						// Redraw Board
						bp.repaint();
						
						// End Condition
						if(rule.endGame(piece) == true) {
							String ms;
							if(piece.getColor()==1) {
								ms="°Ëµ¹½Â¸®!";
							}
							else if(piece.getColor()==2) {
								ms="¹éµ¹½Â¸®!";
							}
//							showWin(ms);
							rule.init();
						}
					}
					break;
				}			
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message){
		try {
			to_server.write((message + "\n").getBytes());
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
