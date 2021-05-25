public class Othello {
	
	private char color;
	private int x;
	private int y;
	private char pr_c;
	
	public Othello(int x, int y, char color) {
		setX(x);
		setY(y);
		setColor(color);
	}
	
	public char getColor() {
		return color;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public char getPr_c() {
		return pr_c;
	}
	

	public void setColor(char color) {
		this.color = color;
		if(color=='b') this.pr_c= '¡Ü';
		else if(color=='w') this.pr_c='¡Û';
		else this.pr_c='¡à';
	}
	
	public void setPr_c(char mark) {
		this.pr_c=mark;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
