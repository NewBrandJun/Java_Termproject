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
            //���� ��ȣ�� �ӽ÷� 1525
            ServerSocket server = new ServerSocket(1525);
            hash = new HashMap<String, Object>();
            gamehash = new HashMap<String, Object>();
            //hash�� Ű���ҷ��ͼ� �� �渶�� ������� ���ϱ�
            
            while(true)
            {
                System.out.println("=======================");
                System.out.println("���� ������   "+hash.size()+"��...");
                System.out.println("hash:" + hash.keySet());
                System.out.println("gamehash:" + gamehash.keySet());
                System.out.println("room: " + room);
                //hash�� ���� ���ϱ�                
                System.out.println("������ ��ٸ�����...");
                Socket sck = server.accept();
                //Ŭ���̾�Ʈ�� ���ε��ö����� chatThr������ �Ѱ� ���� ������ ���������� ����
                ChatThread chatThr = new ChatThread(sck, hash, gamehash, room);
                chatThr.start();            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


