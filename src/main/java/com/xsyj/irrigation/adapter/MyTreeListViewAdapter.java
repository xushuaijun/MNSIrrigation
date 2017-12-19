package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.entity.Node;

import java.util.List;

public class MyTreeListViewAdapter<T> extends TreeListViewAdapter<T> {

	public MyTreeListViewAdapter(ListView mTree, Context context,
			List<T> datas, int defaultExpandLevel,boolean isHide)
			throws IllegalArgumentException, IllegalAccessException {
		super(mTree, context, datas, defaultExpandLevel,isHide);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public View getConvertView(Node node, int position, View convertView,
							   ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.group_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.iv_group);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.tv_group);
//			viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.id_treeNode_check);
			
			convertView.setTag(viewHolder);

		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (node.getIcon() == -2){
			viewHolder.icon.setVisibility(View.INVISIBLE);
			viewHolder.label.setTextColor(mContext.getResources().getColor(R.color.font_gray));
		}else if (node.getIcon() == -1)
		{
			viewHolder.icon.setVisibility(View.INVISIBLE);
			viewHolder.label.setTextColor(mContext.getResources().getColor(R.color.font_gray));
		}else if (node.getIcon() == -4)
		{
			viewHolder.icon.setVisibility(View.INVISIBLE);
			viewHolder.label.setTextColor(mContext.getResources().getColor(R.color.font_lightblue));
		}else if (node.getIcon() == -3)
		{
			viewHolder.icon.setVisibility(View.INVISIBLE);
			viewHolder.label.setTextColor(mContext.getResources().getColor(R.color.font_gray));
		}else
		{
			viewHolder.icon.setVisibility(View.VISIBLE);
			viewHolder.icon.setImageResource(node.getIcon());
			if (node.getIcon() == R.drawable.menu_arrow){
				viewHolder.label.setTextColor(mContext.getResources().getColor(R.color.font_lightblue));
			}else{
				viewHolder.label.setTextColor(mContext.getResources().getColor(R.color.font_gray));
			}

		}

//		if(node.isHideChecked()){
//			viewHolder.checkBox.setVisibility(View.GONE);
//		}else{
//			viewHolder.checkBox.setVisibility(View.VISIBLE);
//			setCheckBoxBg(viewHolder.checkBox,node.isChecked());
//		}
		viewHolder.label.setText(node.getAreaName());
		
		
		return convertView;
	}
	private final class ViewHolder
	{
		ImageView icon;
		TextView label;
//		CheckBox checkBox;
	}
	
	/**
	 * checkbox是否显示
	 * @param cb
	 * @param isChecked
	 */
//	private void setCheckBoxBg(CheckBox cb,boolean isChecked){
//		if(isChecked){
//			cb.setBackgroundResource(R.drawable.check_box_bg_check);
//		}else{
//			cb.setBackgroundResource(R.drawable.check_box_bg);
//		}
//	}
}
