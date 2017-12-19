package com.xsyj.irrigation.utils;
import android.util.Log;

import com.xsyj.irrigation.application.MyApplication;


public class LogUtil {

	public static void e(String tag,String msg){
		if(!MyApplication.isRelease){
			if(msg==null){
				Log.e(tag,"您当前打印的信息为空！！");
			}else{
				Log.e(tag, msg);
			}
			
		}
	}
	public static void e(String tag,Object msg){
		if(msg==null){
			Log.e(tag,"您当前打印的信息为空！！");
		}else{
			e(tag,String.valueOf(msg));
		}
		
	}
}
