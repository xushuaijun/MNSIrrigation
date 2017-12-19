package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xsyj.irrigation.AddTurnActivity;
import com.xsyj.irrigation.ChangeActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.entity.TapInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/9/1.
 */
public class TapBindedAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    private List<TapInfo> list = new ArrayList<TapInfo>();
    private Context context;

    public TapBindedAdapter(List<TapInfo> list, Context context) {
        this.inflater = LayoutInflater.from(context);
        if(list!=null){
            this.list.clear();
            this.list.addAll(list);
        }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.tap_binded_item, null);
            vh.tv_binded_pile = (TextView) convertView.findViewById(R.id.tv_binded_pile);
            vh.ck_A = (CheckBox) convertView.findViewById(R.id.ck_A);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final TapInfo pile = list.get(position);
        vh.tv_binded_pile.setText(pile.getPileName());
         if("A".equals(pile.getOpenMouth())){
             vh.ck_A.setText("A");
         }else{
             vh.ck_A.setText("B");
         }

         if(pile.getIsSelected()==0){
             vh.ck_A.setChecked(true);
         }else{
             vh.ck_A.setChecked(false);
         }

        vh.ck_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked=((CheckBox)v).isChecked();

                if (!isChecked) {
                    ((ChangeActivity)context).getBinded_list().get(position).setIsSelected(1);
                    ((ChangeActivity)context).getcancelbindedSeled().put(pile.getPileName(), String.valueOf(pile.getId()).concat("_").concat(pile.getOpenMouth()));
                } else {
                    ((ChangeActivity)context).getBinded_list().get(position).setIsSelected(0);
                    ((ChangeActivity)context).getcancelbindedSeled().remove(pile.getPileName());
                }
            }
        });
        return convertView;
    }

    public static class ViewHolder {

        TextView tv_binded_pile;
        CheckBox ck_A;

    }
}
