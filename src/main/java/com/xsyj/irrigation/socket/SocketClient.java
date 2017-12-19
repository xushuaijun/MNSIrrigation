package com.xsyj.irrigation.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/1/13.
 */
public class SocketClient {
    private static final int PORT = 54321;
    // 线程池
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        new SocketClient();
    }

    public SocketClient() {
        try {
            Socket socket = new Socket("10.81.36.177", PORT);
            exec.execute(new Sender(socket));
            // 设置读取套接字服务器返回流的编码格式
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "GB2312"));
            String msg;
            while((msg = br.readLine()) != null) {
                System.out.println(msg);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 客户端线程获取控制台输入消息
    static class Sender implements Runnable {
        private Socket socket;

        public Sender(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        System.in));
                // 设置向套接字服务器写入流的编码格式
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), "GB2312"), true);
                String msg;

                while(true) {
                    msg = br.readLine();
                    pw.println(msg);

                    if(msg.trim().equals("exit")) {
                        pw.close();
                        br.close();
                        exec.shutdownNow();
                        break;
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
