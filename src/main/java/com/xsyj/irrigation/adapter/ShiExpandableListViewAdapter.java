package com.xsyj.irrigation.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.customview.CustomExpandableListView;
import com.xsyj.irrigation.entity.Shi;
import com.xsyj.irrigation.entity.Xian;
import com.xsyj.irrigation.entity.Xiang;

import java.util.List;

/**
 * Created by Lenovo on 2016/9/12.
 */
public class ShiExpandableListViewAdapter extends BaseExpandableListAdapter{

    private List<Shi> groupData;
    private Context context;
    //用来装载某个item是否被选中
    SparseBooleanArray selected;
    int old = -1;
    int parentPosition = -1;

    public ShiExpandableListViewAdapter(List<Shi> groupData,
                                        Context context) {
        super();
        this.groupData = groupData;
//        this.childData = childData;
        this.context = context;
        selected = new SparseBooleanArray();
    }


    @Override
    public int getGroupCount() {
        return groupData == null ? 0 : groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupData.get(groupPosition).getXianList().get(childPosition);
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


        holder.tv_group.setText(groupData.get(groupPosition).getShi_Name());
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
//        holder.tv_child.setText(groupData.get(groupPosition).get(childPosition).getTown_Name());
        return getGenericExpandableListView(groupData.get(groupPosition));
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
    }

    /**
     *  返回子ExpandableListView 的对象  此时传入的是该大学下所有班级的集合。
     * @param
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ExpandableListView getGenericExpandableListView(Shi shi){
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomExpandableListView view = new CustomExpandableListView(context);

        // 加载班级的适配器
        final  DrawExpandableListViewAdapter adapter = new DrawExpandableListViewAdapter(shi.getXianList(),context);

        view.setAdapter(adapter);

        view.setPadding(20,0,0,0);
        view.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                adapter.setSelectedItem(groupPosition);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                adapter.setChildSelectedItem(childPosition);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }
}

