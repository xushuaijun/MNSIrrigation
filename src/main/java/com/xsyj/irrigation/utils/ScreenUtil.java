package com.xsyj.irrigation.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

public class ScreenUtil {
	
	/**
	 * 获得屏幕大小
	 * @param context  Object extends Activity
	 * @return DisplayMetrics dm
	 */
	public static DisplayMetrics getScreenDm(Activity context){
		DisplayMetrics dm=new DisplayMetrics();
		if(context != null){
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		}

		
		return dm;
	}
	
	/**
	 * 返回屏幕宽度 像素
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Activity context){
		return getScreenDm(context).widthPixels;
	}
	
	/**
	 * 返回屏幕高度 像素
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Activity context){
		return getScreenDm(context).heightPixels;
	}
	
	/**
	 * 获得view的座标，存储在Int数组�?
	 * @param v
	 * @return
	 */
	public static int[] getLocationOnScreen(View v){
		
		int[] location=new int[2];
		v.getLocationOnScreen(location);  
		return location;
		
	}
	
	/**
     * 
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity){
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
	
	
}
