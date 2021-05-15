
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
			int ch=0;
			
			char tmp='w';
			if(num%2==0) tmp='b'; //검정색 부터 시작
			
			if(board.check(tmp)) {//Pass 확인
				board.print();
				
				int x=0, y=0;//input
				while(board.select(x-1, y-1, tmp)) {// 이동할 위치 고르기
					if(num%2==0) System.out.print("Black: "); //검정색 부터 시작
					else System.out.print("White: ");
					x= scn.nextInt();
					y= scn.nextInt();
				}
				
				board.reverse(x-1, y-1, tmp); //뒤집기
				board.count();
			}
			else {
				System.out.println("PASS");
				if(ch-num==-1) break; //2번 연속으로 pass -> 흑, 백 둘다 순서를 넘길 경우
				ch=num;
			}
			num++;
			
			/*
			 * 만약 GUI에서 색을 고르고 변수에 저장할 수 있으면 아래와 같이 변경(예: char player_color= 'w' or 'b')
			 * 
			 * board.select(x-1, y-1, player_color);
			 * board.reverse(x-1, y-1, player_color);
			 */
		}
		board.print();
		board.result();
	}
}