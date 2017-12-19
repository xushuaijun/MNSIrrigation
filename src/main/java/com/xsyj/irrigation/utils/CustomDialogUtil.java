package com.xsyj.irrigation.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lenovo on 2016/11/28.
 */
public class CustomDialogUtil {

    public static Dialog showCustomDialog(Activity context,View view,String title){
        Dialog builder = new Dialog(context);
        builder.setTitle(title);
        builder.show();

        LayoutInflater inflater = LayoutInflater.from(context);
        int width = ScreenUtil.getScreenWidth((Activity)context);
        //设置对话框的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width * 90 / 100,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.setContentView(view, layoutParams);

        return builder;
    }
}
