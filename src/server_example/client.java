package server_example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class client {
 
    public static void main(String[] args) {
    	Board board = new Board();
        Socket sck = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        boolean endFlag = false;
        boolean start = false;
        String id = null;
        String code = null;
        int num = 0;
        String full = "This room is already full. Please input another room number code : ";       
        try {
            //서버의 소캣번호 입력
            sck = new Socket("222.101.148.141", 1525);
            pw = new PrintWriter(new PrintWriter(sck.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sck.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            //code목록 읽어오기-방번호 입력요청
            System.out.println("코드읽어오기");
            String str = br.readLine();
            System.out.println(str);
            //방번호 입력받기
            code = keyboard.readLine();
            pw.println(code);//code 스트링에 담아보내기
            pw.flush();
            str = br.readLine();
            while(str.compareTo(full)==0) {
            	System.out.println(str);
            	code = keyboard.readLine();
                pw.println(code);//code 스트링에 담아보내기
                pw.flush();
                str = br.readLine();
            }
            code = str;
            //서버로 부터 계속 읽어오는 스레드 실행
            InputThread it = new InputThread(sck,br,board,start,num);
            it.start();
            String line = null;
            while((line = keyboard.readLine())!=null){
                pw.println(code+"/"+line);
                pw.flush();
                if(line.equals("quit")){
                    System.out.println("시스템을 종료합니다.");
                    endFlag = true;
                    break;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null)
                    br.close();
                if(pw != null)
                    pw.close();
                if(sck != null)
                    sck.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
class InputThread extends Thread
{
    Socket sck = null;
    int num;
    int x=0;
    int y=0;
    char color;
    BufferedReader br = null;
    boolean start;
    Board board;
    String starting = "Starting game...";
    public InputThread(Socket sck, BufferedReader br, Board board, boolean start, int num) {
        this.sck = sck;
        this.br = br;
        this.board = board;
        this.start = start;
        this.num = num;
    }
    public void run()//스레드로 서버로부터 계속 읽어오기
    {
        try {
            String line = null;
            //null값이 아니면 계속 읽어다 출력해주기
            while((line = br.readLine()) !=null)
            {
                
                if (line.equals(starting)){//start...             	
                	start = true;
                	board.print();
                	num = 0;
                	System.out.println(line);
                }
                else if (line.startsWith("(") && line.endsWith(")")) {
                	x = line.charAt(1) - '0';
            		y = line.charAt(3) - '0';
            		color = line.charAt(5);
            		board.reverse(x-1, y-1, color); //뒤집기
     				board.count();
     				board.print();
                }
                else if (line.equals("ENDGAME")) {
                	board.print();
                	board.result();
                }
                else {
                	System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("시스템을 종료합니다.");
        }finally {
            try {
                if(sck != null)
                    sck.close();
                if(br !=null)
                    br.close();
                    
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}