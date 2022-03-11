
import java.util.Scanner;

public class runGame {

	public static void main(String[] args) {
		//first edit

		Board board= new Board();// ���� ����  & initialize
		Scanner scn = new Scanner(System.in);
		int num=0;
		int ch=0;
		while(board.notfinish()) {
			char tmp='w';
			if(num%2==0) tmp='b'; //������ ���� ����
			
			if(board.check(tmp)) {//Pass Ȯ��
				board.hint(tmp);
				int x=0, y=0;//input
				while(board.select(x-1, y-1, tmp)) {// �̵��� ��ġ ������
					if(num%2==0) System.out.print("Black: ");
					else System.out.print("White: ");
					x= scn.nextInt();
					y= scn.nextInt();
				}
				board.reverse(x-1, y-1, tmp); //������
				board.count();
			}
			else {
				if(ch-num==-1) break; //2�� �������� pass -> ��, �� �Ѵ� ������ �ѱ� ���
				System.out.println("PASS");
				ch=num;
			}
			num++;
		}
		board.print();
		board.count();
		board.result();
	}
}