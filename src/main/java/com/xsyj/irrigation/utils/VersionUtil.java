package com.xsyj.irrigation.utils;

import android.content.Context;
import android.content.pm.PackageManager;

public class VersionUtil {

    public static int getVisionCode(Context context){

        try {
            String pkName = context.getPackageName();
     /*   String versionName = this.getPackageManager().getPackageInfo(
                pkName, 0).versionName;*/
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;

            return  versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  1;
    }
}
