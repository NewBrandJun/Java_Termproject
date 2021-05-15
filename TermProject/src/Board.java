
public class Board {

	Othello[][] board= new Othello[8][8];
	private int N=8;
	private int num;
	private int white;
	private int black;
	
	Board(){//initialize
		int i, j;
		this.num=4;
		for(i=0; i<N; i++) {
			for(j=0; j<N; j++) {
				if((i==(N/2)-1 && j==(N/2)-1) || (i==N/2 && j==N/2)) {//white
					this.board[i][j]= new Othello(i, j, 'w');
				}
				else if((i==N/2 && j==(N/2)-1) || (i==(N/2)-1 && j==N/2)) {//black
					
					this.board[i][j]= new Othello(i, j, 'b');
				}
				else this.board[i][j]= new Othello(i, j, ' ');
			}
		}
	}
	
	public void print() {
		this.count();
		int i, j;
		System.out.println("    1 2 3 4 5 6 7 8");
		for(i=0; i<N; i++) {
			System.out.print(i+1 + "   ");
			for(j=0; j<N; j++) {
				System.out.print(this.board[i][j].getPr_c() + " ");
			}
			System.out.print("\n");
		}
	}
	
	
	public boolean notfinish() {
		if(num==N*N) {
			if(black>white)System.out.println("Black Win");
			else if(white>black)System.out.println("White Win");
			else System.out.println("Drawn");
			return false;
		}
		return true;
	}
	
	
	public void select(int x, int y, char player_color) {
		if(board[x][y].getColor()==' ') board[x][y].setColor(player_color);
		num++;
	}
	
	
	private void check_right(int x, int y, char c) {
		if(y>=N-2) return;
		else if(board[x][y+1].getColor()==' ' || board[x][y+1].getColor()==c) return;
		
		int i, yy=0;
		for(i=y+1; i<N; i++) {
			if(board[x][i].getColor()==c) {
				yy=i; break;
			}
		}
		for(i=y+1; i<yy; i++) {
			board[x][i].setColor(c);
		}
	}
	
	private void check_left(int x, int y, char c) {
		if(y<=1) return;
		else if(board[x][y-1].getColor()==' ' || board[x][y-1].getColor()==c) return;
		
		int i, yy=N;
		for(i=y-1; i>=0; i--) {
			if(board[x][i].getColor()==c) {
				yy=i; break;
			}
		}
		for(i=y-1; i>yy; i--) {
			board[x][i].setColor(c);
		}
	}
	private void check_up(int x, int y, char c) {
		if(x<=1) return;
		else if(board[x-1][y].getColor()==' ' || board[x-1][y].getColor()==c) return;
		
		int i, xx=N;
		for(i=x-1; i>=0; i--) {
			if(board[i][y].getColor()==c) {
				xx=i; break;
			}
		}
		for(i=x-1; i>xx; i--) {
			board[i][y].setColor(c);
		}
	}
	private void check_down(int x, int y, char c) {
		if(x>=N-2) return;
		else if(board[x+1][y].getColor()==' ' || board[x+1][y].getColor()==c) return;
		
		int i, xx=0;
		for(i=x+1; i<N; i++) {
			if(board[i][y].getColor()==c) {
				xx=i; break;
			}
		}
		for(i=x+1; i<xx; i++) {
			board[i][y].setColor(c);
		}
	}
	private void check_ur(int x, int y, char c) {
		if(x<=1 || y>=N-2) return;
		else if(board[x-1][y+1].getColor()==' ' || board[x-1][y+1].getColor()==c) return;
		
		int i, j, xx=N, yy=0;
		for(i=x-1; i>=0; i--) {
			for(j=y+1; j<N; j++) {
				if((x+y)==(i+j) && board[i][j].getColor()==c) {
					xx= i; yy= j; break;
				}
			}
		}
		for(i=x-1; i>xx; i--) {
			for(j=y+1; j<yy; j++) {
				board[i][j].setColor(c);
			}
		}
	}
	private void check_ul(int x, int y, char c) {
		if(x<=1 || y<=1) return;
		else if(board[x-1][y-1].getColor()==' ' || board[x-1][y-1].getColor()==c) return;
		
		int i, j, xx=N, yy=N, tmp=2, next=0;
		for(i=x-1; i>=0; i--) {
			for(j=y-1; j>=0; j--) {
				if((x+y-tmp)==(i+j) && board[i][j].getColor()==c) {
					xx= i; yy= j; next=1;
					break;
				}
			}
			if(next==1)break;
			tmp=tmp*2;
		}
		
		for(i=x-1; i>xx; i--) {
			for(j=y-1; j>yy; j--) {
				board[i][j].setColor(c);
			}
		}
	}
	private void check_dr(int x, int y, char c) {
		if(x>=N-2 || y>=N-2) return;
		else if(board[x+1][y+1].getColor()==' ' || board[x+1][y+1].getColor()==c) return;
		
		int i, j, xx=0, yy=0, tmp=2, next=0;
		for(i=x+1; i<N; i++) {
			for(j=y+1; j<N; j++) {
				if((x+y+tmp)==(i+j) && board[i][j].getColor()==c) {
					xx= i; yy= j; next=1;
					break;
				}
			}
			if(next==1)break;
			tmp=tmp*2;
		}
		
		for(i=x+1; i<xx; i++) {
			for(j=y+1; j<yy; j++) {
				board[i][j].setColor(c);
			}
		}
	}
	private void check_dl(int x, int y, char c) {
		if(x>=N-2 || y<=1) return;
		else if(board[x+1][y-1].getColor()==' ' || board[x+1][y-1].getColor()==c) return;
		
		int i, j, xx=0, yy=N;
		for(i=x+1; i<N; i++) {
			for(j=y-1; j>=0; j--) {
				if((x+y)==(i+j) && board[i][j].getColor()==c) {
					xx= i; yy= j; break;
				}
			}
		}
		for(i=x+1; i<xx; i++) {
			for(j=y-1; j>yy; j--) {
				board[i][j].setColor(c);
			}
		}
	}
	
	public void reverse(int x, int y, char player_color) {
		check_right(x, y, player_color);
		check_left(x, y, player_color);
		check_up(x, y, player_color);
		check_down(x, y, player_color);
		check_ur(x, y, player_color);
		check_ul(x, y, player_color);
		check_dr(x, y, player_color);
		check_dl(x, y, player_color);
	}	
	
	
	private void count() {
		int i, j;
		this.black=0; this.white=0;
		for(i=0; i<N; i++) {
			for(j=0; j<N; j++) {
				if(board[i][j].getColor()=='b') this.black++;
				else if(board[i][j].getColor()=='w') this.white++;
			}
		}
		System.out.println("black: " + this.black + "\twhite: " + this.white + "\n");
	}
}
