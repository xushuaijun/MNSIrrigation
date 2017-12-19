package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.entity.NetWork;
import com.xsyj.irrigation.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/12/23.
 */
public class ParentAdapter extends BaseExpandableListAdapter {


    private Context context;              //上下文
    private List<NetWork> mParents;       //数据源
    private OnChildViewClickListener mChildViewClickListener;


    public ParentAdapter(Context context, List<NetWork> parents) {
        this.context = context;
        this.mParents = parents;
    }



    @Override
    public int getGroupCount() {
        return mParents != null ? mParents.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mParents.get(groupPosition).getChildren() != null ? mParents
                .get(groupPosition).getChildren().size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    @Override
    public NetWork getChild(int groupPosition, int childPosition) {
        return mParents.get(groupPosition).getChildren().get(childPosition);
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
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.parent_group_item, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.update(mParents.get(groupPosition));
        if(isExpanded){
            holder.arrow.setImageResource(R.drawable.arrow_down);
        }else{
            holder.arrow.setImageResource(R.drawable.arrow_right);
        }
        return convertView;
    }

    /**
     *         Holder优化
     * */
    class GroupHolder {

        private TextView ptitle;
        private ImageView arrow;

        public GroupHolder(View v) {
            ptitle = (TextView) v.findViewById(R.id.ptitle);
            arrow = (ImageView) v.findViewById(R.id.parent_arrow);
        }

        public void update(NetWork model) {
            ptitle.setText(model.getNetWorkName());
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ExpandableListView eListView = getExpandableListView();
        List<NetWork> childs = new ArrayList<NetWork>();

        final NetWork child = getChild(groupPosition, childPosition);
        childs.add(child);
        String netgetcode = mParents.get(groupPosition).getNetWorkName();
        final ChildAdapter childAdapter = new ChildAdapter(this.context,
                childs,netgetcode);
        eListView.setAdapter(childAdapter);

        /**
         *
         *         点击子ExpandableListView子项时，调用回调接口
         * */
        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (mChildViewClickListener != null) {
                    mChildViewClickListener.onClickPosition(groupPosition, childPosition, childPosition);
                }
                return false;
            }
        });

        /**
         *
         *         子ExpandableListView展开时，因为group只有一项，所以子ExpandableListView的总高度=
         *         （子ExpandableListView的child数量 + 1 ）* 每一项的高度
         * */
        eListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                LayoutParams lp = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (child
                        .getChildren().size() + 1)
                        * (int) context.getResources().getDimension(
                        R.dimen.dp_48));
                eListView.setLayoutParams(lp);
            }
        });

        /**
         *
         *         子ExpandableListView关闭时，此时只剩下group这一项，
         *         所以子ExpandableListView的总高度即为一项的高度
         * */
        eListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                LayoutParams lp = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (int) context
                        .getResources().getDimension(
                                R.dimen.dp_48));
                eListView.setLayoutParams(lp);
            }
        });


        return eListView;
    }

    /**
     * @author Apathy、恒
     *
     *         动态创建子ExpandableListView
     * */
    public ExpandableListView getExpandableListView() {
        ExpandableListView mExpandableListView = new ExpandableListView(
                context);
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) context
                .getResources().getDimension(
                        R.dimen.dp_48));
        mExpandableListView.setLayoutParams(lp);
        mExpandableListView.setDividerHeight(0);// 取消group项的分割线
        mExpandableListView.setChildDivider(null);// 取消child项的分割线
        mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
        return mExpandableListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    /**
     * @author Apathy、恒
     *
     *         设置点击子ExpandableListView子项的监听
     * */
    public void setOnChildTreeViewClickListener(
            OnChildViewClickListener treeViewClickListener) {
        this.mChildViewClickListener = treeViewClickListener;
    }

    /**
     * 点击子ExpandableListView子项的回调接口
     */
    public interface OnChildViewClickListener {

        void onClickPosition(int parentPosition, int groupPosition,
                             int childPosition);
    }

}
