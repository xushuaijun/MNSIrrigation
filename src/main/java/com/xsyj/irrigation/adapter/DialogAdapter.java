package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xsyj.irrigation.AddTurnActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.entity.GetBindTaps;
import com.xsyj.irrigation.entity.LandPile;
import com.xsyj.irrigation.entity.TapInfo;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/9/1.
 */
public class DialogAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    private List<GetBindTaps> list = new ArrayList<GetBindTaps>();
    private Context context;

    public DialogAdapter( List<GetBindTaps> list, Context context) {
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
            convertView = inflater.inflate(R.layout.dialog_item, null);
            vh.tv_unbinded_pile = (TextView) convertView.findViewById(R.id.tv_unbinded_pile);
            vh.ck_unbind_A = (CheckBox) convertView.findViewById(R.id.ck_unbind_A);
            vh.ck_unbind_B = (CheckBox) convertView.findViewById(R.id.ck_unbind_B);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final GetBindTaps pile = list.get(position);

        vh.tv_unbinded_pile.setText(pile.getPileName());
        if(pile.getIsBindA()==0){  // 未被绑定
            vh.ck_unbind_A.setVisibility(View.VISIBLE);

        }else{
                         // 已经被绑定
            vh.ck_unbind_A.setVisibility(View.INVISIBLE);
        }
        if(pile.getIsBindB() ==0){
            vh.ck_unbind_B.setVisibility(View.VISIBLE);

        }else{
            vh.ck_unbind_B.setVisibility(View.INVISIBLE);
        }

       switch (pile.getIsSelected()){
            case 0:
                vh.ck_unbind_A.setChecked(false);
                vh.ck_unbind_B.setChecked(false);
                break;
            case 1:
                vh.ck_unbind_A.setChecked(true);
                vh.ck_unbind_B.setChecked(false);
                break;
            case 2:
                vh.ck_unbind_A.setChecked(false);
                vh.ck_unbind_B.setChecked(true);
                break;
        }

        vh.ck_unbind_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked=((CheckBox)v).isChecked();
                if (isChecked) {
                    ((AddTurnActivity)context).getBindTapsesList().get(position).setIsSelected(1);
                    ((AddTurnActivity)context).getUnbindedSeled().put(pile.getPileName(), String.valueOf(pile.getId()).concat("_A"));
                    ((AddTurnActivity)context).refreshUnBindTapinfo();
                } else {
                    ((AddTurnActivity)context).getBindTapsesList().get(position).setIsSelected(0);
                    ((AddTurnActivity)context).getUnbindedSeled().remove(pile.getPileName());
                    ((AddTurnActivity) context).refreshUnBindTapinfo();
                }
            }
        });

        vh.ck_unbind_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked=((CheckBox)v).isChecked();

                if (isChecked) {
                    ((AddTurnActivity)context).getBindTapsesList().get(position).setIsSelected(2);
                    ((AddTurnActivity)context).getUnbindedSeled().put(pile.getPileName(), String.valueOf(pile.getId()).concat("_B"));
                    ((AddTurnActivity)context).refreshUnBindTapinfo();
                } else {
                    ((AddTurnActivity)context).getBindTapsesList().get(position).setIsSelected(0);
                    ((AddTurnActivity)context).getUnbindedSeled().remove(pile.getPileName());
                    ((AddTurnActivity) context).refreshUnBindTapinfo();
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder {

        TextView tv_unbinded_pile;
        CheckBox ck_unbind_A;
        CheckBox ck_unbind_B;
    }
}
