package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsyj.irrigation.MonitorHistoryActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.SoilMoistureInfoActivity;
import com.xsyj.irrigation.biz.DeleteSoilMoistureBiz;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.entity.SoilMoistureList;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;
import com.xsyj.irrigation.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/3.
 */
public class SoilMoisMonitorAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<SoilMoistureList> list = new ArrayList<SoilMoistureList>();
    private Context context;
    private Handler handler;

    private final int DESOILMOIS_SUCCESS = 0;
    private final int DESOILMOIS_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    public SoilMoisMonitorAdapter(List<SoilMoistureList> list, Context context, Handler handler) {
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
        vh.tv_monitor_num.setText(soilMois.getStcd());
        vh.tv_monitor_20cmw.setText(soilMois.getTs1()+"");
        vh.tv_monitor_40cmw.setText(soilMois.getTs2()+"");
        vh.tv_monitor_60cmw.setText(soilMois.getTs3()+"");
        vh.tv_monitor_20cmt.setText(soilMois.getWd()+"");
        vh.tv_monitor_40cmt.setText(soilMois.getWd2()+"");
        vh.tv_monitor_60cmt.setText(soilMois.getWd3()+"");
        vh.tv_monitor_pressure.setText(soilMois.getSolarVoltage()+"");
        vh.tv_monitor_landname.setText(soilMois.getLandName());
        vh.tv_monitor_time.setText(soilMois.getTime());

        vh.tv_monitor_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MonitorHistoryActivity.class);
                String stcd = soilMois.getStcd();
                intent.putExtra("STCD", stcd);
                context.startActivity(intent);
            }
        });

//        if(soilMois.getIsBind()==0){
//            vh.tv_bd.setText("未绑定");
//        }else{
//            vh.tv_bd.setText("已绑定");
//        }
//        vh.soilmoisMonitor_drag.setOnDragStateListener(new DragView.DragStateListener() {
//            @Override
//            public void onOpened(DragView dragView) {
//
//            }
//
//            @Override
//            public void onClosed(DragView dragView) {
//
//            }
//
//            @Override
//            public void onForegroundViewClick(DragView dragView, View v) {
//                soilMois.setSelected(!soilMois.isSelected());
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void onBackgroundViewClick(DragView dragView, View v) {
//                switch (v.getId()) {
//                    case R.id.btn_soil_del:
//                        final int pos = (int) dragView.getTag();
//                        AlertDialogUtil.showDialog((SoilMoistureInfoActivity)context,"","是否删除阀门？",new Handler(){
//                            @Override
//                            public void handleMessage(Message msg) {
//                                switch (msg.what){
//                                    case AlertDialogUtil.SETOK: // 确定
//                                        delSoilMois(list.get(pos).getSoilExplorerNum(), handler);
//                                        break;
//                                    case AlertDialogUtil.SETCANCEL: // 取消
//
//                                        break;
//
//                                }
//                            }
//                        });
//                        break;
//                }
//            }
//        });
//        vh.iv_wg.setSelected(soilMois.isSelected());
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
