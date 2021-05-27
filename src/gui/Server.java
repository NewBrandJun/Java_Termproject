package gui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable{
	// Wait Players
	public Vector<Player> wait_players;
	// All Rooms
	public Vector<Room> rooms;
	
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
				// ´ë±â
				Socket socket = server_socket.accept();
				
				Player player = new Player(socket, this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	   
		
	}
	
	public static void main(String[] args) {
		new Server();
	}

}

