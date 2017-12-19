package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xsyj.irrigation.LandActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.TapActivity;
import com.xsyj.irrigation.biz.DeleteLandBiz;
import com.xsyj.irrigation.biz.DeleteTapBiz;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.entity.Tap;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;
import com.xsyj.irrigation.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/3.
 */
public class TapAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<Tap> list = new ArrayList<Tap>();
    private Context context;
    private Handler handler;

    private final int DETAP_SUCCESS = 0;
    private final int DETAP_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    public TapAdapter(List<Tap> list, Context context, Handler handler) {
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
        final Tap tap = list.get(position);
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.tap_item, null);
            vh.tap_drag = (DragView)convertView.findViewById(R.id.tap_drag);
            vh.tap_num = (TextView)convertView.findViewById(R.id.tv_tap_num);
            vh.tap_name = (TextView)convertView.findViewById(R.id.tv_tap_name);
            vh.tap_id = (TextView)convertView.findViewById(R.id.tv_tap_id);
            vh.tap_sbid = (TextView)convertView.findViewById(R.id.tv_tap_sbid);
            vh.tap_area = (TextView)convertView.findViewById(R.id.tv_tap_area);
            vh.tap_gddy = (TextView)convertView.findViewById(R.id.tv_tap_gddy);
            vh.tap_dcdy = (TextView)convertView.findViewById(R.id.tv_tap_dcdy);
            vh.tap_gdyl = (TextView)convertView.findViewById(R.id.tv_tap_gdyl);
            vh.tap_userid = (TextView)convertView.findViewById(R.id.tv_tap_userid);
            vh.tap_ggxh = (TextView)convertView.findViewById(R.id.tv_tap_ggxh);
            vh.tap_scpc = (TextView)convertView.findViewById(R.id.tv_tap_scpc);
            vh.tap_wgbh = (TextView)convertView.findViewById(R.id.tv_tap_wgbh);
            vh.tap_wgname = (TextView)convertView.findViewById(R.id.tv_tap_wgname);
            vh.iv_wg = (ImageView)convertView.findViewById(R.id.iv_wg);
            vh.tv_bd = (TextView)convertView.findViewById(R.id.tv_bd);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        views.add(vh.tap_drag);
        vh.tap_drag.setTag(position);
        vh.tap_num.setText(tap.getTapnum());
        vh.tap_name.setText(tap.getTapname());
        vh.tap_id.setText(String.valueOf(tap.getPileid()));
        vh.tap_sbid.setText(tap.getPumpName());
        vh.tap_area.setText(String.valueOf(tap.getIrrigationarea()));
        vh.tap_gddy.setText(String.valueOf(tap.getProvidevoltage()));
        vh.tap_dcdy.setText(String.valueOf(tap.getCellvoltage()));
        vh.tap_gdyl.setText(String.valueOf(tap.getPipelinepress()));
        vh.tap_userid.setText(tap.getUserName());
        vh.tap_ggxh.setText(tap.getTaptype());
        vh.tap_scpc.setText(tap.getTapbatch());
        vh.tap_wgbh.setText(tap.getNetgatecode());
        vh.tap_wgname.setText(tap.getNetgatename());
        if(tap.getIsDownLoad()==0){
            vh.tv_bd.setText("未绑定");
        }else{
            vh.tv_bd.setText("已绑定");
        }
        vh.tap_drag.setOnDragStateListener(new DragView.DragStateListener() {
            @Override
            public void onOpened(DragView dragView) {

            }

            @Override
            public void onClosed(DragView dragView) {

            }

            @Override
            public void onForegroundViewClick(DragView dragView, View v) {
                tap.setIsSelected(!tap.isSelected());
                notifyDataSetChanged();
            }

            @Override
            public void onBackgroundViewClick(DragView dragView, View v) {
                switch (v.getId()) {
                    case R.id.btn_tap_editer:
                        break;
                    case R.id.btn_tap_del:
                        final int pos = (int) dragView.getTag();
                        AlertDialogUtil.showDialog((TapActivity)context,"","是否删除阀门？",new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what){
                                    case AlertDialogUtil.SETOK: // 确定
                                        delTap(list.get(pos).getId()+"", handler);
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
        vh.iv_wg.setSelected(tap.isSelected());
        LogUtil.e("TRUE", tap.isSelected());
        return convertView;
    }

    public static class ViewHolder {
        TextView tap_num ;
        TextView tap_name;
        TextView tap_id;
        TextView tap_sbid;
        TextView tap_area;
        TextView tap_gddy;
        TextView tap_dcdy;
        TextView tap_gdyl;
        TextView tap_userid;
        TextView tap_ggxh;
        TextView tap_scpc;
        TextView tap_wgbh;
        TextView tap_wgname;
        DragView tap_drag;
        ImageView iv_wg;
        TextView tv_bd;
    }

    private void delTap(String id, final Handler handler ) {

        DeleteTapBiz deleteTapBiz = new DeleteTapBiz();
        deleteTapBiz.delTap(context, id, new NetCallBack<PileDeleteResult>() {
            @Override
            public void getNetSuccess(int statu, String url, PileDeleteResult pileDeleteResult) {
                if (pileDeleteResult != null) {
                    handler.obtainMessage(DETAP_SUCCESS, pileDeleteResult).sendToTarget();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                handler.obtainMessage(DETAP_FAIL, "删除失败").sendToTarget();
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
