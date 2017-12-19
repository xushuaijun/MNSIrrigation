package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.entity.IrrigationTurnInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/9/6.
 */
public class LGZadapter extends BaseAdapter {

    private final LayoutInflater inflater;
    List<IrrigationTurnInfo> list = new ArrayList<IrrigationTurnInfo>();
    private Context context;

    public LGZadapter(Context context, List<IrrigationTurnInfo> list) {
        this.list = list;
        this.inflater= LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.lgz_btn_item,null);
            vh.tv_lgz_btn_name = (TextView) convertView.findViewById(R.id.tv_lgz_btn_name);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final IrrigationTurnInfo it = list.get(position);
        vh.tv_lgz_btn_name.setText(it.getTurnname());
        return convertView;
    }

    public static class ViewHolder {

        TextView tv_lgz_btn_name;
    }
}
