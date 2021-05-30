package gui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable{
	// All Wait Players
	static Vector<Player> wait_players;
	// All Rooms
	static Vector<Room> rooms;
	
	// Constructor
	public Server() {
		wait_players = new Vector<>();
		rooms = new Vector<>();
		
		new Thread(this).start();
	}

	@Override
	public void run(){
		try {
			ServerSocket server_socket = new ServerSocket(8000);
			
			System.out.println("Start Server...");
			
			while(true){
				// Waiting
				Socket socket = server_socket.accept();
				
				new Player(socket, wait_players, rooms);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	   		
	}
	
	public static void main(String[] args) {
		new Server();
	}

}

