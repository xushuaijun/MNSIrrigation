package com.xsyj.irrigation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xsyj.irrigation.FragmentActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.customview.DragView;
import com.xsyj.irrigation.entity.IrrigationTaskTurn;
import com.xsyj.irrigation.fragment.HomePageFragment;
import com.xsyj.irrigation.utils.DateTimePickerDialog;
import com.xsyj.irrigation.utils.TimeUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lenovo on 2016/9/2.
 */
public class PullToRefreshListViewAdapter1 extends BaseAdapter {

    private final LayoutInflater inflater;
    private final int modle;
    List<DragView> views = new ArrayList<DragView>();
    private List<IrrigationTaskTurn> list = new ArrayList<IrrigationTaskTurn>();
    private Context context;

    /**
     *
     * @param list
     * @param context
     * @param modle    0: 创建新任务 ，1:任务周期列表
     */
    public PullToRefreshListViewAdapter1(List<IrrigationTaskTurn> list, Context context,int modle) {
        this.list .clear();
        this.list.addAll(list);
        this.context = context;
        this.inflater= LayoutInflater.from(context);
        this.modle=modle;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IrrigationTaskTurn getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.lgz_item,null);
            vh.drag_lgzjh_view = (DragView)convertView.findViewById(R.id.drag_lgzjh_view);
            vh.drag_lgzjh_view.setTag(position);
            views.add(vh.drag_lgzjh_view);
            vh.drag_lgzjh_view.setOnDragStateListener(new DragView.DragStateListener() {
                @Override
                public void onOpened(DragView dragView) {

                }

                @Override
                public void onClosed(DragView dragView) {

                }

                @Override
                public void onForegroundViewClick(DragView dragView, View v) {

                    int pos = (int) dragView.getTag();
                }

                @Override
                public void onBackgroundViewClick(DragView dragView, View v) {
                    switch (v.getId()) {
                        case R.id.btn_lgzjh_del:


                            int pos = (int) dragView.getTag();
                            FragmentManager t = ((FragmentActivity) context).getSupportFragmentManager();
                            String data = "";
                            String starttime = list.get(pos).getLstarttime();
                            String endtime = list.get(pos).getLendtime();

                            if (!TextUtils.isEmpty(starttime) && !TextUtils.isEmpty(endtime)){
                                starttime = starttime.substring(0,16);
                                endtime = endtime.substring(0,16);
                               data = list.get(pos).getTurnid() + "_" + starttime+ "_" +
                                       endtime+"_"+
                                       ((HomePageFragment)t.findFragmentByTag("homepagefragment")).getPage3().getCurrCircle();
                                ((HomePageFragment)t.findFragmentByTag("homepagefragment")).getPage3().delIrrTurnComm(data);
                            }

                            ((HomePageFragment)t.findFragmentByTag("homepagefragment")).getPage3().getLgz_set_list().remove(pos);
                            list.remove(pos);
                            notifyDataSetChanged();
                            break;
                    }
                }
            });
            vh.tv_lgzjh_name = (TextView) convertView.findViewById(R.id.tv_lgzjh_name);
            // 开始时间
            vh.tv_ks_sj = (TextView) convertView.findViewById(R.id.tv_ks_sj);

            // 结束时间


            vh.tv_js_sj = (TextView) convertView.findViewById(R.id.tv_js_sj);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        final IrrigationTaskTurn taskTurn = list.get(position);
        vh.tv_lgzjh_name.setText(taskTurn.getTurnname());
        if(taskTurn.getLstarttime()!= null && taskTurn.getLendtime()!= null){
//            vh.tv_ks_sj.setText(TimeUtil.datetimestring(taskTurn.getStarttime()));
//            vh.tv_js_sj.setText(TimeUtil.datetimestring(taskTurn.getEndtime()));
            vh.tv_ks_sj.setText(taskTurn.getLstarttime());
            vh.tv_js_sj.setText(taskTurn.getLendtime());
        }else{

            vh.tv_ks_sj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateTimePickerDialog ks_dateTimePickerDialog = new DateTimePickerDialog((Activity)context,new Date());
                    ks_dateTimePickerDialog.dateTimePicKDialog(vh.tv_ks_sj);
                    ks_dateTimePickerDialog.setOnTimeClickListener(new DateTimePickerDialog.OnTimeClickListener() {
                        @Override
                        public void OnSubmit(String dateTime) {
                            FragmentManager t = ((FragmentActivity) context).getSupportFragmentManager();
                            ((HomePageFragment)t.findFragmentByTag("homepagefragment")).getPage3().
                                    getLgz_set_list().get(position).setStarttime(TimeUtil.stringtoDateTime(dateTime));
                        }
                    });
                }
            });


            vh.tv_js_sj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateTimePickerDialog js_dateTimePickerDialog = new DateTimePickerDialog((Activity)context,new Date());
                    js_dateTimePickerDialog.dateTimePicKDialog(vh.tv_js_sj);
                    js_dateTimePickerDialog.setOnTimeClickListener(new DateTimePickerDialog.OnTimeClickListener() {
                        @Override
                        public void OnSubmit(String dateTime) {
                            FragmentManager t = ((FragmentActivity) context).getSupportFragmentManager();
                            ((HomePageFragment)t.findFragmentByTag("homepagefragment")).getPage3().
                                    getLgz_set_list().get(position).setEndtime(TimeUtil.stringtoDateTime(dateTime));
                        }
                    });
                }
            });
        }

        return convertView;
    }

    public static class ViewHolder {

        public DragView drag_lgzjh_view;
        public TextView tv_lgzjh_name;
        public TextView tv_ks_sj;
        public TextView tv_js_sj;
    }

    public void close(){
        for (int i = 0; i < views.size(); i++) {
            if(views.get(i).isOpen())
                views.get(i).closeAnim();
        }
    }
}
