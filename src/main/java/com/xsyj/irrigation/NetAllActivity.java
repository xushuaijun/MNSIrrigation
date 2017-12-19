package com.xsyj.irrigation;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.xsyj.irrigation.adapter.CommUseAdapter;
import com.xsyj.irrigation.biz.NetAllBiz;
import com.xsyj.irrigation.biz.QWBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.customview.LJListView;
import com.xsyj.irrigation.entity.IrrigationTask;
import com.xsyj.irrigation.entity.NetWork;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NetAllActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_qw_tj;
    private LJListView lj_net;
    private List<NetWork> nets = new ArrayList<>();
    private CommUseAdapter<NetWork> adapter;
    private Map<String,IrrigationTask> tasks = new HashMap<>();

    public Map<String, IrrigationTask> getTasks() {
        return tasks;
    }

    public void setTasks(Map<String, IrrigationTask> tasks) {
        this.tasks = tasks;
    }

    private List<IrrigationTask> temptasks = new ArrayList<>();

    private Dialog dialog;


    /**
     * 创建任务
     */
    private List<IrrigationTask> createTasks() {
        List<IrrigationTask> list = new ArrayList<>();
        Iterator it = tasks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            IrrigationTask value = (IrrigationTask) entry.getValue();
            list.add(value);
        }
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_all);
        dialog = DialogUtil.createLoadingDialog(this,"正在获取数据……");
        initdata();
        initview();
        setlistener();
    }

    private void setlistener() {
        tv_qw_tj.setOnClickListener(this);
    }

    private void initdata() {
        NetAllBiz allBiz = new NetAllBiz();
        allBiz.getInfo(this, new NetCallBack<CommonData<List<NetWork>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<NetWork>> listCommonData) {
                if (listCommonData != null){
                    List<NetWork> works = listCommonData.getData();
                    nets.addAll(works);
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtil.toast(NetAllActivity.this, "获取数据失败!");
                }

                if (dialog != null){
                    dialog.dismiss();
                }

            }

            @Override
            public void getNetFauiled(int statu, String url) {
                ToastUtil.toast(NetAllActivity.this, "获取数据失败!");
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                ToastUtil.toast(NetAllActivity.this, "连接服务器失败！");
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }

    private void initview() {
        tv_qw_tj = (TextView) findViewById(R.id.tv_qw_tj);

        lj_net = (LJListView)findViewById(R.id.lj_net);
        lj_net.setPullRefreshEnable(false);
        lj_net.setPullLoadEnable(false,"");
        adapter = new CommUseAdapter<>(this,nets,CommUseAdapter.VIEW_TYPE_COMPLEX_VIEW);
        lj_net.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_qw_tj:
                temptasks.clear();
                temptasks.addAll(createTasks());
                if(temptasks!=null && temptasks.size()>0) {
                    dialog = DialogUtil.createLoadingDialog(this,"正在添加……");
                    getcb();
                }else{
                    ToastUtil.toast(this,"请选择网关编号");
                }
                break;
        }
    }

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    private void getcb() {
        QWBiz qwBiz = new QWBiz();
        qwBiz.getInfo(this, gson.toJson(temptasks), new NetCallBack<Integer>() {
            @Override
            public void getNetSuccess(int statu, String url, Integer integer) {
                if(integer>0){
                    ToastUtil.toast(NetAllActivity.this,"添加成功");
                    finish();
                }else{
                    switch (integer.intValue()) {
                        case -1:
                            ToastUtil.toast(NetAllActivity.this, "添加失败");
                            break;
                        case -2:
                            ToastUtil.toast(NetAllActivity.this, "5分钟内不能做相同的操作");
                            break;
                        case -3:
                            ToastUtil.toast(NetAllActivity.this, "请选择内容");
                            break;
                        case -4:
                            ToastUtil.toast(NetAllActivity.this, "网关不在线");
                            break;
                    }
                }
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                ToastUtil.toast(NetAllActivity.this, "添加失败");
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                ToastUtil.toast(NetAllActivity.this, "连接服务器失败！");
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }
}
