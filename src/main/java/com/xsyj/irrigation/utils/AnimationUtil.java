package com.xsyj.irrigation.utils;




import android.support.v4.app.FragmentTransaction;

import com.xsyj.irrigation.R;


public class AnimationUtil {
	
	/**
	 * 
	 * @param t
	 * @param currindex
	 * @param lastindex
	 */
	public static void setFragmentAnim(FragmentTransaction t,int currindex,int lastindex) {
		if(currindex>lastindex){
			t.setCustomAnimations(R.anim.slide_alpha_in_right, R.anim.slide_alpha_out_left);
		}else{
			t.setCustomAnimations(R.anim.slide_alpha_in_left, R.anim.slide_alpha_out_right);
		}
	}


}
