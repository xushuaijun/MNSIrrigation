package com.xsyj.irrigation.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2015/12/17.
 */
public class ListViewUtil {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
//获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
// pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//listView.getDividerHeight()获取子项间分隔符占用的高度
//params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }



    public void isTopOrBottom(final ListView listView,TopOrBottom topOrBottom){
        this.topOrBottom=topOrBottom;
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = listView.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                        // "ListView", "##### 滚动到顶部 #####";
                        isTop();
                    }
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    View lastVisibleItemView = listView.getChildAt(listView.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == listView.getHeight()) {
                        //  "ListView", "##### 滚动到底部 ######"
                        isBottom();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //do nothing
            }

        });
    }

    private void isTop() {
         if(topOrBottom!=null){
             topOrBottom.onTop(true);
         }

    }

    private void isBottom() {

        if(topOrBottom!=null){
            topOrBottom.onBottom(true);
        }
    }

    /**
     * 判断listview 是否到顶部或者底部
     */
    private TopOrBottom  topOrBottom;
    public interface TopOrBottom{
        void onTop(boolean isTop);
        void onBottom(boolean isBottom);
    }




}
