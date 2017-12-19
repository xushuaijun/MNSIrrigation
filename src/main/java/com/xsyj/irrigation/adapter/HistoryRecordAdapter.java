package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xsyj.irrigation.ChangeActivity;
import com.xsyj.irrigation.FragmentActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.biz.CreateTempTurnTaskBiz;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.HistoryRecord;
import com.xsyj.irrigation.entity.IrrigationTurnInfo;
import com.xsyj.irrigation.fragment.Page2Fragment;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;
import com.xsyj.irrigation.utils.BigDecimalDivide;
import com.xsyj.irrigation.utils.TimeUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lenovo on 2016/8/31.
 */
public class HistoryRecordAdapter extends BaseAdapter{
    private final LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<HistoryRecord> list = new ArrayList<HistoryRecord>();
    private Context context;

    public HistoryRecordAdapter(List<HistoryRecord> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HistoryRecord getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.historyrecord_item,null);

            vh.tv_hispump_name = (TextView) convertView.findViewById(R.id.tv_hispump_name);
            vh.tv_hispump_GPRS = (TextView) convertView.findViewById(R.id.tv_hispump_GPRS);
            vh.tv_hispump_num = (TextView) convertView.findViewById(R.id.tv_hispump_num);
            vh.tv_hispump_level1 = (TextView) convertView.findViewById(R.id.tv_hispump_level1);
            vh.tv_hispump_pressure = (TextView) convertView.findViewById(R.id.tv_hispump_pressure);
            vh.tv_hispump_insFlow = (TextView) convertView.findViewById(R.id.tv_hispump_insFlow);
            vh.tv_hispump_cumFlow = (TextView) convertView.findViewById(R.id.tv_hispump_cumFlow);
            vh.tv_hispump_times = (TextView) convertView.findViewById(R.id.tv_hispump_times);
            vh.tv_hispump_status = (TextView) convertView.findViewById(R.id.tv_hispump_status);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        HistoryRecord historyRecord = list.get(position);
        vh.tv_hispump_name.setText(historyRecord.getPumpName());
        vh.tv_hispump_GPRS.setText(historyRecord.getsTCD());
        vh.tv_hispump_num.setText(historyRecord.getPumpNum());
        vh.tv_hispump_level1.setText(historyRecord.getLevel1()+"");
        vh.tv_hispump_pressure.setText(historyRecord.getPressure()+"");
        vh.tv_hispump_insFlow.setText(historyRecord.getInsFlow()+"");
        vh.tv_hispump_cumFlow.setText(historyRecord.getCumFlow()+"");
        vh.tv_hispump_times.setText(historyRecord.getTimes());
        String status = historyRecord.getPumpStatus();
        if (TextUtils.isEmpty(status)) {
            vh.tv_hispump_status.setText("异常");
            vh.tv_hispump_status.setTextColor(context.getResources().getColor(R.color.color_EDB65F));
        }else{
            if (status.equals("2")){
                vh.tv_hispump_status.setText("开");
                vh.tv_hispump_status.setTextColor(context.getResources().getColor(R.color.title_bg));
            }else if (status.equals("3")){
                vh.tv_hispump_status.setText("关");
                vh.tv_hispump_status.setTextColor(context.getResources().getColor(R.color.font_red));
            }else{
                vh.tv_hispump_status.setText("异常");
                vh.tv_hispump_status.setTextColor(context.getResources().getColor(R.color.color_EDB65F));
            }
        }





        return convertView;
    }
    public static class ViewHolder {
        TextView tv_hispump_name;//轮灌组名称
        TextView tv_hispump_GPRS;//出地桩-阀口
        TextView tv_hispump_num;//当前状态
        TextView tv_hispump_level1;//轮灌方式
        TextView tv_hispump_pressure;//阀口数
        TextView tv_hispump_insFlow;//下次执行时间
        TextView tv_hispump_cumFlow;//执/总次数
        TextView tv_hispump_times;//累计时长
        TextView tv_hispump_status;//灌溉面积
    }


}
