package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.SoilMoistureInfoActivity;
import com.xsyj.irrigation.TapActivity;
import com.xsyj.irrigation.biz.DeleteSoilMoistureBiz;
import com.xsyj.irrigation.biz.DeleteTapBiz;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.entity.SoilMoistureList;
import com.xsyj.irrigation.entity.Tap;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;
import com.xsyj.irrigation.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/3.
 */
public class SoilMoisAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<SoilMoistureList> list = new ArrayList<SoilMoistureList>();
    private Context context;
    private Handler handler;

    private final int DESOILMOIS_SUCCESS = 0;
    private final int DESOILMOIS_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    public SoilMoisAdapter(List<SoilMoistureList> list, Context context, Handler handler) {
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
            convertView = inflater.inflate(R.layout.soilmois_item, null);
            vh.soilmois_drag = (DragView)convertView.findViewById(R.id.soilmois_drag);
            vh.tv_soil_num = (TextView)convertView.findViewById(R.id.tv_soil_num);
            vh.tv_soil_name = (TextView)convertView.findViewById(R.id.tv_soil_name);
            vh.tv_soil_local = (TextView)convertView.findViewById(R.id.tv_soil_local);
            vh.tv_soil_landname = (TextView)convertView.findViewById(R.id.tv_soil_landname);
            vh.tv_soil_netnum = (TextView)convertView.findViewById(R.id.tv_soil_netnum);
            vh.iv_wg = (ImageView)convertView.findViewById(R.id.iv_wg);
            vh.tv_bd = (TextView)convertView.findViewById(R.id.tv_bd);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        views.add(vh.soilmois_drag);
        vh.soilmois_drag.setTag(position);
        vh.tv_soil_num.setText(soilMois.getSoilExplorerNum());
        vh.tv_soil_name.setText(soilMois.getSoilExplorerName());
        vh.tv_soil_local.setText(soilMois.getInstallAddress());
        vh.tv_soil_landname.setText(soilMois.getLandName());
        vh.tv_soil_netnum.setText(soilMois.getNetCode());
        if(soilMois.getIsBind()==0){
            vh.tv_bd.setText("未绑定");
        }else{
            vh.tv_bd.setText("已绑定");
        }
        vh.soilmois_drag.setOnDragStateListener(new DragView.DragStateListener() {
            @Override
            public void onOpened(DragView dragView) {

            }

            @Override
            public void onClosed(DragView dragView) {

            }

            @Override
            public void onForegroundViewClick(DragView dragView, View v) {
                soilMois.setSelected(!soilMois.isSelected());
                notifyDataSetChanged();
            }

            @Override
            public void onBackgroundViewClick(DragView dragView, View v) {
                switch (v.getId()) {
                    case R.id.btn_soil_del:
                        final int pos = (int) dragView.getTag();
                        AlertDialogUtil.showDialog((SoilMoistureInfoActivity)context,"","是否删除阀门？",new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what){
                                    case AlertDialogUtil.SETOK: // 确定
                                        delSoilMois(list.get(pos).getSoilExplorerNum(), handler);
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
        vh.iv_wg.setSelected(soilMois.isSelected());
        LogUtil.e("TRUE", soilMois.isSelected());
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_soil_num ;
        TextView tv_soil_name;
        TextView tv_soil_local;
        TextView tv_soil_landname;
        TextView tv_soil_netnum;
        DragView soilmois_drag;
        ImageView iv_wg;
        TextView tv_bd;
    }

    private void delSoilMois(String explorerNum, final Handler handler ) {

        DeleteSoilMoistureBiz deleteSoilMoistureBiz = new DeleteSoilMoistureBiz();
        deleteSoilMoistureBiz.delSoilMois(context, explorerNum, new NetCallBack<PileDeleteResult>() {
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
