package gui;

import source.Dimensions;

public class Rule {
	// From Wait Frame
	private Dimensions dim;
	public int map[][];
	
	// True : °ÔÀÓ ½ÃÀÛ
	private boolean start_flag;
	
	// Â¦¼ö : Èæ, È¦¼ö : ¹é
	private int turn;
	
	public Rule(Dimensions dim) {
		this.dim = dim;
		
		map = new int[dim.getSize()][dim.getSize()];
		
		start_flag = false;
		turn = 0;
	}

	public int[][] getMap(){
		return map;
	}
	
	public void init() {
		for (int i = 0; i < dim.getSize(); i++) {
			for (int j = 0; j < dim.getSize(); j++) {
				map[i][j] = 0;
			}
		}
	}
	public int getTurn() {
		return turn;
	}
	
	public int setTurn(int t) {
		return turn = t;
	}

	public boolean getStartFlag() {
		return start_flag;
	}
	
	public void setStartFlag(boolean flag) {
		start_flag = flag;
	}
	
}
