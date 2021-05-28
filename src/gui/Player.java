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
	// 클릭 허용 범위
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
		
		// x의 click가능한 range
		rangeX_below = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeX_below[i] = 163+(i*dim.getCellRow());
		}
		rangeX_upper = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeX_upper[i] = 198+(i*dim.getCellRow());
		}
		
		// y의 click가능한 range
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
						// Create Room
						room = new Room();
						// Receive Room Title
						room.title = messages[1];
						room.ready_count = 0;
						
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
						break;
						
					case "Exit": 
						// Receive Exit Signal						
						
						// Send Exit Message to Room Players
						sendMessageRoom("ExitRoom|" + player_name);
						
						// 각 Player들의 board 초기화
						room.board.init();
						room.board.turn = 0;
						room.ready_count = 0;						
						sendMessageRoom("Board|" + room.board.print() + "," + Integer.toString(room.board.turn));//room에 board전송
						
						// 나가는 player의 chatting area, ready button 초기화						
						sendMessage("RoomFrameInit|");
																							
							
						room.players.remove(this);	
						//synchronized (wait_players) {
							wait_players.add(this);
							sendMessageWait("WaitPlayers|"+ getWaitPlayers());	
						//}					
						if(room.players.size()==0) {
							synchronized (rooms) {
								rooms.remove(room);
							}							
						}else {
							// 방에 있는 다른 player에게는 나갔다는 메시지 보낸 후 ready button 초기화
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
						sendMessageRoom("Message|["+player_name +"]▶ "+messages[1]);
						break;
					case "Ready":						
						room.ready_count++;
						
						if(room.ready_count == 2) {
							// Game Start
							sendMessageRoom("Start|");
							room.players.get(0).color = 'b';
							room.players.get(1).color = 'w';
							sendMessageRoom("Board|" + room.board.print() + "," + Integer.toString(room.board.turn));//room에 board전송
						}
						break;
					case "UnReady":						
						room.ready_count--;						
						break;
					case "Position":
						// Piece를 둘 수 있는지 없는지에 대한 Flag
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
								if(color =='b'){//p1(흑)인경우
				        			if (room.board.turn%2 == 0) {//짝수턴?
				                		if (room.board.check('b')) {//pass check
				                			if(!room.board.select(y, x, 'b')) {//놓을  수 있는지 확인
				                				room.board.reverse(y, x, 'b'); //뒤집기
				                				room.board.count();
				                				room.board.turn++;
				                				sendMessageRoom("Board|" + room.board.print() + "," + Integer.toString(room.board.turn));//room에 board전송
				                 				
				                			}
				                			else{//놓을 수 없음
				                				sendMessage("Error_location|" );//location error
				                			}            			
				                		}
				                		else {//pass
				                			//TODO:pass
				                			if (room.board.turn-room.board.temp==1) {
				                				/*sendMsg(code_p1+"/"+"ENDGAME");
				                				sendMsg(code_p2+"/"+"ENDGAME");
				                				board.print();
				                				board.result();*/
				                				//TODO:양측에 game종료 선언, position 전송, 결과전송
				                				
				                			}
				                			room.board.temp = room.board.turn;
				                		}
				        			}       			
				        			else {//짝수턴이 아님
				        				sendMessage("Error_turn|" );//turn error
				        			}           		
				                }
								
								else if(color =='w'){//p2(백)인경우
				        			if (room.board.turn%2 != 0) {//홀수턴?
				                		if (room.board.check('b')) {//pass check
				                			if(!room.board.select(y, x, 'w')) {//놓을  수 있는지 확인
				                				room.board.reverse(y, x, 'w'); //뒤집기
				                				room.board.count();
				                				room.board.turn++;
				                				sendMessageRoom("Board|" + room.board.print()+ "," + Integer.toString(room.board.turn));//room에 board전송
				                 				
				                			}
				                			else{//놓을 수 없음
				                				sendMessage("Error_location|" );//location error
				                			}            			
				                		}
				                		else {//pass
				                			//TODO:pass
				                			if (room.board.turn-room.board.temp==1) {
				                				/*sendMsg(code_p1+"/"+"ENDGAME");
				                				sendMsg(code_p2+"/"+"ENDGAME");
				                				board.print();
				                				board.result();*/
				                				//TODO:양측에 game종료 선언, position 전송, 결과전송
				                				
				                			}
				                			room.board.temp = room.board.turn;
				                		}
				        			}       			
				        			else {//홀수턴이 아님
				        				sendMessageRoom("Error_turn|" );//turn error
				        			}           		
				                }
								
							}
						}						
						break;
					}
				}
			}
		}catch (IOException e) {
			System.out.println("★");
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