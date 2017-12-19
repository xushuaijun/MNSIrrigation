package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.xsyj.irrigation.R;
import com.xsyj.irrigation.entity.Xian;
import com.xsyj.irrigation.entity.Xiang;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.List;

/**
 * Created by Lenovo on 2016/9/12.
 */
public class DrawExpandableListViewAdapter extends BaseExpandableListAdapter{

    private List<Xian> groupData;
//    private List<List<Xiang>> childData;
    private Context context;
    //用来装载某个item是否被选中
    SparseBooleanArray selected;
    //用来装载某个childItem是否被选中
    SparseBooleanArray childSelected;
    int old = -1;
    int childOld = -1;
    int parentPosition = -1;
    int childPosition = -1;

    public DrawExpandableListViewAdapter(List<Xian> groupData,
                                         Context context) {  // List<List<Xiang>> childData,
        super();
        this.groupData = groupData;
//        this.childData = childData;
        this.context = context;
        selected = new SparseBooleanArray();
        childSelected = new SparseBooleanArray();
    }


    @Override
    public int getGroupCount() {
        return groupData == null ? 0 : groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupData != null && groupData.get(groupPosition) != null)
            return groupData.get(groupPosition).getXiangList().size();
        else
            return 0;
//        return childData.get(groupPosition) == null ? 0 : childData.get(
//                groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (groupData != null)
           return groupData.get(groupPosition);
        else
            return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupData != null && groupData.get(groupPosition) != null)
            return groupData.get(groupPosition).getXiangList().get(childPosition);
        else
            return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        MyGroupHolder holder;
        if(convertView==null){
            holder=new MyGroupHolder();
            convertView=View.inflate(context, R.layout.draw_group_item, null);
            holder.iv_group = (ImageView) convertView.findViewById(R.id.iv_group);
            holder.tv_group = (TextView) convertView.findViewById(R.id.tv_group);
            holder.iv_lable = (ImageView) convertView.findViewById(R.id.iv_lable);
            convertView.setTag(holder);
         }else{
            holder=(MyGroupHolder) convertView.getTag();
        }
        //重点代码
        if (selected.get(groupPosition)&&this.parentPosition==groupPosition) {
            holder.iv_group.setImageResource(R.drawable.icon_sgs02);
            holder.tv_group.setTextColor(context.getResources().getColor(R.color.font_lightblue));
        }else{
            holder.iv_group.setImageResource(R.drawable.icon_sgs01);
            holder.tv_group.setTextColor(context.getResources().getColor(R.color.font_gray));
        }

        if(isExpanded){
            holder.iv_lable.setImageResource(R.drawable.arrow_down);
        }else{
            holder.iv_lable.setImageResource(R.drawable.arrow_right);
        }


        holder.tv_group.setText(groupData.get(groupPosition).getCoun_Name());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MyChildHolder holder;
        if(convertView==null){
            holder=new MyChildHolder();
            convertView = View.inflate(context,R.layout.draw_child_item,null);
            holder.iv_child = (ImageView) convertView.findViewById(R.id.iv_child);
            holder.tv_child = (TextView) convertView.findViewById(R.id.tv_child);
            convertView.setTag(holder);
        }else{
            holder=(MyChildHolder) convertView.getTag();
        }
        if (childSelected.get(childPosition)&&this.parentPosition==groupPosition) {
            holder.tv_child.setTextColor(context.getResources().getColor(R.color.font_lightblue));
        }else{
            holder.tv_child.setTextColor(context.getResources().getColor(R.color.font_gray));
        }
        holder.tv_child.setText(groupData.get(groupPosition).getXiangList().get(childPosition).getTown_Name());
//        holder.tv_child.setText(childData.get(groupPosition).get(childPosition).getTown_Name());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }




    class MyGroupHolder {
        TextView tv_group;
        ImageView iv_group;
        ImageView iv_lable;
    }

    class MyChildHolder {
        TextView tv_child;
        ImageView iv_child;
    }

    public void setSelectedItem(int groupPosition) {
        this.parentPosition = groupPosition;
        if (old != -1) {
            this.selected.put(old, false);
        }
        this.selected.put(groupPosition, true);
        old = groupPosition;
        this.childSelected.put(childOld,false);
    }
    public void setChildSelectedItem(int childPosition) {
        this.childPosition = childPosition;
        if (childOld != -1) {
            this.childSelected.put(childOld,false);
        }
        this.childSelected.put(childPosition,true);
        childOld = childPosition;
    }
}
