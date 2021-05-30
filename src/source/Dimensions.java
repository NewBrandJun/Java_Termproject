package source;

public class Dimensions {
	// Board Cell Size
	private final float CELL_ROW = (float) 44.5;
	private final float CELL_COL = (float) 48.75;
	
	// 8 X 8
	private final int SIZE = 8;
	
	// Frame Size
	private final int FRAME_ROW = 920;
	private final int FRAME_COL = 670;
	
	// Icon Size
	private final int ICON = 38;
	
	// Board Size
	private final int BOARD_ROW = 660;
	private final int BOARD_COL = 540;
	
	// Ready Button Size
	private final int READY_ROW = 100;
	private final int READY_COL = 50;
	
	// Getter
	public float getCellRow() {
		return CELL_ROW;
	}
	
	public float getCellCol() {
		return CELL_COL;
	}
	
	public int getSize() {
		return SIZE;
	}
	
	public int getFrameRow() {
		return FRAME_ROW;
	}
	
	public int getFrameCol() {
		return FRAME_COL;
	}
	
	public int getIcon() {
		return ICON;
	}
	
	public int getBoardRow() {
		return BOARD_ROW;
	}
	
	public int getBoardCol() {
		return BOARD_COL;
	}
	
	public int getReadyRow() {
		return READY_ROW;
	}
	
	public int getReadyCol() {
		return READY_COL;
	}
	
}