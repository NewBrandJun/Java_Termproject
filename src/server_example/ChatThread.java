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
    //���� Ŭ���̾�Ʈ�� Printwriter��ü�� code�� �����ϰ� �������ִ� �ؽ���
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
            //123_p1�� 123_p2���� ä���� ����
            pw.println("Please input your room number code : ");
            pw.flush();
            code = br.readLine();
            code_p1 = code + "_p1";
            code_p2 = code + "_p2";
            synchronized (room) {
            	while (Collections.frequency(room, code) >=2) {//�̹� ���̲���
                	pw.println("This room is already full. Please input another room number code : ");
                    pw.flush();
                    code = br.readLine();	
                }            	
            }
            
            if (Collections.frequency(room, code) > 0) {//����ִ� ��
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
            else if (Collections.frequency(room, code) == 0) {//���ο� ��
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
            //����ȭ �� �ؽ��ʿ� ����          
            System.out.println("===="+code+"�԰� ���������� ����");
        } catch (IOException e) {
            e.printStackTrace();
        }            
    }
    public void run() {
        String line = null;
        try {
            while((line = br.readLine()) != null)
            {//Ŭ���̾�Ʈ�κ��� quit�� ������ ����
                if(line.split("/")[0].equals("quit")){
                    System.out.println(code+"���� �ý����� �����մϴ�...");
                    break;                    
                }
                else if (Collections.frequency(room, code) == 1) {//room�� ȥ���ִ°��, wait for player ����
                	pw = (PrintWriter) hash.get(line.split("/")[0]);
                	pw.println("wait for player...");
                	pw.flush();
                }              
                else{//�ƴ� ��� ��� �о�� �����͸� Ŭ���̾�Ʈ�鿡�� ����
                	System.out.println(line);
                	if (line.split("/")[1].startsWith("(") && line.split("/")[1].endsWith(")")) {
                		game(line);
                	} else {
                		sendMsg(line);
                	}                 
                }
            }
        } catch (IOException e) {
            System.out.println(code+"���� �ý����� ���������� �����մϴ�...");
            
        }finally
        {
            synchronized (hash) {
                hash.remove(code);
            }
            System.out.println("=======================");
            System.out.println("���� ������   "+hash.size()+"��...");
            System.out.println("=======================");
            
            try {
                sck.close();
            } catch (IOException e) {
                System.out.println("socket�� ���������� ������� �ʾҽ��ϴ�.");
            }
        }
    }
     
    //1:1 ���� (p1�̸� p2���Ը�, p2�� p1���Ը� 1:1�� ����)
    public void sendMsg(String msg)
    {
        synchronized (hash) 
        {
            pw = null;
            //code�� 1�� ������ 2���� �޽���
             if(msg.split("/")[0].endsWith("1"))
             {//split�Լ��� replace�Լ��� �̿��� �����ϰ� ���Ź��� Ŭ���̾�Ʈ printwriter��ü�� ����
                pw = (PrintWriter) hash.get(msg.split("/")[0].replace("p1", "p2"));
                pw.println(msg.split("/")[1]);
                pw.flush();
            }else if(msg.split("/")[0].endsWith("2"))
            {
                pw = (PrintWriter) hash.get(msg.split("/")[0].replace("p2", "p1"));
                pw.println(msg.split("/")[1]);
                pw.flush();
            }else//�߸��� �ڵ�
            {
                pw = (PrintWriter) hash.get(msg.split("/")[0]);
                pw.println("����� �ڵ尡 �߸��� �ڵ��Դϴ�.");
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
        	if (board.notfinish()) {//game ������
        		if(msg.split("/")[0].endsWith("1")){//p1(��)�ΰ��
        			if (board.turn%2 == 0) {//¦����?
        				x = msg.split("/")[1].charAt(1) - '0';
                		y = msg.split("/")[1].charAt(3) - '0';
                		if (board.check('b')) {//pass check
                			if(!board.select(x-1, y-1, 'b')) {//����  �� �ִ��� Ȯ��
                				board.reverse(x-1, y-1, 'b'); //������
                				board.count();
                				board.print();
                				board.turn++;
                				msg = msg.replace(")",",b)");
                				sendMsg(msg);
                 				sendMsg(msg.replace("p1", "p2"));
                			}
                			else{//���� �� ����
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
        			else {//¦������ �ƴ�
        				sendMsg(code_p2+"/"+"Not your turn.");
        			}           		
                }
                 else if(msg.split("/")[0].endsWith("2")){//p2(��)�ΰ�� 
             		if (board.turn%2 == 1) {//Ȧ����?
             			x = msg.split("/")[1].charAt(1) - '0';
                 		y = msg.split("/")[1].charAt(3) - '0';
                 		if (board.check('w')) {//pass check
                 			if(!board.select(x-1, y-1, 'w')) {//����  �� �ִ��� Ȯ��
                 				board.reverse(x-1, y-1, 'w'); //������
                 				board.count();
                 				board.print();
                 				board.turn++;
                 				msg = msg.replace(")",",w)");
                 				sendMsg(msg);
                 				sendMsg(msg.replace("p2", "p1"));
                 			}
                 			else{//���� �� ����
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
             		else {//Ȧ������ �ƴ�
             			sendMsg(code_p1+"/"+"Not your turn.");
             		}              	
                }
                 else{//�߸��� �ڵ�
                     pw = (PrintWriter) hash.get(msg.split("/")[0]);
                     pw.println("����� �ڵ尡 �߸��� �ڵ��Դϴ�.");
                     pw.flush();
                 }        		 
        		 
        	}
        	else {//game ��
        		//TODO:restart
        	}
        	             
        }
    }
}