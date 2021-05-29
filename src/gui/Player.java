package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import source.Dimensions;

public class Player extends Thread{
	private int id;
	
	// From Server
	Vector<Player> wait_players;
	Vector<Room> rooms;
	
	// New Room
	private Room room;
	private char color;

	// Data Streams
	private BufferedReader from_client = null;
	private OutputStream to_client = null;	
	
	private Socket socket;
	
	// Player Name
	private String player_name;

	private Dimensions dim;
	// Ŭ�� ��� ����
	private float[] rangeX_below;
	private float[] rangeX_upper;
	private float[] rangeY_below;
	private float[] rangeY_upper;
	
	public Player(Socket s, Vector<Player> wait_players, Vector<Room> rooms) { 
		id = 1;
		
		this.wait_players = wait_players;
		this.rooms = rooms;
		dim = new Dimensions();
		
		this.socket = s;
		   
		try {		
			from_client = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			to_client = socket.getOutputStream();
			
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// x�� click������ range
		rangeX_below = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeX_below[i] = 163+(i*dim.getCellRow());
		}
		rangeX_upper = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeX_upper[i] = 198+(i*dim.getCellRow());
		}
		
		// y�� click������ range
		rangeY_below = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeY_below[i] = 194+(i*dim.getCellCol());
		}
		rangeY_upper = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeY_upper[i] = 233+(i*dim.getCellCol());
		}						
	}

	
	
	@Override
	public void run() {
		try {
			while(true){
				// Get Message    
				String message = from_client.readLine();
	
				if(message == null) return; 
				
				if(message.trim().length() > 0){  
					System.out.println("from Client: "+ message +":"+ socket.getInetAddress().getHostAddress());
					String messages[]=message.split("\\|");
					String cmd = messages[0].trim(); 
					
					switch(cmd){
					case "Connect": 
						// Receive Connect Signal
						synchronized (wait_players) {
							wait_players.add(this);							
						}
											
						break;
					
					case "PlayerName":
						// Receive Player Name
						player_name = messages[1]; 
						
						// Send Rooms
						sendMessageWait("Rooms|"+ getRooms());
						// Send Wait Players
						sendMessageWait("WaitPlayers|"+ getWaitPlayers());
						System.out.println("Wait Players : "+wait_players);
						System.out.println("Rooms : "+rooms);
						break;
						
					case "RoomTitle": 
						// Receive Room Title
						room = new Room(messages[1]);
						
						// room unique id
						room.id = id++;
						synchronized (rooms) {
							rooms.add(room);							
						}			
						synchronized (wait_players) {
							wait_players.remove(this);
						}						
						room.players.add(this);
						
						
						// Send Enter Signal
						sendMessageRoom("EnterRoom|" + player_name);
						// Send Rooms
						sendMessageWait("Rooms|"+ getRooms());
						// Send Wait Players
						sendMessageWait("WaitPlayers|"+ getWaitPlayers());
					
						System.out.println("Wait Players : "+wait_players);
						System.out.println("Rooms : "+rooms);
						sendMessageRoom("Board|" + room.board.print() + "," + Integer.toString(room.board.turn));//room�� board����
						break;

					case "EnterRoom": 
						// Enter Room
						synchronized (rooms) {
							for(int i=0; i<rooms.size(); i++){
								Room r = rooms.get(i);
								if(r.title.equals(messages[1])) {
									if (r.players.size()>=2) {
										//Send Error
										sendMessage("Error_room_full|");
										// Send Rooms
										sendMessageWait("Rooms|"+ getRooms());
										// Send Wait Players
										sendMessageWait("WaitPlayers|"+ getWaitPlayers());
									}
									else {
										room = r;
										synchronized (wait_players) {	
											wait_players.remove(this);
										}								
										room.players.add(this);
										// Send Enter Message to Room Players
										sendMessageRoom("EnterRoom|" + player_name);								
										// Send Room Title
										sendMessage("RoomTitle|"+ room.title);
										// Send Rooms
										sendMessageWait("Rooms|"+ getRooms());
										// Send Wait Players
										sendMessageWait("WaitPlayers|"+ getWaitPlayers());	
									}							
								}
							}													
						}		
						System.out.println("Wait Players : "+wait_players);
						System.out.println("Rooms : "+rooms);
						sendMessageRoom("Board|" + room.board.print() + "," + Integer.toString(room.board.turn));//room�� board����
						break;
						
					case "Exit": 
						// Receive Exit Signal							
						// Send Exit Message to Room Players
						sendMessageRoom("ExitRoom|" + player_name);
						
						// �� Player���� board �ʱ�ȭ
						room.board.init();
						room.board.turn = 0;
						room.ready_count = 0;						
						sendMessageRoom("Board|" + room.board.print() + "," + Integer.toString(room.board.turn));//room�� board����
						
						// ������ player�� chatting area, ready button �ʱ�ȭ						
						sendMessage("RoomFrameInit|");
																							
							
						room.players.remove(this);	
						//synchronized (wait_players) {
							wait_players.add(this);
							sendMessageWait("WaitPlayers|"+ getWaitPlayers());	
						//}					
						if(room.players.size()==0) {
							synchronized (rooms) {
								rooms.remove(room);
								room =null;
							}							
						}else {
							// �濡 �ִ� �ٸ� player���Դ� �����ٴ� �޽��� ���� �� ready button �ʱ�ȭ
							sendMessageRoom("ExitPlayer|");	
						}
						
						// Send Rooms
						sendMessageWait("Rooms|"+ getRooms());
						// Send Wait Players
						sendMessageWait("WaitPlayers|"+ getWaitPlayers());
						System.out.println("Wait Players : "+wait_players);
						System.out.println("Rooms : "+rooms);
						break;
						
					case "Message":						
						// Send Message to Room Players
						sendMessageRoom("Message|["+player_name +"]�� "+messages[1]);
						break;
					case "Ready":						
						room.ready_count++;
						
						if(room.ready_count == 2) {
							// Game Start
							sendMessageRoom("Start|");
							room.players.get(0).color = 'b';
							room.players.get(1).color = 'w';
							sendMessageRoom("Board|" + room.board.print() + "," + Integer.toString(room.board.turn));//room�� board����
						}
						break;
					case "UnReady":						
						room.ready_count--;						
						break;
					case "Hint":
						if((color == 'b' && room.board.turn%2 == 0) ||
								(color == 'w' && room.board.turn%2 == 1) ) {
							sendMessage("Board|" + room.board.print_hint(color) + "," + Integer.toString(room.board.turn));					
							sendMessage("NoHint|" + messages[1]);
						}
						break;
					case "Position":
						// Piece�� �� �� �ִ��� �������� ���� Flag
						Boolean possible = true;
						
						// Receive Position
						String pos[] = messages[1].split(",");
						int x = Integer.parseInt(pos[0]);
						int y = Integer.parseInt(pos[1]);
						
						
						
						// Out of bounds
						if(x < 163 || y < 194 || x > 510 || y > 574) {
							break;
						}
						
						// x range check
						for(int i =0; i < 8;i++) {
							if(x >= rangeX_below[i]) {
								if(x <= rangeX_upper[i]) {
									// correct range
									x = i;
									break;
								}
							}else {
								// incorrect range
								possible = false;
							}
						}		
						
						// y range check
						for(int i =0; i < 8;i++) {
							if(y >= rangeY_below[i]) {
								if(y <= rangeY_upper[i]) {
									// correct range
									y = i;
									break;
								}
							}else {
								// incorrect range
								possible = false;
							}
						}
						
						if(possible) {
							System.out.println("position: " + y +","+ x +"\n");
							room.board.print_2();
							// Send Position to Room Players
							if(room.board.notfinish()) {//game not end
								if(color =='b'){//p1(��)�ΰ��
				        			if (room.board.turn%2 == 0) {//¦����?
				                		if (room.board.check('b')) {//pass check
				                			if(!room.board.select(y, x, 'b')) {//����  �� �ִ��� Ȯ��
				                				room.board.reverse(y, x, 'b'); //������
				                				room.board.count();
				                				room.board.turn++;
				                				sendMessageRoom("Board|" + room.board.print() + "," + Integer.toString(room.board.turn));//room�� board����
				                				if(room.board.end()) {
				                					sendMessageRoom("Endgame|" + room.board.result());
				                				}
				                 				
				                			}
				                			else{//���� �� ����
				                				sendMessage("Error_location|" );//location error
				                			}            			
				                		}
				                		else {//pass
				                			room.board.turn++;
				                			sendMessageRoom("Board|" + room.board.print()+ "," + Integer.toString(room.board.turn));//room�� board����
				                		}
				        			}       			
				        			else {//¦������ �ƴ�
				        				sendMessage("Error_turn|" );//turn error
				        			}           		
				                }
								
								else if(color =='w'){//p2(��)�ΰ��
				        			if (room.board.turn%2 != 0) {//Ȧ����?
				                		if (room.board.check('b')) {//pass check
				                			if(!room.board.select(y, x, 'w')) {//����  �� �ִ��� Ȯ��
				                				room.board.reverse(y, x, 'w'); //������
				                				room.board.count();
				                				room.board.turn++;
				                				sendMessageRoom("Board|" + room.board.print()+ "," + Integer.toString(room.board.turn));//room�� board����
				                				if(room.board.end()) {
				                					sendMessageRoom("Endgame|" + room.board.result());
				                				}
				                 				
				                			}
				                			else{//���� �� ����
				                				sendMessage("Error_location|" );//location error
				                			}            			
				                		}
				                		else {//pass
				                			room.board.turn++;
				                			sendMessageRoom("Board|" + room.board.print()+ "," + Integer.toString(room.board.turn));//room�� board����
				                		}
				        			}       			
				        			else {//Ȧ������ �ƴ�
				        				sendMessage("Error_turn|" );//turn error
				        			}           		
				                }
								
							}
						}						
						break;
					}
				}
			}
		}catch (IOException e) {
			System.out.println("��");
			e.printStackTrace();
		}
	}

	
	public String getRooms(){
		String titles = "";
		synchronized (rooms) {
			for(int i = 0; i < rooms.size(); i++){
				Room room= rooms.get(i);
				titles += room.title + "-" + room.id;
				
				if(i < rooms.size()-1)
					titles += ",";
			}
			return titles;			
		}		
	}


	public String getRoomPlayers(){
		String players = ""; 
		for(int i=0; i<room.players.size(); i++){
			Player player = room.players.get(i);
			players += player.player_name;
			
			if(i < room.players.size()-1)
				players += ",";
		}
		return players;
	}
	
	public String getWaitPlayers(){
		String players="";
		synchronized (wait_players) {	
			for(int i=0; i<wait_players.size(); i++){
				Player player = wait_players.get(i);
				players += player.player_name;
				if(i < wait_players.size()-1)
					players += ",";
			}
		}
		
		return players;
	}
	
	public void sendMessageWait(String message){	 
		// Send Message to Wait Players
		synchronized (wait_players) {	
			for(int i = 0; i < wait_players.size(); i++){
				Player player = wait_players.get(i); 
				player.sendMessage(message);
			}
		}	
	}
	
	public void sendMessageRoom(String message){
		// Send Message to Room Players
		for(int i = 0; i< room.players.size(); i++){
			Player player = room.players.get(i);
			player.sendMessage(message);
		}
	}
	
	// Send Message to Other Player in Room
	public void sendMessageOtherPlayer(String message) {
		for(int i =0; i < room.players.size(); i++) {
			Player player = room.players.get(i);
			if(player.player_name.equals(player_name)) {
				// pass
			}else {
				System.out.println(player.player_name);
				sendMessage(message);
			}
		}
	}
	public void sendMessage(String message){
		try {
			to_client.write((message + "\n").getBytes());
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}