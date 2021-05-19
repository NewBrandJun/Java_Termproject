package game;

import Source.Size;
import User.Player;

public class GameInfo {
	Size size;
	private int map[][];
	private int turn = 1;
	
	public GameInfo(Size _size) {
		size = _size;
		map = new int[size.getSize()][size.getSize()];
	}
	
	public void init() {
		for (int i = 0; i < size.getSize(); i++) {
			for (int j = 0; j < size.getSize(); j++) {
				map[i][j] = 0;
			}
		}
	}
	
	public void inputWord(Player player) {
		map[player.getY()][player.getX()] = player.getColor();
	}
	
	public boolean checkInput(int y, int x) {
		if (y < 0 || y > size.getSize() || x < 0 || x > size.getSize() || map[y][x] != 0 ) {
			return false;
		}
		return true;
	}
	
	public int[][] getMap() {
		return map;
	}
	
	public void nextPlayer(int _turn) {
		if(_turn == 1) {
			turn = 2;
		}else {
			turn = 1;
		}		
	}
	
	public int getCurPlayer() {
		return turn;
	}
	
	public boolean endGame(Player player) {
		return false;
	}
	
	
}
