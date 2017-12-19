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

import com.xsyj.irrigation.PumpWellActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.biz.DeletePumpInfoBiz;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.entity.PumpWell;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/3.
 */
public class PumpWellAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<PumpWell> list = new ArrayList<PumpWell>();
    private Context context;
    private Handler handler;

    private final int DELPUMPWELL_SUCCESS = 0;
    private final int DELPUMPWELL_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    public PumpWellAdapter(List<PumpWell> list, Context context, Handler handler) {
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
            convertView = inflater.inflate(R.layout.pumpwell_item, null);
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
                            AlertDialogUtil.showDialog((PumpWellActivity)context,"","是否删除机井？",new Handler(){
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what){
                                        case AlertDialogUtil.SETOK: // 确定
                                            delPumpWell(String.valueOf(list.get(pos).getId()), handler);
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
            vh.pumnum = (TextView)convertView.findViewById(R.id.tv_sysb_pumnum);
            vh.punmpname = (TextView)convertView.findViewById(R.id.tv_sysb_punmpname);
            vh.qpress = (TextView)convertView.findViewById(R.id.tv_sysb_qpress);
            vh.hpress = (TextView)convertView.findViewById(R.id.tv_sysb_hpress);
            vh.av = (TextView)convertView.findViewById(R.id.tv_sysb_av);
            vh.ae = (TextView)convertView.findViewById(R.id.tv_sysb_ae);
            vh.bv = (TextView)convertView.findViewById(R.id.tv_sysb_bv);
            vh.be = (TextView)convertView.findViewById(R.id.tv_sysb_be);
            vh.cv = (TextView)convertView.findViewById(R.id.tv_sysb_cv);
            vh.ce = (TextView)convertView.findViewById(R.id.tv_sysb_ce);
            vh.userid = (TextView)convertView.findViewById(R.id.tv_sysb_userid);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.sysb_drag.setTag(position);
        final PumpWell source = list.get(position);
        vh.pumnum.setText(source.getPumpnum());
        vh.punmpname.setText(source.getPumpname());
        vh.qpress.setText(String.valueOf(source.getWarnpropumppress()));
        vh.hpress.setText(String.valueOf(source.getWarnaftpumppress()));
        vh.av.setText(String.valueOf(source.getWarnavoltage()));
        vh.ae.setText(String.valueOf(source.getWarnaelectricity()));
        vh.bv.setText(String.valueOf(source.getWarnbvoltage()));
        vh.be.setText(String.valueOf(source.getWarnbelectricity()));
        vh.cv.setText(String.valueOf(source.getWarncvoltage()));
        vh.ce.setText(String.valueOf(source.getWarncelectricity()));
        return convertView;
    }
    public static class ViewHolder {
        TextView pumnum;
        TextView punmpname;
        TextView qpress;
        TextView hpress;
        TextView av;
        TextView ae;
        TextView bv;
        TextView be;
        TextView cv;
        TextView ce;
        TextView userid;
        DragView sysb_drag;
    }

    private void delPumpWell(String pumpnum, final Handler handler ){

        DeletePumpInfoBiz deletePumpInfoBiz = new DeletePumpInfoBiz();
        deletePumpInfoBiz.delPumpWell(context, pumpnum, new NetCallBack<PileDeleteResult>() {
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
