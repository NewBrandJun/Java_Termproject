package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import source.Dimensions;

public class Player extends Thread{

	// From Server
	private Vector<Player> wait_players;
	private Vector<Room> rooms;
	
	// New Room
	private Room room;

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
	
	public Player(Socket s, Server server) { 
		wait_players = server.wait_players;
		rooms = server.rooms;
		
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
						wait_players.add(this);						
						break;
					
					case "PlayerName":
						// Receive Player Name
						player_name = messages[1]; 
						
						// Send Rooms
						sendMessageWait("Rooms|"+ getRooms());
						// Send Wait Players
						sendMessageWait("WaitPlayers|"+ getWaitPlayers());
						break;
						
					case "RoomTitle": 
						// Create Room
						room = new Room();
						// Receive Room Title
						room.title = messages[1];
						room.count = 1;
						rooms.add(room);
						
						wait_players.remove(this);
						room.players.add(this);
						
						// Send Enter Signal
						sendMessageRoom("EnterRoom|" + player_name);
						// Send Rooms
						sendMessageWait("Rooms|"+ getRooms());
						// Send Wait Players
						sendMessageWait("WaitPlayers|"+ getWaitPlayers());
						break;

					case "EnterRoom": 
						// Enter Room
						for(int i=0; i<rooms.size(); i++){//방이름 찾기!!
							Room r = rooms.get(i);
							if(r.title.equals(messages[1])){//일치하는 방 찾음!!		    	        	
								room = r;
								room.count++;//인원수 1증가
								break;
							}
						}
						
						wait_players.remove(this);
						room.players.add(this);
						
						// Send Enter Message to Room Players
						sendMessageRoom("EnterRoom|" + player_name);
						
						// Send Room Title
						sendMessage("RoomTitle|"+ room.title);
						sendMessageWait("Rooms|"+ getRooms());
						sendMessageWait("WaitPlayers|"+ getWaitPlayers());
						break;
						
					case "ExitRoom": 
						// Receive Exit Signal						
						room.count--;//인원수 감소
						
						// Send Exit Message to Room Players
						sendMessageRoom("Exit|" + player_name);
						
						room.players.remove(this);	
						wait_players.add(this);
						
						// Send Rooms
						sendMessageWait("Rooms|"+ getRooms());
						// Send Wait Players
						sendMessageWait("WaitPlayers|"+ getWaitPlayers());						
						break;
						
					case "Message":						
						// Send Message to Room Players
						sendMessageRoom("Message|["+player_name +"]▶ "+messages[1]);
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
							// Send Position to Room Players
							sendMessageRoom("Position|" + Integer.toString(x) + "," + Integer.toString(y));
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
		for(int i = 0; i < rooms.size(); i++){
			Room room= rooms.get(i);
			titles += room.title + "--" + room.count;
			
			if(i < rooms.size()-1)
				titles += ",";
		}
		return titles;
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
		for(int i=0; i<wait_players.size(); i++){
			Player player = wait_players.get(i);
			players += player.player_name;
			if(i < wait_players.size()-1)
				players += ",";
		}
		return players;
	}
	
	public void sendMessageWait(String message){	 
		// Send Message to Wait Players
		for(int i = 0; i < wait_players.size(); i++){
			Player player = wait_players.get(i); 
			player.sendMessage(message);
		}
	}
	
	public void sendMessageRoom(String message){
		// Send Message to Room Players
		for(int i = 0; i< room.players.size(); i++){
			Player player = room.players.get(i);
			player.sendMessage(message);
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