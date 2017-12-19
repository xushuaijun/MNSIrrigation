package com.xsyj.irrigation.interfaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


public abstract class AdapterGetView {
	public abstract View getStringView(Object object, int position,View convertView, LayoutInflater inflater,Context context) ;
	public abstract View getView(Object object, int position,View convertView, LayoutInflater inflater,Context context);
	
	public abstract int getItemViewType(int position);

	public  abstract int getViewTypeCount();
	
}
