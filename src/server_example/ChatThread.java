package server_example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class ChatThread extends Thread{
    Socket sck;
    String [] split;
    String code;
    String code_p1;
    String code_p2;
    int num;
    int ch;
    BufferedReader br;
    PrintWriter pw;
    //들어온 클라이언트의 Printwriter객체와 code를 저장하고 관리해주는 해쉬맵
    HashMap<String, Object> hash;
    HashMap<String, Object> gamehash;
    ArrayList<String> room;
    Board board;
    
    public ChatThread(Socket sck,HashMap<String, Object> hash, HashMap<String, Object> gamehash, ArrayList<String> room) {
        
        this.sck = sck;
        this.hash = hash;
        this.room = room;
        this.gamehash = gamehash;
        try {
            pw = new PrintWriter(new OutputStreamWriter(sck.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sck.getInputStream()));
            //123_p1은 123_p2랑만 채팅이 가능
            pw.println("Please input your room number code : ");
            pw.flush();
            code = br.readLine();
            code_p1 = code + "_p1";
            code_p2 = code + "_p2";
            synchronized (room) {
            	while (Collections.frequency(room, code) >=2) {//이미 방이꽉참
                	pw.println("This room is already full. Please input another room number code : ");
                    pw.flush();
                    code = br.readLine();	
                }            	
            }
            
            if (Collections.frequency(room, code) > 0) {//놀고있는 방
            	Board board = new Board(); 
            	synchronized (hash) {
                    hash.put(code_p2, pw);
                }
            	synchronized (room) {
                    room.add(code);
                }
            	synchronized (gamehash) {
            		gamehash.put(code, board);
            	}
            	pw.println(code_p2);
            	pw.println("Connected in room "+code+".");
            	pw.println("Starting game...");
                pw.flush();
                sendMsg(code_p2+"/"+"Starting game...");
                num = 0;
                ch = 0;
            }
            else if (Collections.frequency(room, code) == 0) {//새로운 방
            	synchronized (hash) {
                    hash.put(code_p1, pw);
                }
            	synchronized (room) {
                    room.add(code);
                }
            	pw.println(code_p1);
            	pw.println("Connected in room "+code+". wait for other player...");
                pw.flush();
            }
            //직렬화 후 해쉬맵에 저장          
            System.out.println("===="+code+"님과 성공적으로 연결");
        } catch (IOException e) {
            e.printStackTrace();
        }            
    }
    public void run() {
        String line = null;
        try {
            while((line = br.readLine()) != null)
            {//클라이언트로부터 quit을 받으면 종료
                if(line.split("/")[0].equals("quit")){
                    System.out.println(code+"님이 시스탬을 종료합니다...");
                    break;                    
                }
                else if (Collections.frequency(room, code) == 1) {//room에 혼자있는경우, wait for player 전송
                	pw = (PrintWriter) hash.get(line.split("/")[0]);
                	pw.println("wait for player...");
                	pw.flush();
                }              
                else{//아닐 경우 계속 읽어온 데이터를 클라이언트들에게 전송
                	System.out.println(line);
                	if (line.split("/")[1].startsWith("(") && line.split("/")[1].endsWith(")")) {
                		game(line);
                	} else {
                		sendMsg(line);
                	}                 
                }
            }
        } catch (IOException e) {
            System.out.println(code+"님이 시스탬을 강제적으로 종료합니다...");
            
        }finally
        {
            synchronized (hash) {
                hash.remove(code);
            }
            System.out.println("=======================");
            System.out.println("현재 서버에   "+hash.size()+"명...");
            System.out.println("=======================");
            
            try {
                sck.close();
            } catch (IOException e) {
                System.out.println("socket이 정상적으로 종료되지 않았습니다.");
            }
        }
    }
     
    //1:1 전송 (p1이면 p2에게만, p2는 p1에게만 1:1로 전송)
    public void sendMsg(String msg)
    {
        synchronized (hash) 
        {
            pw = null;
            //code가 1로 끝나면 2에게 메시지
             if(msg.split("/")[0].endsWith("1"))
             {//split함수와 replace함수를 이용해 간편하게 수신받을 클라이언트 printwriter객체를 설정
                pw = (PrintWriter) hash.get(msg.split("/")[0].replace("p1", "p2"));
                pw.println(msg.split("/")[1]);
                pw.flush();
            }else if(msg.split("/")[0].endsWith("2"))
            {
                pw = (PrintWriter) hash.get(msg.split("/")[0].replace("p2", "p1"));
                pw.println(msg.split("/")[1]);
                pw.flush();
            }else//잘못된 코드
            {
                pw = (PrintWriter) hash.get(msg.split("/")[0]);
                pw.println("연결된 코드가 잘못된 코드입니다.");
                pw.flush();
            }
        }
    }
    
    public void game(String msg)
    {
    	int x;
    	int y;
        synchronized (gamehash) 
        { 
        	System.out.println(msg.split("/")[0].substring(0,msg.split("/")[0].length()-3));
        	board = (Board) gamehash.get(msg.split("/")[0].substring(0,msg.split("/")[0].length()-3));
        	if (board.notfinish()) {//game 실행중
        		if(msg.split("/")[0].endsWith("1")){//p1(흑)인경우
        			if (board.turn%2 == 0) {//짝수턴?
        				x = msg.split("/")[1].charAt(1) - '0';
                		y = msg.split("/")[1].charAt(3) - '0';
                		if (board.check('b')) {//pass check
                			if(!board.select(x-1, y-1, 'b')) {//놓을  수 있는지 확인
                				board.reverse(x-1, y-1, 'b'); //뒤집기
                				board.count();
                				board.print();
                				board.turn++;
                				msg = msg.replace(")",",b)");
                				sendMsg(msg);
                 				sendMsg(msg.replace("p1", "p2"));
                			}
                			else{//놓을 수 없음
                				sendMsg(code_p2+"/"+"Wrong location");
                			}            			
                		}
                		else {//pass
                			sendMsg(code_p2+"/"+"PASS");
                			if (board.turn-ch==1) {
                				sendMsg(code_p1+"/"+"ENDGAME");
                				sendMsg(code_p2+"/"+"ENDGAME");
                				board.print();
                				board.result();
                				board.turn++;
                			}
                			ch = board.turn;
                		}
        			}       			
        			else {//짝수턴이 아님
        				sendMsg(code_p2+"/"+"Not your turn.");
        			}           		
                }
                 else if(msg.split("/")[0].endsWith("2")){//p2(백)인경우 
             		if (board.turn%2 == 1) {//홀수턴?
             			x = msg.split("/")[1].charAt(1) - '0';
                 		y = msg.split("/")[1].charAt(3) - '0';
                 		if (board.check('w')) {//pass check
                 			if(!board.select(x-1, y-1, 'w')) {//놓을  수 있는지 확인
                 				board.reverse(x-1, y-1, 'w'); //뒤집기
                 				board.count();
                 				board.print();
                 				board.turn++;
                 				msg = msg.replace(")",",w)");
                 				sendMsg(msg);
                 				sendMsg(msg.replace("p2", "p1"));
                 			}
                 			else{//놓을 수 없음
                 				sendMsg(code_p1+"/"+"Wrong location");
                 			}            			
                 		}
                 		else {//pass
                 			sendMsg(code_p1+"/"+"PASS");
                 			if (board.turn-ch==1) {
                				sendMsg(code_p1+"/"+"ENDGAME");
                				sendMsg(code_p2+"/"+"ENDGAME");
                				board.print();
                				board.result();
                				board.turn++;
                			}
                			ch = board.turn;
                 		}                		
             		}
             		else {//홀수턴이 아님
             			sendMsg(code_p1+"/"+"Not your turn.");
             		}              	
                }
                 else{//잘못된 코드
                     pw = (PrintWriter) hash.get(msg.split("/")[0]);
                     pw.println("연결된 코드가 잘못된 코드입니다.");
                     pw.flush();
                 }        		 
        		 
        	}
        	else {//game 끝
        		//TODO:restart
        	}
        	             
        }
    }
}