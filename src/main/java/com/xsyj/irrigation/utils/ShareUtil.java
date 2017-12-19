package com.xsyj.irrigation.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class ShareUtil {
	
	
	/** 
     * ������ 
     *  
     * @param context 
     *            ������ 
     * @param activityTitle 
     *            Activity������ 
    * @param msgTitle 
     *            ��Ϣ���� 
     * @param msgText 
    *            ��Ϣ���� 
     * @param imgPath 
     *            ͼƬ·����������ͼƬ��null 
     */  
    public static void  shareMsg(String activityTitle, String msgTitle, String msgText,  
            String imgPath,Context context) {  
        Intent intent = new Intent(Intent.ACTION_SEND);  
        if (imgPath == null || imgPath.equals("")) {  
            intent.setType("text/plain"); // ���ı�  
        } else {  
            File f = new File(imgPath);  
            if (f != null && f.exists() && f.isFile()) {  
                intent.setType("image/jpg");  
              Uri u = Uri.fromFile(f);  
                intent.putExtra(Intent.EXTRA_STREAM, u);  
            }  
        }  
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);  
        intent.putExtra(Intent.EXTRA_TEXT, msgText);  
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        context.startActivity(Intent.createChooser(intent, activityTitle));  
    }  

}
