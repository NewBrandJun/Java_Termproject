
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
		
		
		Board board= new Board();// ���� ����  & initialize
		Scanner scn = new Scanner(System.in);
		int num=0;
		while(board.notfinish()) {
			board.print();
			
			int x=0, y=0;//input
			while(x<1 || y<1 || x>8 || y>8) {// �̵��� ��ġ ����
				x= scn.nextInt();
				y= scn.nextInt();
			}
			
			char tmp=' ';
			if(num%2==0) tmp='b'; //������ ���� ����
			else tmp='w';
			board.select(x-1, y-1, tmp); //�� ��ġ�� ������ �α�
			board.reverse(x-1, y-1, tmp); //������
			num++;
			
			/*
			 * ���� GUI���� ���� ���� ������ ������ �� ������ �Ʒ��� ���� ����(��: char player_color= 'w' or 'b')
			 * 
			 * board.select(x-1, y-1, player_color);
			 * board.reverse(x-1, y-1, player_color);
			 */
		}
	}
}