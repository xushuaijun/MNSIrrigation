package com.xsyj.irrigation.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2015/6/2.
 */
public class ProgressDialogUtil {



    /**
     * 创建进度框，并且显示
     */
    public static ProgressDialog createProgressDialog(Activity context,String msg) {


        ProgressDialog pd = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setCanceledOnTouchOutside(false);
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                }
            });
            pd.setMessage(msg);



        pd.show();

        return pd;

    }

}
