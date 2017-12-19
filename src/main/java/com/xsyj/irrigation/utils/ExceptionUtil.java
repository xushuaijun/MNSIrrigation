package com.xsyj.irrigation.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;

public class ExceptionUtil {

	public static void handle(final Context context,final Exception e){
		new Thread(){
			
			public void run() {
				try {
				    	e.printStackTrace();
					 StringWriter sw=new StringWriter();
					  PrintWriter pw=new PrintWriter(sw);
					  e.printStackTrace(pw);
					  String str=sw.toString();
					
					    boolean sdCardExist = Environment.getExternalStorageState()   
			                    .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在?
			      	String savedir=null;	
				
				// 当sdcard 正确挂在�?
			  if   (sdCardExist)   
			    {                               
			  savedir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/pingan.txt";//获取跟目?
			     LogUtil.e("savedir",savedir);
				 WriteTxtFile(str,savedir);
				   
				  e.printStackTrace();
			} else{
				ToastUtil.toast(context, "您的sdcard未正确挂载?!");
			}
					
				} catch (Exception e2) {
					LogUtil.e("ExceptionUtil", e2.getMessage()+"");
					e.printStackTrace();
				}
				
				
			}
			
		}.start();
			 
	
		
	
	}
	
	
	
	// 将异常日志写入文�?
	 public static void WriteTxtFile(String strcontent,String strFilePath)
	    {
	      //每次写入时，都换行写
	      String strContent=strcontent+"\n";
	      try {
	           File file = new File(strFilePath);
	           if (!file.exists()) {
	            Log.d("TestFile", "Create the file:" + strFilePath);
	            file.createNewFile();
	           }
	           RandomAccessFile raf = new RandomAccessFile(file, "rw");
	           raf.seek(file.length());
	           raf.write(strContent.getBytes());
	           raf.close();
	      } catch (Exception e) {
	           Log.e("TestFile", "Error on write File.");
	          }
	    }
}
