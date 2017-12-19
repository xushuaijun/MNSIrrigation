package com.xsyj.irrigation.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class InputMethodUtil {
	

	
	/**
	 * 隐藏输入法
	 */
	public static void hideKeyboard(Activity context) {
		InputMethodManager manager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (context.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (context.getCurrentFocus() != null)
				
				manager.hideSoftInputFromWindow(
						context.getCurrentFocus().getWindowToken(), 
						InputMethodManager.HIDE_NOT_ALWAYS);

		}
	}


}
