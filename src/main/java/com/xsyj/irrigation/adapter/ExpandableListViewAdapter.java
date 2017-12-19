package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.entity.CirclePlan;
import com.xsyj.irrigation.utils.LogUtil;

import java.util.List;

/**
 * Created by Lenovo on 2016/10/21.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<String> groupData;
    private List<List<CirclePlan>> childData;

    private Context context;

    //用来装载某个item是否被选中
    SparseBooleanArray selected;


    int old = -1;
    int parentPosition = -1;

    public ExpandableListViewAdapter(List<String> groupData, List<List<CirclePlan>> childData, Context context) {
        super();
        this.groupData = groupData;
        this.childData = childData;
        this.context = context;
        selected = new SparseBooleanArray();
    }

    @Override
    public int getGroupCount() {
        return groupData == null ? 0 : groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition) == null ? 0 : childData.get(
                groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
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
        LogUtil.e("getGroupView","GetGroupview");
        MyGroupHolder holder;
        if(convertView==null){
            holder=new MyGroupHolder();
            convertView=View.inflate(context, R.layout.group_item, null);
            holder.iv_group = (ImageView) convertView.findViewById(R.id.iv_group);
            holder.tv_group = (TextView) convertView.findViewById(R.id.tv_group);
            holder.iv_lable = (ImageView) convertView.findViewById(R.id.iv_lable);
            convertView.setTag(holder);
        }else{
            holder=(MyGroupHolder) convertView.getTag();
        }

        if(isExpanded){
            holder.iv_lable.setImageResource(R.drawable.arrow_down);
        }else{
            holder.iv_lable.setImageResource(R.drawable.arrow_right);
        }


        holder.tv_group.setText(groupData.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MyChildHolder holder;
        if(convertView==null){
            holder=new MyChildHolder();
            convertView = View.inflate(context,R.layout.child_item,null);
            holder.lgz_Name= (TextView) convertView.findViewById(R.id.zq_lgzN);
            holder.csk = (TextView) convertView.findViewById(R.id.zq_csk);
            holder.startTime = (TextView) convertView.findViewById(R.id.zq_startTime);
            holder.endTime = (TextView) convertView.findViewById(R.id.zq_endTime);
            convertView.setTag(holder);
        }else{
            holder=(MyChildHolder) convertView.getTag();
        }
        holder.lgz_Name.setText(childData.get(groupPosition).get(childPosition).getChoiceName());
        holder.csk.setText(childData.get(groupPosition).get(childPosition).getPileName());
        holder.startTime.setText(childData.get(groupPosition).get(childPosition).getStartTime());
        holder.endTime.setText(childData.get(groupPosition).get(childPosition).getEndTime());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class MyGroupHolder {
        TextView tv_group;
        ImageView iv_group;
        ImageView iv_lable;
    }

    class MyChildHolder {
        TextView lgz_Name;
        TextView csk;
        TextView startTime;
        TextView endTime;
    }

    public void setSelectedItem(int groupPosition) {
        this.parentPosition = groupPosition;
        if (old != -1) {
            this.selected.put(old, false);
        }
        this.selected.put(groupPosition, true);
        old = groupPosition;
    }

}
