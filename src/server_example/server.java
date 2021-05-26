package server_example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
public class server {
    int i;
    static HashMap<String, Object> hash;
    static HashMap<String, Object> gamehash;
    static ArrayList<String> room = new ArrayList<String>();
    public static void main(String[] args) {
        
        try {
            //소켓 번호는 임시로 1525
            ServerSocket server = new ServerSocket(1525);
            hash = new HashMap<String, Object>();
            gamehash = new HashMap<String, Object>();
            //hash맵 키값불러와서 각 방마다 몇명인지 구하기
            
            while(true)
            {
                System.out.println("=======================");
                System.out.println("현재 서버에   "+hash.size()+"명...");
                System.out.println("hash:" + hash.keySet());
                System.out.println("gamehash:" + gamehash.keySet());
                System.out.println("room: " + room);
                //hash맵 개수 구하기                
                System.out.println("접속을 기다리는중...");
                Socket sck = server.accept();
                //클라이언트가 새로들어올때마다 chatThr스레드 한개 실행 각각은 독립적으로 실행
                ChatThread chatThr = new ChatThread(sck, hash, gamehash, room);
                chatThr.start();            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


