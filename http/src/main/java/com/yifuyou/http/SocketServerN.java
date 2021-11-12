package com.yifuyou.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.LinkedList;

public class SocketServerN {
    static ServerSocket serverSocket;
    static LinkedList<Socket> sockets;
    static boolean runState;


    static SocketServerN socketServerN;
    public static void Builder(){
        if(socketServerN==null){
            socketServerN=new SocketServerN();
        }
    }

    public static void close(){
        if(socketServerN!=null){
            runState=false;
            socketServerN=null;
        }
    }

     SocketServerN() {
        sockets = new LinkedList<>();
        init();

    }

    void init() {
        runState = true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    serverSocket = new ServerSocket(14333);
                    System.out.println("服务serverSocket 开启-- " + InetAddress.getLocalHost());
                    do {
                        try {
                            Socket socket = serverSocket.accept();
                            addNewSocket(socket);
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        InputStreamReader reader = new InputStreamReader(socket.getInputStream());
//                                        do {
//                                            char[] chars = new char[128];
//                                            StringBuilder stringBuilder=new StringBuilder();
//                                            while (reader.read(chars) != -1) {
//                                                stringBuilder.append(String.valueOf(chars).trim());
//                                            }
//                                            System.out.println(stringBuilder.toString());
//                                        }while (!socket.isClosed());
//
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }).start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } while (runState);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }

    void addNewSocket(Socket socket){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStreamReader reader = new InputStreamReader(socket.getInputStream());
                    do {
                        char[] chars = new char[128];
                        StringBuilder stringBuilder=new StringBuilder();
                        while (reader.read(chars) != -1) {
                            stringBuilder.append(new String(chars));
                        }
                        System.out.println(stringBuilder.toString());
                    }while (!socket.isClosed());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
