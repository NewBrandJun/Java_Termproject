package gui;

import java.util.Vector;

import source.Dimensions;
import source.Images;

public class Room {
	int id;
    String title;
    static Board board = new Board();
    Vector<Player> players;
    int ready_count;
    
    public Room() {
    	players = new Vector<>();
	} 
}