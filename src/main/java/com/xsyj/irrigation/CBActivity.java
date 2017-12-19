package com.xsyj.irrigation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xsyj.irrigation.adapter.CommUseAdapter;
import com.xsyj.irrigation.biz.NetWorkBiz;
import com.xsyj.irrigation.biz.QWBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.customview.LJListView;
import com.xsyj.irrigation.entity.IrrigationTask;
import com.xsyj.irrigation.entity.NetWork;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.List;

public class CBActivity extends BaseActivity implements View.OnClickListener {

    private Context context;
    private LJListView lj_cb;
    private CommUseAdapter<IrrigationTask> adapter;
    private List<IrrigationTask> tasks;
    private TextView tv_submit;

    private Dialog dialog;

    public List<IrrigationTask> getTasks() {
        return tasks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cb);
        context = this;
        Intent intent = getIntent();
        tasks = (List<IrrigationTask>) intent.getSerializableExtra("tasks");
        initview();
        setlistener();
    }

    private void initview() {
        tv_submit = (TextView)findViewById(R.id.tv_submit);
        lj_cb = (LJListView) findViewById(R.id.lj_cb);
        adapter = new CommUseAdapter<>(this,tasks,CommUseAdapter.VIEW_TYPE_COMPLEX_VIEW);
        lj_cb.setAdapter(adapter);
    }

    private void setlistener() {
        tv_submit.setOnClickListener(this);
    }

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    private void getqw() {
        QWBiz qwBiz = new QWBiz();
        qwBiz.getInfo(this, gson.toJson(tasks), new NetCallBack<Integer>() {
            @Override
            public void getNetSuccess(int statu, String url, Integer integer) {
                if(integer>0){
                    ToastUtil.toast(CBActivity.this, "添加成功");
                    finish();
                }else{
                    switch (integer.intValue()) {
                        case -1:
                            ToastUtil.toast(CBActivity.this, "添加失败");
                            break;
                        case -2:
                            ToastUtil.toast(CBActivity.this, "5分钟内不能做相同的操作");
                            break;
                        case -3:
                            ToastUtil.toast(CBActivity.this, "请选择内容");
                            break;
                        case -4:
                            ToastUtil.toast(CBActivity.this, "网关不在线");
                            break;
                    }
                }
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                ToastUtil.toast(context,"获取数据失败！");
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                ToastUtil.toast(context,"连接服务器失败！");
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit:
                dialog = DialogUtil.createLoadingDialog(this, "正在提交……");
                getqw();
                break;
        }
    }
}
