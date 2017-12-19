package com.xsyj.irrigation.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/1/13.
 */
public class SocketServer {

    // 服务器端口
    private static final int SERVERPORT = 54321;
    // 客户端连接
    private static List<Socket> mClientList = new ArrayList<Socket>();
    // 线程池
    private ExecutorService mExecutorService;
    // serverSocket对象
    private ServerSocket mServerSocket;

    public static void main(String[] args) {
        new SocketServer();
    }

    public SocketServer() {
        try {
            // 设置服务器端口
            mServerSocket = new ServerSocket(SERVERPORT);
            // 创建一个线程池
            mExecutorService = Executors.newCachedThreadPool();
            System.out.println("start...");
            // 用来临时保存客户端连接的Socket对象
            Socket client = null;
            while(true) {
                // 接受客户度连接并添加到List中
                client = mServerSocket.accept();
                mClientList.add(client);
                mExecutorService.execute(new ThreadServer(client));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 每个客户端单独开启一个线程
    static class ThreadServer implements Runnable {
        private Socket mSocket;
        private BufferedReader mBufferedReader;
        private PrintWriter mPrintWriter;
        private String mStrMSG;

        public ThreadServer(Socket socket) throws IOException {
            this.mSocket = socket;
            // 设置读取套接字客户端发送流的编码格式
            mBufferedReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "GB2312"));
            mStrMSG = "user:" + this.mSocket.getInetAddress() + " come total:"
                    + mClientList.size();
            sendMessage();
        }

        private void sendMessage() throws IOException {
            System.out.println(mStrMSG);
            for(Socket client : mClientList) {
                // 设置向套接字客户端写入流的编码格式
                mPrintWriter = new PrintWriter(new OutputStreamWriter(
                        client.getOutputStream(), "GB2312"), true);
                mPrintWriter.println(mStrMSG);
            }
        }

        public void run() {
            try {
                while((mStrMSG = mBufferedReader.readLine()) != null) {
                    if(mStrMSG.trim().equals("exit")) {
                        // 当一个客户端退出时
                        mClientList.remove(mSocket);
                        mBufferedReader.close();
                        mPrintWriter.close();
                        mStrMSG = "user:" + this.mSocket.getInetAddress()
                                + " exit tatal:" + mClientList.size();
                        mSocket.close();
                        sendMessage();
                        break;
                    }
                    else {
                        mStrMSG = mSocket.getInetAddress() + ":" + mStrMSG;
                        sendMessage();
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

    }
}
