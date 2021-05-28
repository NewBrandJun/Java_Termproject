package gui;

import java.util.Vector;

import source.Dimensions;
import source.Images;

public class Room {
	int id;
    String title;
    static Board board;
    Vector<Player> players;
    int ready_count;
    
    public Room(String title) {
    	this.players = new Vector<>();
    	this.board = new Board();
    	this.ready_count = 0;
    	this.title = title;
	} 
}