
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
		
		/*
		 * ���� GUI���� ���� ���� ������ ������ �� ������ �Ʒ��� ���� ����(��: char player_color= 'w' or 'b')
		 * 
		 * board.select(x-1, y-1, player_color);
		 * board.reverse(x-1, y-1, player_color);
		 */
		
		Board board= new Board();// ���� ����  & initialize
		Scanner scn = new Scanner(System.in);
		int num=0;
		while(board.notfinish()) {
			int ch=0;
			
			char tmp='w';
			if(num%2==0) tmp='b'; //������ ���� ����
			
			if(board.check(tmp)) {//Pass Ȯ��
				board.hint(tmp);
				int x=0, y=0;//input
				while(board.select(x-1, y-1, tmp)) {// �̵��� ��ġ ����
					if(num%2==0) System.out.print("Black: "); //������ ���� ����
					else System.out.print("White: ");
					x= scn.nextInt();
					y= scn.nextInt();
				}
				board.reverse(x-1, y-1, tmp); //������
				board.count();
			}
			else {
				System.out.println("PASS");
				if(ch-num==-1) break; //2�� �������� pass -> ��, �� �Ѵ� ������ �ѱ� ���
				ch=num;
			}
			num++;
		}
		
		//���(���� ��)
		board.print();
		board.result();
	}
}