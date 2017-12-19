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
import com.xsyj.irrigation.PumpWellActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.biz.DeleteGPRSBiz;
import com.xsyj.irrigation.biz.DeletePumpInfoBiz;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.GetGPRSResult;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.entity.PumpWell;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/3.
 */
public class GPRSAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<GetGPRSResult> list = new ArrayList<GetGPRSResult>();
    private Context context;
    private Handler handler;

    private final int DELPUMPWELL_SUCCESS = 0;
    private final int DELPUMPWELL_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    public GPRSAdapter(List<GetGPRSResult> list, Context context, Handler handler) {
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
            convertView = inflater.inflate(R.layout.gprs_item, null);
            vh.sysb_drag = (DragView)convertView.findViewById(R.id.sysb_drag);
            views.add(vh.sysb_drag);
            vh.sysb_drag.setOnDragStateListener(new DragView.DragStateListener() {
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
                    switch (v.getId()){
                        case R.id.btn_sysb_editer:
                            break;
                        case R.id.btn_sysb_del:
                            final int pos = (int) dragView.getTag();
                            AlertDialogUtil.showDialog((GPRSActivity)context,"","是否删除GPRS？",new Handler(){
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what){
                                        case AlertDialogUtil.SETOK: // 确定
                                            delGprs(list.get(pos).getId()+"", handler);
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
            vh.tv_gprs_num = (TextView)convertView.findViewById(R.id.tv_gprs_num);
            vh.tv_gprs_name = (TextView)convertView.findViewById(R.id.tv_gprs_name);
            vh.tv_gprs_ip = (TextView)convertView.findViewById(R.id.tv_gprs_ip);
            vh.tv_gprs_port = (TextView)convertView.findViewById(R.id.tv_gprs_port);
            vh.tv_gprs_username = (TextView)convertView.findViewById(R.id.tv_gprs_username);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.sysb_drag.setTag(position);
        final GetGPRSResult source = list.get(position);
        vh.tv_gprs_num.setText(source.getGprs());
        vh.tv_gprs_name.setText(source.getGprsName());
        vh.tv_gprs_ip.setText(source.getIp());
        vh.tv_gprs_port.setText(String.valueOf(source.getPort()));
        vh.tv_gprs_username.setText(String.valueOf(source.getUserName()));
        return convertView;
    }
    public static class ViewHolder {
        TextView tv_gprs_num;
        TextView tv_gprs_name;
        TextView tv_gprs_ip;
        TextView tv_gprs_port;
        TextView tv_gprs_username;
        DragView sysb_drag;
    }

    private void delGprs(String id, final Handler handler ){

        DeleteGPRSBiz deleteGPRSBiz = new DeleteGPRSBiz();
        deleteGPRSBiz.delGprs(context, id, new NetCallBack<PileDeleteResult>() {
            @Override
            public void getNetSuccess(int statu, String url, PileDeleteResult pileDeleteResult) {
                if (pileDeleteResult != null){
                    handler.obtainMessage(DELPUMPWELL_SUCCESS,pileDeleteResult).sendToTarget();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                handler.obtainMessage(DELPUMPWELL_FAIL,"删除失败").sendToTarget();
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
