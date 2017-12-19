package com.xsyj.irrigation.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/22.
 */
public class TapState extends AdapterGetView implements Serializable {
    private int stateId;
    private String stateName;
    private boolean isSelected;

    public TapState() {
    }

    public TapState(int stateId, String stateName, boolean isSelected) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.isSelected = isSelected;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public View getStringView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        ViewHolder vh;
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.griditem, null);
            vh.btn_lgz = (TextView) convertView.findViewById(R.id.btn_item);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final TapState it = (TapState) object;
        vh.btn_lgz.setText(it.getStateName());
        vh.btn_lgz.setSelected(it.isSelected());
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

    static class ViewHolder {
        TextView btn_lgz;
    }
}
