package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


public class CommUseAdapter<T extends AdapterGetView> extends BaseAdapter {
	/**
	 * 简单的一个字符串布局
	 */
	public static final int VIEW_TYPE_SIMPLE_STRING=0;
	/**
	 * 复杂的view布局
	 */
	public static final int VIEW_TYPE_COMPLEX_VIEW=1;

	private Context context;
	private List<T> list;
	private LayoutInflater inflater;
	private int viewtype;

	public CommUseAdapter(Context context, List<T> list, int viewtype) {
		super();
		this.context = context;
		this.viewtype=viewtype;
		this.list = list;
		this.inflater=LayoutInflater.from(context);


	}



	@Override
	public int getCount() {
		if (list!=null) {
			return list.size();
		}
		return 0;
	}



	@Override
	public T getItem(int position) {
		if (list!=null) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		T object=getItem(position);
		if(viewtype==VIEW_TYPE_SIMPLE_STRING){
			return object.getStringView(object, position, convertView, inflater,context);
		}else{
			return object.getView(object, position, convertView, inflater,context);
		}

	}
}
