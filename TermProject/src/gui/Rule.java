package gui;

import source.Dimensions;

public class Rule {
	Dimensions dim;
	private int map[][];
	private boolean start_flag;
	private int turn = 1;
	
	public Rule(Dimensions dim) {
		this.dim = dim;
		map = new int[dim.getSize()][dim.getSize()];
		start_flag = false;
	}
	
	public void init() {
		for (int i = 0; i < dim.getSize(); i++) {
			for (int j = 0; j < dim.getSize(); j++) {
				map[i][j] = 0;
			}
		}
	}
	
	public void inputWord(Piece player) {
		map[player.getY()][player.getX()] = player.getColor();
	}
	
	public boolean checkInput(int y, int x) {
		if (y < 0 || y > dim.getSize() || x < 0 || x > dim.getSize() || map[y][x] != 0 ) {
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
	
	public boolean endGame(Piece player) {
		return false;
	}
	
	public boolean getStartFlag() {
		return start_flag;
	}
	
	public void setStartFlag(boolean flag) {
		start_flag = flag;
	}
	
}
