package com.xsyj.irrigation.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;

import java.io.Serializable;

public class LandPile extends AdapterGetView implements Serializable {


    private static final long serialVersionUID = 1L;

    private int id;

    private String pilenum;


    private String userid;


    private int landid;


    private String pilename;

    private String landName;
    private String userName;

    private boolean isSelected; // 筛选时是否被选中


    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getLandName() {
        return landName;
    }


    public void setLandName(String landName) {
        this.landName = landName;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getPilenum() {
        return pilenum;
    }


    public void setPilenum(String pilenum) {
        this.pilenum = pilenum;
    }


    public String getUserid() {
        return userid;
    }


    public void setUserid(String userid) {
        this.userid = userid;
    }


    public int getLandid() {
        return landid;
    }


    public void setLandid(int landid) {
        this.landid = landid;
    }


    public String getPilename() {
        return pilename;
    }


    public void setPilename(String pilename) {
        this.pilename = pilename;
    }

    @Override
    public View getStringView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        ViewHolder vh;
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.griditem, null);
            vh.btn_cdz = (TextView) convertView.findViewById(R.id.btn_item);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final LandPile it = (LandPile) object;
        vh.btn_cdz.setText(it.getPilename());
        vh.btn_cdz.setSelected(it.isSelected);
        return convertView;
    }

    @Override
    public View getView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {

        return convertView;
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
        TextView btn_cdz;
    }
}