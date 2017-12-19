package com.xsyj.irrigation.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/12/27.
 */
public class Menu extends AdapterGetView implements Serializable {

    private int menuId;
    private String mName;
    private boolean isSelected;

    public Menu() {
    }

    public Menu(int menuId, String mName, boolean isSelected) {
        this.menuId = menuId;
        this.mName = mName;
        this.isSelected = isSelected;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
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
            vh.btn_menu = (TextView) convertView.findViewById(R.id.btn_item);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final Menu it = (Menu) object;
        vh.btn_menu.setText(it.getmName());
        vh.btn_menu.setSelected(it.isSelected());
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
        TextView btn_menu;
    }

}
