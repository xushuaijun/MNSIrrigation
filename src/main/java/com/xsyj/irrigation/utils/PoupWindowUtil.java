package com.xsyj.irrigation.utils;


import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.PopupWindow;

public class PoupWindowUtil {
	
	private static PopupWindow pw;

	/**
	 * 
	 * @param contentView 窗体内容
	 * @param background 背景
	 * @param animationStyle  动画style
	 * @param isfocusable 初始是否获得焦点 
	 * @param widht 宽度
	 * @param height 高度
	 * 
	 * @return  PoupWindow
	 */
	public static PopupWindow getInstance(
			View contentView,
			Drawable background,
			int animationStyle,
			boolean isfocusable,
			int width,
			int height
			)
	{
		pw=new PopupWindow(contentView);
		pw.setWidth(width);
		pw.setHeight(height);

		if(background!=null){
			pw.setBackgroundDrawable(background);
		}

		if(animationStyle!=0){
			pw.setAnimationStyle(animationStyle);
		}
		pw.setFocusable(isfocusable);
		
		return pw;
		
	}
	
	public static void showPoupWinow(View parent, int gravity, int x, int y){
		if(pw!=null){
		pw.showAtLocation(parent, gravity, x, y);
		}
	}
	
	public static void dismiss(){
		if(pw!=null){
			pw.dismiss();
		}
	
	}

}
