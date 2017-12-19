package com.xsyj.irrigation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.xsyj.irrigation.adapter.ParentAdapter;
import com.xsyj.irrigation.biz.NetWorkBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.IrrigationTask;
import com.xsyj.irrigation.entity.NetWork;
import com.xsyj.irrigation.entity.TapInfo;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NetWorkActivity extends BaseActivity implements ExpandableListView.OnGroupExpandListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Context mContext;
    private List<NetWork> parents = new ArrayList<>();
    private ExpandableListView elv_parent;
    private ParentAdapter adapter;
    private TextView tj;
    private List<IrrigationTask> tasks = new ArrayList<>();
    private CheckBox cb_gdyl, cb_dcdy, cb_ssll, cb_ljll, cb_kglzt, cb_cjsj, cb_cddy, cb_lydz, cb_xhqd, cb_bbh;
    private CheckBox[] cbs=new CheckBox[10];
    private int chanel=1;
    private int lastchanel=1;

    private Map<String,IrrigationTask> maptask = new HashMap<>();

    public Map<String, IrrigationTask> getTasks() {
        return maptask;
    }

    public void setTasks(Map<String, IrrigationTask> tasks) {
        this.maptask = tasks;
    }

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);
        mContext = this;
        dialog = DialogUtil.createLoadingDialog(mContext,"正在获取数据……");
        loadData();
        initElist();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (maptask != null){
            maptask.clear();
        }
        adapter.notifyDataSetChanged();

    }

    private void initElist() {

        tj = (TextView) findViewById(R.id.tv_tj);
        tj.setOnClickListener(this);
        cb_gdyl = (CheckBox) findViewById(R.id.cb_gdyl);
        cb_gdyl.setOnCheckedChangeListener(this);
        cbs[0]=cb_gdyl;
        cbs[0].setEnabled(false);

        cb_dcdy = (CheckBox) findViewById(R.id.cb_dcdy);
        cb_dcdy.setOnCheckedChangeListener(this);
        cbs[1]=cb_dcdy;

        cb_ssll = (CheckBox) findViewById(R.id.cb_ssll);
        cb_ssll.setOnCheckedChangeListener(this);
        cbs[2]=cb_ssll;

        cb_ljll = (CheckBox) findViewById(R.id.cb_ljll);
        cb_ljll.setOnCheckedChangeListener(this);
        cbs[3]=cb_ljll;

        cb_kglzt = (CheckBox) findViewById(R.id.cb_kglzt);
        cb_kglzt.setOnCheckedChangeListener(this);
        cbs[4]=cb_kglzt;

        cb_cjsj = (CheckBox) findViewById(R.id.cb_cjsj);
        cb_cjsj.setOnCheckedChangeListener(this);
        cbs[5]=cb_cjsj;

        cb_cddy = (CheckBox) findViewById(R.id.cb_cddy);
        cb_cddy.setOnCheckedChangeListener(this);
        cbs[6]=cb_cddy;

        cb_lydz = (CheckBox) findViewById(R.id.cb_lydz);
        cb_lydz.setOnCheckedChangeListener(this);
        cbs[7]=cb_lydz;

        cb_xhqd = (CheckBox) findViewById(R.id.cb_xhqd);
        cb_xhqd.setOnCheckedChangeListener(this);
        cbs[8]=cb_xhqd;

        cb_bbh = (CheckBox) findViewById(R.id.cb_bbh);
        cb_bbh.setOnCheckedChangeListener(this);
        cbs[9]=cb_bbh;


        elv_parent = (ExpandableListView) findViewById(R.id.elv_parent);
        elv_parent.setOnGroupExpandListener(this);

        adapter = new ParentAdapter(this, parents);

        elv_parent.setAdapter(adapter);


    }


    private void loadData() {
        NetWorkBiz workBiz = new NetWorkBiz();
        workBiz.getInfo(this, new NetCallBack<CommonData<List<NetWork>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<NetWork>> listCommonData) {
                List<NetWork> netWorkList = listCommonData.getData();
                parents.addAll(netWorkList);
                adapter.notifyDataSetChanged();
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                ToastUtil.toast(mContext,"获取数据失败！");
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                ToastUtil.toast(mContext,"连接服务器失败！");
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });

    }

    @Override
    public void onGroupExpand(int groupPosition) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tj:
                if (tasks != null){
                    tasks.clear();
                }

                createTasks(tasks);

                break;
        }
    }

    private void  createTasks(final List<IrrigationTask> tasks){

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {

                Iterator it = maptask.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    IrrigationTask value = (IrrigationTask) entry.getValue();
                    tasks.add(value);

                }

                StringBuilder sb = new StringBuilder();
                for (int i=0;i<tasks.size();i++){
                     sb.delete(0, sb.length() + 1);
                     IrrigationTask task = tasks.get(i);
                     task.setDowhat(chanel);
                     List<TapInfo> infos = task.getOpenmouths();
                    for(int j = 0;j<infos.size();j++){

                        String openmouth = infos.get(j).getOpenMouth();
                        if(j==0){
                            sb.append(openmouth);
                        }else {
                            sb.append(";").append(openmouth);
                        }
                    }
                    task.setOpenmouth(sb.toString());
                }

                runOnUiThread(  new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e("smdd",tasks);
                        startActivity(new Intent(NetWorkActivity.this, CBActivity.class).putExtra("tasks", (Serializable) tasks).putExtra("chanel",chanel));
                    }
                });

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        synchronized (this){
            if(isChecked) {
                chanel = Integer.valueOf(String.valueOf(buttonView.getTag()));
                changebox();
                lastchanel = chanel;
            }
        }






    }

    private void changebox() {
        /**
         * 改变
         */
        cbs[chanel-1].setChecked(true);
        cbs[chanel-1].setEnabled(false);

        cbs[lastchanel-1].setChecked(false);
        cbs[lastchanel-1].setEnabled(true);
    }
}
