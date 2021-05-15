
import java.util.Scanner;

public class runGame {

	public static void main(String[] args) {
		
		
		/*Procedure of game
		 *initialize board
		 *while(not finish){
		 *	print
		 *	select
		 *	reverse
		 * }
		 */
		
		
		Board board= new Board();// 게임 시작  & initialize
		Scanner scn = new Scanner(System.in);
		int num=0;
		while(board.notfinish()) {
			board.print();
			int x=0, y=0;
			
			while(x<1 || y<1 || x>8 || y>8) {// 이동할 위치 고르기
				x= scn.nextInt();
				y= scn.nextInt();
			}
			
			char tmp=' ';
			if(num%2==0) tmp='b'; //검정색 부터 시작
			else tmp='w';
			board.select(x-1, y-1, tmp); //고른 위치에 오셀로 두기
			board.reverse(x-1, y-1, tmp); //뒤집기
			num++;
			
			/*
			 * 만약 GUI에서 색을 고르고 변수에 저장할 수 있으면 아래와 같이 변경(예: char player_color= 'w' or 'b')
			 * 
			 * board.select(x-1, y-1, player_color);
			 * board.reverse(x-1, y-1, player_color);
			 */
		}
	}
}