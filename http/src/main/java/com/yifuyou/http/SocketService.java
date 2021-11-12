package com.yifuyou.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class SocketService {
    static SocketService socketService;
    static LinkedList<Socket> sockets;
    public static void initService() {
        sockets=new LinkedList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketService = new SocketService();
                    System.out.println("log 服务开启 =-- "+ InetAddress.getLocalHost());
                    InputStreamReader isr=null;
                    StringBuilder stringBuilder=null;
                    do {
                        stringBuilder=new StringBuilder();
                        Socket accept = socketService.socket.accept();
                        sockets.add(accept);
                        isr = new InputStreamReader(accept.getInputStream());

                        char [] chars=new char[128];
                        while (isr.read(chars) != -1) {
                            stringBuilder.append(new String(chars).trim());
                        }
                        System.out.println(stringBuilder);
                        isr.close();
                        accept.close();
                    } while (!"exit".equals(stringBuilder.toString()));

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (socketService != null) {
                        socketService.close();
                    }
                }
            }
        }).start();
    }

    private ServerSocket socket;

    private SocketService() throws IOException {
        socket = new ServerSocket(14333);
    }
    private void close(){
        try {
            socket.close();
            System.out.println("服务 关闭");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
