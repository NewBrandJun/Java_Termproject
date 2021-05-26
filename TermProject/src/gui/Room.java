package gui;

import java.util.Vector;

import source.Dimensions;
import source.Images;

public class Room {
    String title;
    int count;
    Vector<Player> players;

    public Room() {
    	players = new Vector<>();
	} 
}