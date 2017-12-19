package com.xsyj.irrigation.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;
import com.xsyj.irrigation.utils.ToastUtil;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/10/25.
 */
public class ZQ extends AdapterGetView implements Serializable{

    private int circleCount;      //周期数
    private boolean isSelected;   // 条件筛选时 是否被选中

    public ZQ() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ZQ(int circleCount, boolean isSelected) {
        this.circleCount = circleCount;
        this.isSelected = isSelected;
    }

    public int getCircleCount() {
        return circleCount;
    }

    public void setCircleCount(int circleCount) {
        this.circleCount = circleCount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return String.valueOf(circleCount);
    }

    @Override
    public View getStringView(Object object, int position, View convertView, LayoutInflater inflater, final Context context) {
        ViewHolder vh;
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.griditem, null);
            vh.btn_ZQ = (TextView) convertView.findViewById(R.id.btn_item);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final ZQ zq = (ZQ) object;
        vh.btn_ZQ.setText("周期"+String.valueOf(zq.getCircleCount()));
        vh.btn_ZQ.setSelected(zq.isSelected);

        return convertView;
    }

    @Override
    public View getView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        return null;
    }

    @Override
    public View getView1(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    public static class ViewHolder {
        TextView btn_ZQ;
    }
}
