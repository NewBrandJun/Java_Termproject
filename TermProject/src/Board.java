
public class Board {

	Othello[][] board= new Othello[8][8];
	private int N=8;
	private int num;
	private int white;
	private int black;
	private int check;
	
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
		if(num==N*N) return false;
		else if((white==0 && num>4) || (black==0 && num>4)) return false;
		return true;
	}
	
	
	private void check_right(int x, int y, char c, int r) {
		if(y>=N-2) return;
		else if(board[x][y+1].getColor()==' ' || board[x][y+1].getColor()==c) return;
		
		int i, yy=0;
		for(i=y+1; i<N; i++) {
			if(board[x][i].getColor()==c) {
				yy=i; break;
			}
		}
		for(i=y+1; i<yy; i++) {
			if(r==1)board[x][i].setColor(c);
			this.check++;
		}
	}
	
	private void check_left(int x, int y, char c, int r) {
		if(y<=1) return;
		else if(board[x][y-1].getColor()==' ' || board[x][y-1].getColor()==c) return;
		
		int i, yy=N;
		for(i=y-1; i>=0; i--) {
			if(board[x][i].getColor()==c) {
				yy=i; break;
			}
		}
		for(i=y-1; i>yy; i--) {
			if(r==1)board[x][i].setColor(c);
			this.check++;
		}
	}
	private void check_up(int x, int y, char c, int r) {
		if(x<=1) return;
		else if(board[x-1][y].getColor()==' ' || board[x-1][y].getColor()==c) return;
		
		int i, xx=N;
		for(i=x-1; i>=0; i--) {
			if(board[i][y].getColor()==c) {
				xx=i; break;
			}
		}
		for(i=x-1; i>xx; i--) {
			if(r==1)board[i][y].setColor(c);
			this.check++;
		}
	}
	private void check_down(int x, int y, char c, int r) {
		if(x>=N-2) return;
		else if(board[x+1][y].getColor()==' ' || board[x+1][y].getColor()==c) return;
		
		int i, xx=0;
		for(i=x+1; i<N; i++) {
			if(board[i][y].getColor()==c) {
				xx=i; break;
			}
		}
		for(i=x+1; i<xx; i++) {
			if(r==1)board[i][y].setColor(c);
			this.check++;
		}
	}
	private void check_ur(int x, int y, char c, int r) {
		if(x<=1 || y>=N-2) return;
		else if(board[x-1][y+1].getColor()==' ' || board[x-1][y+1].getColor()==c) return;
		
		int i, j, xx=N, yy=0, next=0;
		for(i=x-1; i>=0; i--) {
			for(j=y+1; j<N; j++) {
				if((x+y)==(i+j) && board[i][j].getColor()==c) {
					xx= i; yy= j; next=1; break;
				}
			}
			if(next==1)break;
		}
		
		for(i=x-1; i>=xx; i--) {
			for(j=y+1; j<=yy; j++) {
				if((x+y)==(i+j)) {
					if(r==1)board[i][j].setColor(c);
					this.check++;
				}
			}
		}
	}
	private void check_ul(int x, int y, char c, int r) {
		if(x<=1 || y<=1) return;
		else if(board[x-1][y-1].getColor()==' ' || board[x-1][y-1].getColor()==c) return;
		
		int i, j, xx=N, yy=N, tx=x-1, ty=y-1, next=0;
		for(i=x-1; i>=0; i--) {
			for(j=y-1; j>=0; j--) {
				if(tx==i && ty==j && board[i][j].getColor()==c) {
					xx= i; yy= j; next=1;
					break;
				}
			}
			tx--; ty--;
			if(next==1)break;
		}
		tx=x-1; ty=y-1;
		for(i=x-1; i>=xx; i--) {
			for(j=y-1; j>=yy; j--) {
				if(tx==i && ty==j) {
					if(r==1)board[i][j].setColor(c);
					this.check++;
				}
			}
			tx--; ty--;
		}
	}
	private void check_dr(int x, int y, char c, int r) {
		if(x>=N-2 || y>=N-2) return;
		else if(board[x+1][y+1].getColor()==' ' || board[x+1][y+1].getColor()==c) return;
		
		int i, j, xx=0, yy=0, tx=x+1, ty=y+1, next=0;
		for(i=x+1; i<N; i++) {
			for(j=y+1; j<N; j++) {
				if(tx==i && ty==j && board[i][j].getColor()==c) {
					xx= i; yy= j; next=1; break;
				}
			}
			tx++; ty++;
			if(next==1)break;
		}
		tx=x+1; ty=y+1;
		for(i=x+1; i<=xx; i++) {
			for(j=y+1; j<=yy; j++) {
				if(tx==i && ty==j) {
					if(r==1) board[i][j].setColor(c);
					this.check++;
				}
			}
			tx++; ty++;
		}
	}
	private void check_dl(int x, int y, char c, int r) {
		if(x>=N-2 || y<=1) return;
		else if(board[x+1][y-1].getColor()==' ' || board[x+1][y-1].getColor()==c) return;
		
		int i, j, xx=0, yy=N, next=0;
		for(i=x+1; i<N; i++) {
			for(j=y-1; j>=0; j--) {
				if((x+y)==(i+j) && board[i][j].getColor()==c) {
					xx= i; yy= j; next=1; break;
				}
			}
			if(next==1)break;
		}
		
		for(i=x+1; i<=xx; i++) {
			for(j=y-1; j>=yy; j--) {
				if((x+y)==(i+j)) {
					if(r==1) board[i][j].setColor(c);
					this.check++;
				}
			}
		}
	}
	
	public void reverse(int x, int y, char player_color) {
		this.check=0;
		if(board[x][y].getColor()==' ') {
			check_right(x, y, player_color, 1);
			check_left(x, y, player_color, 1);
			check_up(x, y, player_color, 1);
			check_down(x, y, player_color, 1);
			check_ur(x, y, player_color, 1);
			check_ul(x, y, player_color, 1);
			check_dr(x, y, player_color, 1);
			check_dl(x, y, player_color, 1);
			if(check>0) {
				board[x][y].setColor(player_color); num++;
			}
		}
	}	
	
	
	public void count() {
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
	
	public boolean check(char player_color) {
		int i, j;
		this.check=0;
		for(i=0; i<N; i++) {
			for(j=0; j<N; j++) {
				if(board[i][j].getColor()==' ') {
					check_right(i, j, player_color, 0);
					check_left(i, j, player_color, 0);
					check_up(i, j, player_color, 0);
					check_down(i, j, player_color, 0);
					check_ur(i, j, player_color, 0);
					check_ul(i, j, player_color, 0);
					check_dr(i, j, player_color, 0);
					check_dl(i, j, player_color, 0);
				}
			}
		}
		if(this.check>0) return true;
		return false;
	}
	
	public void hint(char player_color) {
		int i, j;
		for(i=0;i<N; i++) {
			for(j=0; j<N; j++) {
				if(!select(i, j, player_color)) {
					board[i][j].setPr_c('*');
				}
			}
		}
		this.print();
		for(i=0;i<N; i++) {
			for(j=0; j<N; j++) {
				if(board[i][j].getPr_c()=='*') {
					board[i][j].setPr_c('бр');
				}
			}
		}
	}
	
	public boolean select(int i, int j, char player_color) {
		if(i<0 || j<0 || i>N-1 || j>N-1) return true;
		else if(board[i][j].getColor()!=' ') return true;
		
		this.check=0;
		if(board[i][j].getColor()==' ') {
			check_right(i, j, player_color, 0);
			check_left(i, j, player_color, 0);
			check_up(i, j, player_color, 0);
			check_down(i, j, player_color, 0);
			check_ur(i, j, player_color, 0);
			check_ul(i, j, player_color, 0);
			check_dr(i, j, player_color, 0);
			check_dl(i, j, player_color, 0);
		}
		if(this.check>0) return false;
		return true;
	}
	
	public void result() {
		if(black>white)System.out.println("Black Win");
		else if(white>black)System.out.println("White Win");
		else if(white==black) System.out.println("Drawn");
	}
}
