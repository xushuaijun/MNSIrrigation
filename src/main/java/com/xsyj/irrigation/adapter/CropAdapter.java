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

import com.xsyj.irrigation.CropActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.WaterSourceActivity;
import com.xsyj.irrigation.biz.DeleteCropBiz;
import com.xsyj.irrigation.biz.DeleteWaterSourceBiz;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.Crop;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/3.
 */
public class CropAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<Crop> list = new ArrayList<Crop>();
    private Context context;
    private Handler handler;

    private final int DELCROP_SUCCESS = 0;
    private final int DELCROP_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    public CropAdapter(List<Crop> list, Context context, Handler handler) {
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
            convertView = inflater.inflate(R.layout.crop_item, null);
            vh.crop_drag = (DragView)convertView.findViewById(R.id.crop_drag);
            views.add(vh.crop_drag);
            vh.crop_drag.setOnDragStateListener(new DragView.DragStateListener() {
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
                    switch(v.getId()){
                        case R.id.btn_crop_editer:
                            break;
                        case R.id.btn_crop_del:
                            final int pos = (int) dragView.getTag();
                            AlertDialogUtil.showDialog((CropActivity)context,"","是否删除作物？",new Handler(){
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what){
                                        case AlertDialogUtil.SETOK: // 确定
                                            delCrop(String.valueOf(list.get(pos).getTheser()), handler);
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
            vh.num = (TextView)convertView.findViewById(R.id.tv_crop_num);
            vh.name = (TextView)convertView.findViewById(R.id.tv_crop_name);
            vh.gsde = (TextView)convertView.findViewById(R.id.tv_crop_gsde);
            vh.wd = (TextView)convertView.findViewById(R.id.tv_crop_wd);
            vh.hbgd = (TextView)convertView.findViewById(R.id.tv_crop_hbgd);
            vh.sh = (TextView)convertView.findViewById(R.id.tv_crop_sh);
            vh.zztime = (TextView)convertView.findViewById(R.id.tv_crop_zztime);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.crop_drag.setTag(position);
        final Crop crop = list.get(position);
        vh.num.setText(String.valueOf(crop.getCropid()));
        vh.name.setText(crop.getCropname());
        vh.gsde.setText(String.valueOf(crop.getCropde()));
        vh.wd.setText(crop.getLatitude());
        vh.hbgd.setText(String.valueOf(crop.getHeight()));
        vh.sh.setText(crop.getCropShenhe());
        vh.zztime.setText(crop.getIndate());
        return convertView;
    }

    public static class ViewHolder {
        TextView num;
        TextView name;
        TextView gsde;
        TextView wd;
        TextView hbgd;
        TextView sh;
        TextView zztime;
        DragView crop_drag;
    }

    private void delCrop(String cropid, final Handler handler ){

        DeleteCropBiz deleteCropBiz = new DeleteCropBiz();
        deleteCropBiz.delCrop(context, cropid, new NetCallBack<PileDeleteResult>() {
            @Override
            public void getNetSuccess(int statu, String url, PileDeleteResult pileDeleteResult) {
                if (pileDeleteResult != null){
                    handler.obtainMessage(DELCROP_SUCCESS,pileDeleteResult).sendToTarget();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                handler.obtainMessage(DELCROP_FAIL,"删除失败").sendToTarget();
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
