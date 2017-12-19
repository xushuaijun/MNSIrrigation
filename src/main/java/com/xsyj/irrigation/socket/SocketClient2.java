package com.xsyj.irrigation.socket;

import com.xsyj.irrigation.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;

public class SocketClient2 {  
    // 线程池  
  
    private volatile static SocketClient2 socketClient;
	private InputStream in;
	private OutputStream out;
	private Socket socket;
	private String ip;
	private int port;



	public SocketClient2(String ip,int port) {
    	
    	this.ip=ip;
    	this.port=port;
    	
        
    }  
    
    
    
    /**
     * 获取单实例
     * @return
     */
    public static SocketClient2 getInstance(String ip,int port) {  
   	    if (socketClient == null) {  
   	        synchronized (SocketClient2.class) {  
   	        if (socketClient == null) {  
   	             socketClient = new SocketClient2(ip,port);  
   	          }  
   	       }  
   	    }  
   	    return socketClient;  
        } 
    
    /**
     * 连接服务器
     * @return
     */
    public void connectService(){

		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {


				if(socket!=null && (socket.isClosed()==false && socket.isConnected()==true)){
					LogUtil.e("sockettest1" , "socket连接成功");
					if(onSocketConnected!=null){
						onSocketConnected.onSuccess();
					}
				}else{

					try{
						socket = new Socket(ip, port);
					}  catch(UnknownHostException e) {
						e.printStackTrace();

					}catch(IOException e){
						e.printStackTrace();

					}
					LogUtil.e("sockettest1" , "socket连接成功");
					if(onSocketConnected!=null){
						onSocketConnected.onSuccess();
					}
					try {

						in = socket.getInputStream();
						byte[] temp=new byte[1024*8];
						byte[] result = new byte[0];
						int len=-1;
						while((len=in.read(temp))!=-1){
							synchronized(this){
								LogUtil.e("sockettest1", "读取长度：" + len);
								byte[] readBytes = new byte[len];
								System.arraycopy(temp, 0, readBytes, 0, len);
								result = BytesArrayUtil.mergeArray(result,readBytes);
								if(len<1024*8){
									String res=BytesAnd16Code.bytesToHexString(result);
									LogUtil.e("sockettest1" , "socket 返回结果："+res);
									if(onSocketConnected!=null){
										onSocketConnected.onResultCallBack(res);
									}
									if(result.length==15){ // 当结果符合某条件是关闭socket
										socket.close();
									}
									result = new byte[0];
								}
							}


						}

					}
					catch(IOException e){
						System.out.println(e.getMessage());
					}
				}



			}
		});


    	 

    }
    
    
    /**
     * 发送信息
     */
    
    public synchronized void sendMessage(byte[] message){
		LogUtil.e("sockettest1", message.length);

    		try {
				out=socket.getOutputStream();
				out.write(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

    }
    
    /**
     * 关闭流，关闭socket
     */
    public void closeAll(){
    	try {
			out.close();
			in.close();
	    	socket.close();
	    	socket=null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }


	private OnSocketConnected onSocketConnected;
	public void setOnSocketConnected(OnSocketConnected onSocketConnected){
		this.onSocketConnected=onSocketConnected;
	}
	interface  OnSocketConnected{
		void onSuccess();
		void onResultCallBack(String result);
	}
  
   
}  