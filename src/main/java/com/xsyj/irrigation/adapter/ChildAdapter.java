package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsyj.irrigation.NetWorkActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.entity.IrrigationTask;
import com.xsyj.irrigation.entity.NetWork;
import com.xsyj.irrigation.entity.TapInfo;
import com.xsyj.irrigation.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/12/23.
 */
public class ChildAdapter extends BaseExpandableListAdapter {

    private Context mContext;// 上下文
    private List<NetWork> mChilds;// 数据源
    private String netgetcode;

    public ChildAdapter(Context context, List<NetWork> childs, String netgetcode) {
        this.mContext = context;
        this.mChilds = childs;
        this.netgetcode = netgetcode;
    }



    @Override
    public int getGroupCount() {
        return mChilds != null ? mChilds.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChilds.get(groupPosition).getChildren() != null ? mChilds
                .get(groupPosition).getChildren().size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (mChilds != null && mChilds.size() > 0)
            return mChilds.get(groupPosition);
        return null;
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        if (mChilds.get(groupPosition).getChildren() != null
                && mChilds.get(groupPosition).getChildren().size() > 0)
            return mChilds.get(groupPosition).getChildren()
                    .get(childPosition).getNetWorkName();
        return null;
    }

    public String getPileName(int groupPosition, int childPosition) {
        if (mChilds.get(groupPosition).getChildren() != null
                && mChilds.get(groupPosition).getChildren().size() > 0)
            return mChilds.get(groupPosition).getChildren()
                    .get(childPosition).getPileName();
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.child_group_item, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        if(isExpanded){
            holder.child_arrow.setImageResource(R.drawable.arrow_down);
        }else{
            holder.child_arrow.setImageResource(R.drawable.arrow_right);
        }
        holder.update(mChilds.get(groupPosition));
        return convertView;
    }

    /**
     * @author Apathy、恒
     *
     *         Holder优化
     * */
    class GroupHolder {

        private TextView childtitle;
        private ImageView child_arrow;
        private CheckBox cb_child;
        public GroupHolder(View v) {
            childtitle = (TextView) v.findViewById(R.id.childtitle);
            child_arrow = (ImageView) v.findViewById(R.id.child_arrow);
        }

        public void update(NetWork model) {
            childtitle.setText(model.getNetWorkName());
        }
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.child_child_item, null);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
              holder = (ChildHolder) convertView.getTag();
        }
        final String openmouth = getChild(groupPosition, childPosition);
        String pileName = getPileName(groupPosition, childPosition);
        holder.update(pileName);
        holder.cb_child.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                Map<String, IrrigationTask> map = ((NetWorkActivity) mContext).getTasks();
                if(isChecked) {
                    if (map != null) {

                        IrrigationTask task = new IrrigationTask();
                        List<TapInfo> openmouths = new ArrayList<TapInfo>();
                        task.setOpenmouths(openmouths);
                        task.setNetGateCode(netgetcode);
                        task.setTapnum("");
                        task.setStime("");
                        task.setIrrigationtime(0);
                        task.setIrrigationwater(0);
                        task.setIrrigationtype(13);
                        task.setState(-1);

                        if (map.get(netgetcode) == null) {
                            TapInfo tapInfo = new TapInfo();
                            tapInfo.setOpenMouth(openmouth);
                            openmouths.add(tapInfo);
                            map.put(netgetcode, task);
                            LogUtil.e("NETGETCODE",netgetcode+","+task.getOpenmouths().get(0).getOpenMouth());
                        } else {
                            boolean isExist = false;
                            for (int i = 0; i < map.get(netgetcode).getOpenmouths().size(); i++) {


                                if (map.get(netgetcode).getOpenmouths().get(i).getOpenMouth().equals(openmouth)) {
                                    isExist = true;
                                    break;
                                }
                            }
                            if (!isExist) {
                                TapInfo tapInfo = new TapInfo();
                                tapInfo.setOpenMouth(openmouth);
                                map.get(netgetcode).getOpenmouths().add(tapInfo);
                            }

                            LogUtil.e("map",map.get(netgetcode).getOpenmouths().size());

                        }
                    }
                }
            }
        });
        return convertView;
    }

    /**
     * @author Apathy、恒
     *
     *         Holder优化
     * */
    class ChildHolder {

        private TextView childtitle1;
        private CheckBox cb_child;

        public ChildHolder(View v) {
            childtitle1 = (TextView) v.findViewById(R.id.childtitle1);
            cb_child = (CheckBox) v.findViewById(R.id.cb_child);
        }

        public void update(String str ) {
            childtitle1.setText(str);
        }
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
