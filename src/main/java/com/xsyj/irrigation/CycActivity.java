package com.xsyj.irrigation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ExpandableListView;

import com.xsyj.irrigation.adapter.ExpandableListViewAdapter;
import com.xsyj.irrigation.biz.CirclePlanBiz;
import com.xsyj.irrigation.entity.CirclePlan;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class CycActivity extends BaseActivity {

    private ExpandableListView lgz_jh;
    private ExpandableListViewAdapter adapter;
    private List<List<CirclePlan>> childdata = new ArrayList<>();
    private List<String> groupdata = new ArrayList<>();

    private String pumpnum;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyc);
        initview();
        initdata();
        setlistener();
    }

    private void setlistener() {

    }

    private void initview() {
        lgz_jh = (ExpandableListView) findViewById(R.id.lgz_jh);
        lgz_jh.setGroupIndicator(null);

        adapter = new ExpandableListViewAdapter(groupdata,childdata,this);
        lgz_jh.setAdapter(adapter);
        lgz_jh.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                adapter.setSelectedItem(groupPosition);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void initdata(){
        dialog = DialogUtil.createLoadingDialog(this, "正在获取周期计划...");
        Intent intent = getIntent();
        pumpnum = intent.getStringExtra("pumpnum");
        CirclePlanBiz planBiz = new CirclePlanBiz();
        planBiz.getdata(this, pumpnum,new NetCallBack<List<CirclePlan>>() {
            @Override
            public void getNetSuccess(int statu, String url, final List<CirclePlan> circlePlans) {
                if(circlePlans!=null&&circlePlans.size()>0){
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null){
                                dialog.dismiss();
                            }
                            List<String> choicelist = new ArrayList<>();
                            List<CirclePlan> tempPlans=new ArrayList<CirclePlan>();
                            for (int i = 0; i < circlePlans.size(); i++) {
                                if(i>0) {
                                    if (!circlePlans.get(i).getChoiceName().equals(circlePlans.get(i-1).getChoiceName())) {
                                        choicelist.add(circlePlans.get(i).getChoiceName());
                                        tempPlans.add(circlePlans.get(i));
                                    }else{
                                        tempPlans.get(tempPlans.size()-1).getChildren().addAll(circlePlans.get(i).getChildren());
                                    }
                                }else{
                                    choicelist.add(circlePlans.get(i).getChoiceName());
                                    tempPlans.add(circlePlans.get(i));
                                }
                            }
                            groupdata.clear();
                            groupdata.addAll(choicelist);
                            List<List<CirclePlan>> lists = new ArrayList<List<CirclePlan>>();
                            for (int j = 0; j < tempPlans.size(); j++) {
                                    lists.add(tempPlans.get(j).getChildren());
                            }
                            childdata.clear();
                            childdata.addAll(lists);
                            CycActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetCanceled(int statu, String url) {
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetError(int statu, String url) {
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetFinished(int statu, String url) {
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });
    }
}
