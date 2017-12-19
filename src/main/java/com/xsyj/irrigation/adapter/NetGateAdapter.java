package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xsyj.irrigation.GPRSActivity;
import com.xsyj.irrigation.NetGateActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.biz.DeleteGPRSBiz;
import com.xsyj.irrigation.biz.DeleteNetGateBiz;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.NetGate;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/17.
 */
public class NetGateAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<NetGate> list = new ArrayList<NetGate>();
    private Context context;

    private Handler handler;

    private final int DELNETGATE_SUCCESS = 0;
    private final int DELNETGATE_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    public NetGateAdapter(List<NetGate> list, Context context, Handler handler) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.net_item, null);
            vh.net_drag = (DragView)convertView.findViewById(R.id.net_drag);
            views.add(vh.net_drag);
            vh.net_drag.setOnDragStateListener(new DragView.DragStateListener() {
                @Override
                public void onOpened(DragView dragView) {

                }

                @Override
                public void onClosed(DragView dragView) {

                }

                @Override
                public void onForegroundViewClick(DragView dragView, View v) {
                    int pos = (int) dragView.getTag();
//                    Toast.makeText(context, "click item" + pos, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onBackgroundViewClick(DragView dragView, View v) {
                    switch (v.getId()) {
                        case R.id.btn_net_editer:
                            break;
                        case R.id.btn_net_del:
                            final int pos = (int) dragView.getTag();
                            AlertDialogUtil.showDialog((NetGateActivity)context,"","是否删除GPRS？",new Handler(){
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what){
                                        case AlertDialogUtil.SETOK: // 确定
                                            delNetGate(list.get(pos).getId()+"", handler);
                                            break;
                                        case AlertDialogUtil.SETCANCEL: // 取消

                                            break;

                                    }
                                }
                            });
                            break;
                    }
                }
            });
            vh.net_num = (TextView)convertView.findViewById(R.id.tv_net_num);
            vh.net_name = (TextView)convertView.findViewById(R.id.tv_net_name);
            vh.net_ip = (TextView)convertView.findViewById(R.id.tv_net_ip);
            vh.net_port = (TextView)convertView.findViewById(R.id.tv_net_port);
            vh.net_count = (TextView)convertView.findViewById(R.id.tv_net_count);
            vh.net_status = (TextView)convertView.findViewById(R.id.tv_net_status);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.net_drag.setTag(position);
        final NetGate net = list.get(position);
        vh.net_num.setText(net.getNetGateCode());
        vh.net_name.setText(net.getNetGateName());
        vh.net_ip.setText(net.getNetIp());
        vh.net_port.setText(String.valueOf(net.getNetPort()));
        vh.net_count.setText(String.valueOf(net.getBindTaps()));
         if (net.getFlag() == 0){
             vh.net_status.setText("不在线");
             vh.net_status.setTextColor(context.getResources().getColor(R.color.font_red));
         }else if (net.getFlag() == 1){
             vh.net_status.setText("在线");
             vh.net_status.setTextColor(context.getResources().getColor(R.color.font_lightgreen));
         }else{
             vh.net_status.setText("设备异常");
             vh.net_status.setTextColor(context.getResources().getColor(R.color.font_black));
         }
        return convertView;
    }

    public static class ViewHolder {

        DragView net_drag;
        TextView net_num;
        TextView net_name;
        TextView net_ip;
        TextView net_port;
        TextView net_count;
        TextView net_status;
    }

    private void delNetGate(String id, final Handler handler ){

        DeleteNetGateBiz deleteNetGateBiz = new DeleteNetGateBiz();
        deleteNetGateBiz.delNetGate(context, id, new NetCallBack<PileDeleteResult>() {
            @Override
            public void getNetSuccess(int statu, String url, PileDeleteResult pileDeleteResult) {
                if (pileDeleteResult != null){
                    handler.obtainMessage(DELNETGATE_SUCCESS,pileDeleteResult).sendToTarget();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                handler.obtainMessage(DELNETGATE_FAIL,"删除失败").sendToTarget();
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                handler.obtainMessage(GET_NET_ERROR,"连接服务器失败").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });

    }
}
