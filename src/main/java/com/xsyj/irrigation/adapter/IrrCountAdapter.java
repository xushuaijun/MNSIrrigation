package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xsyj.irrigation.R;

/**
 * Created by Lenovo on 2017/5/23.
 */

public class IrrCountAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;

    public IrrCountAdapter(Context context){
        this.context = context;
        this.inflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.irrcount_item, null);
            holder.irr_count = (TextView) convertView.findViewById(R.id.irr_count);
            holder.irr_time = (TextView) convertView.findViewById(R.id.irr_time);
            holder.irr_wateryield = (TextView) convertView.findViewById(R.id.irr_wateryield);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder{
        TextView irr_count;
        TextView irr_time;
        TextView irr_wateryield;
    }
}
