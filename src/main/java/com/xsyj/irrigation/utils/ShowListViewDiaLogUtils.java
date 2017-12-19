package com.xsyj.irrigation.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xsyj.irrigation.R;

/**
 * Created by Lenovo on 2016/9/2.
 */
public class ShowListViewDiaLogUtils {



    public static Dialog showDialog(Activity context,View view,String title){
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

    public static void setDialogView(View viewDialog,final Dialog dia,final TextView tv,BaseAdapter cdzadapter,final Handler mHandler,final int messagecode ) {

        ListView lv_dialog = (ListView) viewDialog.findViewById(R.id.lv_dialog);
        Button btn_dialog_cancel = (Button) viewDialog.findViewById(R.id.btn_dialog_cancel);//取消
        Button  btn_dialog_confirm = (Button) viewDialog.findViewById(R.id.btn_dialog_confirm);//确定

        lv_dialog.setAdapter(cdzadapter);


        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia.dismiss();

            }
        });

        btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.obtainMessage(messagecode).sendToTarget();
                dia.dismiss();

            }
        });
    }
}
