package com.xsyj.irrigation.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.utils.LogUtil;

public class SocketTestActivity extends AppCompatActivity {
    private EditText et;
    private Button btn1;
    private Button btn2;
    private TextView tv;
    private static final String SERVERIP = "192.168.3.6";
    private static final int SERVERPORT = 5555;
    private Thread mThread = null;
    private Socket mSocket = null;
   /* private BufferedReader mBufferedReader = null;
    private PrintWriter mPrintWriter = null;*/
    private String mStrMSG = "";
    private InputStream in; //
    private OutputStream out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);

        et = (EditText) findViewById(R.id.et1);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        tv = (TextView) findViewById(R.id.tv1);
        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 连接服务器
                            mSocket = new Socket(SERVERIP, SERVERPORT);
                            // 设置读取套接字服务器返回流的编码格式
                   /* mBufferedReader = new BufferedReader(new InputStreamReader(
                            mSocket.getInputStream(), "GB2312"));*/
                            in=mSocket.getInputStream();
                            // 设置向套接字服务器写入流的编码格式
                    /*mPrintWriter = new PrintWriter(new OutputStreamWriter(
                            mSocket.getOutputStream(), "GB2312"), true);*/
                            out=mSocket.getOutputStream();

                                handler.obtainMessage(1).sendToTarget();

                        } catch(Exception e) {
                                Log.e("连接服务器", e.toString());
                            }
                        }
                    });




                }

        });
        /**
         * 发送信息
         */
        btn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                       /* try {
                          *//* String str = et.getText().toString() + "\n";
                             mPrintWriter.print(str);
                             mPrintWriter.flush();*//*
                            String str = et.getText().toString().trim();
                            Log.e("发送信息", str);
                            out.write(BytesAnd16Code.hexStringToBytes(str));
                            out.flush();
                        } catch (Exception e) {
                            Log.e("发送信息", e.toString());
                        }*/

                        final SocketClient2 client = SocketClient2.getInstance("192.168.3.6", 5555);

                        client.setOnSocketConnected(new SocketClient2.OnSocketConnected() {
                            @Override
                            public void onSuccess() {
                                client.sendMessage(BytesAnd16Code.hexStringToBytes("010145689265856595360016000000150000000088FBFA0B8f610100160000330a2BC4FCFC"));
                            }

                            @Override
                            public void onResultCallBack(String result) {

                            }
                        });
                        client.connectService();


                    }
                });

            }
        });

    }


    // 线程:监听服务器发来的消息
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
           // while(true) {
                try {
                    /*if((mStrMSG = mBufferedReader.readLine()) != null) {
                        mStrMSG += "\n";
                        handler.sendMessage(handler.obtainMessage());
                    }*/

                    byte[] temp=new byte[1024*8];
                    byte[] result = new byte[0];
                    int len=-1;
                    while((len=in.read(temp))!=-1){
                        System.out.println("长度"+len);
                        byte[] readBytes = new byte[len];
                        System.arraycopy(temp, 0, readBytes, 0, len);
                        result = BytesArrayUtil.mergeArray(result,readBytes);
                        if(len<1024*8){
                            mStrMSG=BytesAnd16Code.bytesToHexString(result);
                            handler.sendMessage(handler.obtainMessage());
                            result = new byte[0];
                        }

                    }
                    LogUtil.e("接收信息", "结束");
                }
                catch(Exception e) {
                    LogUtil.e("接收信息",e.toString());
                }
            }
        //}
    };
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(msg.what==1){
                LogUtil.e("socket信息",mSocket.isClosed()+","+mSocket.isConnected());
                /**
                 * 启动接收字节监听
                 */
                mThread = new Thread(mRunnable);
                mThread.start();
            }else{
                // 刷新
                try {
                    tv.append(mStrMSG);
                }
                catch(Exception e) {
                    // TODO: handle exception
                }
            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            SocketClient2.getInstance("192.168.3.6", 5555).closeAll();
            if(mSocket!=null){
                mSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
