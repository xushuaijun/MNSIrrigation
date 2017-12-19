package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xsyj.irrigation.MonitorHistoryActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.biz.DeleteSoilMoistureBiz;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.entity.SoilMoistureList;
import com.xsyj.irrigation.interfaces.NetCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/3.
 */
public class MonitorHistoryAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<SoilMoistureList> list = new ArrayList<SoilMoistureList>();
    private Context context;
    private Handler handler;

    private final int DESOILMOIS_SUCCESS = 0;
    private final int DESOILMOIS_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    public MonitorHistoryAdapter(List<SoilMoistureList> list, Context context, Handler handler) {
        this.list = list;
        this.context = context;
        this.inflater= LayoutInflater.from(context);
        this.handler = handler;
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
        return position ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        final SoilMoistureList soilMois = list.get(position);
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.soilmoismonitor_item, null);
            vh.soilmoisMonitor_drag = (DragView)convertView.findViewById(R.id.soilmoisMonitor_drag);
            vh.tv_monitor_num = (TextView)convertView.findViewById(R.id.tv_monitor_num);
            vh.tv_monitor_20cmw = (TextView)convertView.findViewById(R.id.tv_monitor_20cmw);
            vh.tv_monitor_40cmw = (TextView)convertView.findViewById(R.id.tv_monitor_40cmw);
            vh.tv_monitor_60cmw = (TextView)convertView.findViewById(R.id.tv_monitor_60cmw);
            vh.tv_monitor_20cmt = (TextView)convertView.findViewById(R.id.tv_monitor_20cmt);
            vh.tv_monitor_40cmt = (TextView)convertView.findViewById(R.id.tv_monitor_40cmt);
            vh.tv_monitor_60cmt = (TextView)convertView.findViewById(R.id.tv_monitor_60cmt);
            vh.tv_monitor_pressure = (TextView)convertView.findViewById(R.id.tv_monitor_pressure);
            vh.tv_monitor_landname = (TextView)convertView.findViewById(R.id.tv_monitor_landname);
            vh.tv_monitor_time = (TextView)convertView.findViewById(R.id.tv_monitor_time);
            vh.tv_monitor_history = (TextView)convertView.findViewById(R.id.tv_monitor_history);
//            vh.tv_bd = (TextView)convertView.findViewById(R.id.tv_bd);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        views.add(vh.soilmoisMonitor_drag);
        vh.soilmoisMonitor_drag.setTag(position);
        vh.tv_monitor_history.setVisibility(View.GONE);
        vh.tv_monitor_num.setText(soilMois.getSoilExplorerNum());
        vh.tv_monitor_20cmw.setText(soilMois.getTs1()+"");
        vh.tv_monitor_40cmw.setText(soilMois.getTs2()+"");
        vh.tv_monitor_60cmw.setText(soilMois.getTs3()+"");
        vh.tv_monitor_20cmt.setText(soilMois.getWd()+"");
        vh.tv_monitor_40cmt.setText(soilMois.getWd2()+"");
        vh.tv_monitor_60cmt.setText(soilMois.getWd3()+"");
        vh.tv_monitor_pressure.setText(soilMois.getSolarVoltage()+"");
        vh.tv_monitor_landname.setText(soilMois.getLandName());
        vh.tv_monitor_time.setText(soilMois.getTime());
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_monitor_num ;
        TextView tv_monitor_20cmw;
        TextView tv_monitor_40cmw;
        TextView tv_monitor_60cmw;
        TextView tv_monitor_20cmt;
        TextView tv_monitor_40cmt ;
        TextView tv_monitor_60cmt;
        TextView tv_monitor_pressure;
        TextView tv_monitor_landname;
        TextView tv_monitor_time;
        TextView tv_monitor_history;
        DragView soilmoisMonitor_drag;
//        ImageView iv_wg;
//        TextView tv_bd;
    }

    private void delSoilMois(String soilExplorerNum, final Handler handler ) {

        DeleteSoilMoistureBiz deleteSoilMoistureBiz = new DeleteSoilMoistureBiz();
        deleteSoilMoistureBiz.delSoilMois(context, soilExplorerNum, new NetCallBack<PileDeleteResult>() {
            @Override
            public void getNetSuccess(int statu, String url, PileDeleteResult pileDeleteResult) {
                if (pileDeleteResult != null) {
                    handler.obtainMessage(DESOILMOIS_SUCCESS, pileDeleteResult).sendToTarget();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                handler.obtainMessage(DESOILMOIS_FAIL, "删除失败").sendToTarget();
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                handler.obtainMessage(GET_NET_ERROR, "连接服务器失败").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }

}
