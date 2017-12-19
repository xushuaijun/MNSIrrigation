package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xsyj.irrigation.ChangeActivity;
import com.xsyj.irrigation.FragmentActivity;
import com.xsyj.irrigation.MainActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.biz.CreateTempTurnTaskBiz;
import com.xsyj.irrigation.customview.DragView;
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
public class PullToRefreshListViewAdapter extends BaseAdapter{
    private final LayoutInflater inflater;
    List<DragView> views = new ArrayList<DragView>();
    private List<IrrigationTurnInfo> list = new ArrayList<IrrigationTurnInfo>();
    private Context context;

    public PullToRefreshListViewAdapter(List<IrrigationTurnInfo> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IrrigationTurnInfo getItem(int position) {
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
            convertView = inflater.inflate(R.layout.turn_item,null);
            vh.drag_view = (DragView)convertView.findViewById(R.id.drag_view);
            views.add(vh.drag_view);

            vh.drag_view.setOnDragStateListener(new DragView.DragStateListener() {
                @Override
                public void onOpened(DragView dragView) {//打开状态

                }

                @Override
                public void onClosed(DragView dragView) {//关闭状态

                }

                @Override
                public void onForegroundViewClick(DragView dragView, View v) {//点击前面布局
                    int pos = (int) dragView.getTag();
//                    Toast.makeText(context, "click item" + pos, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onBackgroundViewClick(DragView dragView, View v) {//点击后面的布局
                    final int pos = (int) dragView.getTag();
                    switch (v.getId()){
                        case R.id.btn_editer:
                            Intent intent = new Intent(context, ChangeActivity.class);
                            intent.putExtra("turnName",list.get(pos).getTurnname());
                            intent.putExtra("turnId",String.valueOf(list.get(pos).getId()));
                            intent.putExtra("startType",String.valueOf(list.get(pos).getStartType()));
                            Page2Fragment page2=(Page2Fragment)((FragmentActivity)context).
                                    getSupportFragmentManager().findFragmentByTag("page2");
                            page2.startActivityForResult(intent, 2);
                            break;
                        case R.id.btn_del:

                           /*  list.remove(pos);
                            notifyDataSetChanged();*/

                            AlertDialogUtil.showDialog((FragmentActivity)context,"","是否要删除轮灌组?",new Handler(){
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what){
                                        case AlertDialogUtil.SETOK: // 确定
                                              if(onDelTurnLisitener!=null){

                                                  onDelTurnLisitener.delTurn(list.get(pos).getName(),pos);
                                              }
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

            vh.btn_editer = (Button) convertView.findViewById(R.id.btn_editer);
            vh.btn_del = (Button) convertView.findViewById(R.id.btn_del);

            vh.turn_name = (TextView) convertView.findViewById(R.id.tv_turn_name);
            vh.cdz_fk = (TextView) convertView.findViewById(R.id.tv_cdz_fk);
            vh.state_now = (TextView) convertView.findViewById(R.id.tv_state_now);
            vh.lg_fs = (TextView) convertView.findViewById(R.id.tv_lg_fs);
            vh.tap_count = (TextView) convertView.findViewById(R.id.tv_tap_count);
            vh.next_time = (TextView) convertView.findViewById(R.id.tv_next_time);
            vh.etTimes = (TextView) convertView.findViewById(R.id.tv_etTimes);
            vh.lj_sc = (TextView) convertView.findViewById(R.id.tv_lj_sc);
            vh.gg_mj = (TextView) convertView.findViewById(R.id.tv_gg_mj);
            vh.lj_csl = (TextView) convertView.findViewById(R.id.tv_lj_csl);

            vh.btn_switch = (TextView) convertView.findViewById(R.id.btn_switch);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.drag_view.setTag(position);
        final IrrigationTurnInfo turn = list.get(position);
        vh.turn_name.setText(turn.getTurnname());
        vh.cdz_fk.setText(turn.getPileName());

        vh.tap_count.setText(String.valueOf(turn.getMouthCount()));
        vh.etTimes.setText(turn.getEtTimes());
//        vh.lj_sc.setText(String.valueOf(BigDecimalDivide.devide(turn.getTotalTime(), 60d, 2, BigDecimal.ROUND_HALF_EVEN)));
        vh.gg_mj.setText(String.valueOf(turn.getIrrigationAreas()));
        vh.lj_csl.setText(String.valueOf(BigDecimalDivide.format(turn.getTotalWater())));

        if(turn.getTurnState()==0){
            vh.state_now.setText("休眠中…");
            vh.btn_switch.setText("开启");
        }else{
            vh.state_now.setText("灌溉中…");
            vh.btn_switch.setText("停止");
        }



        if(turn.getStartType()==0){
            vh.lg_fs.setText("自动");
        }else{
            vh.lg_fs.setText("手动");
        }

        if(turn.getStartType()==1){
            vh.next_time.setText("--");
        }else{
            if(turn.getNextExeTime()==null || turn.getNextExeTime()==0){
                vh.next_time.setText("无下次执行任务");
            }else{
                vh.next_time.setText(TimeUtil.datetimestring(new Date(turn.getNextExeTime())));
            }
        }

        vh.btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

               String clickText = ((TextView) v).getText().toString().trim();
                String msg = "";
                if (clickText.equals("开启")){
                    msg = "您确定开启轮灌组吗？";
                }else{
                    msg = "您确定停止轮灌组吗？";
                }

                AlertDialogUtil.showDialog((FragmentActivity) context, "提示", msg, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case AlertDialogUtil.SETOK: // 确定
                                String dowhat = "0";
                                if ("开启".equals(((TextView) v).getText().toString().trim())) {
                                    dowhat = "1";
                                } else {
                                    dowhat = "0";
                                }

                                CreateTempTurnTaskBiz createTempTurnTaskBiz = new CreateTempTurnTaskBiz();

                                createTempTurnTaskBiz.control(v.getContext(), String.valueOf(turn.getId()), dowhat, new NetCallBack<Integer>() {
                                    @Override
                                    public void getNetSuccess(int statu, String url, Integer integer) {
                                        if(integer.intValue()==1){
                                            ToastUtil.toast(context, "任务正在后台操作……");
                                        }else if(integer.intValue()==0){
                                            ToastUtil.toast(context,"操作失败");
                                        }else if(integer.intValue()==2){
                                            ToastUtil.toast(context,"网关未绑定");
                                        }else if(integer.intValue()==-1){
                                            ToastUtil.toast(context,"操作失败，网管不在线");
                                        }else{
                                            ToastUtil.toast(context,"出现异常");
                                        }
                                    }

                                    @Override
                                    public void getNetFauiled(int statu, String url) {

                                    }

                                    @Override
                                    public void getNetCanceled(int statu, String url) {

                                    }

                                    @Override
                                    public void getNetError(int statu, String url) {

                                    }

                                    @Override
                                    public void getNetFinished(int statu, String url) {

                                    }
                                });
                                break;
                            case AlertDialogUtil.SETCANCEL: // 取消


                                break;

                        }
                    }
                });





            }
        });

        return convertView;
    }
    public static class ViewHolder {
        TextView turn_name;//轮灌组名称
        TextView cdz_fk;//出地桩-阀口
        TextView state_now;//当前状态
        TextView lg_fs;//轮灌方式
        TextView tap_count;//阀口数
        TextView next_time;//下次执行时间
        TextView etTimes;//执/总次数
        TextView lj_sc;//累计时长
        TextView gg_mj;//灌溉面积
        TextView lj_csl;//累计出水量

        TextView btn_switch;//控制

        DragView drag_view;
        Button btn_editer;
        Button btn_del;
    }

    public void close(){
        for (int i = 0; i < views.size(); i++) {
            if(views.get(i).isOpen())
                views.get(i).closeAnim();
        }
    }

    private OnDelTurnLisitener onDelTurnLisitener;
    public interface OnDelTurnLisitener{
        void delTurn(String params,int position);
    }
    public  void setOnDelTurnLisitener(OnDelTurnLisitener onDelTurnLisitener){
        this.onDelTurnLisitener=onDelTurnLisitener;
    }

}
