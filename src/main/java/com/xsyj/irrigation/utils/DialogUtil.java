package com.xsyj.irrigation.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xsyj.irrigation.R;

public class DialogUtil {
	/**
	 * 创建自定义Dialog
	 * @param context 上下文环境
	 * @param resource dialog中的布局文件R.layout.xxx
	 * @param style dialog样式
	 * @param v  可为null
	 * @return dialog  返回一个类型为 Dialog 的对话框
	 */
	public static Dialog createDialog(Context context,int resource,int style,View v){
		Dialog dialog=null;
		if(style==-1){
			dialog =new Dialog(context);
		}else{
			dialog = new Dialog(context, style);
		}

		View dialogview = LayoutInflater.from(context).inflate(resource, null);
		//设置它的ContentView
		dialog.setContentView(dialogview);
		dialog.show();
		return dialog;
	}


	public static void dilogDismiss(Dialog dialog){
		if(dialog!=null){
			dialog.dismiss();
		}
	}


	/**
	 * 得到自定义的progressDialog
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		loadingDialog.show();
		return loadingDialog;

	}

}
