package com.xsyj.irrigation;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.xsyj.irrigation.adapter.CommUseAdapter;
import com.xsyj.irrigation.biz.WaitTaskBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.TaskResult;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ZXTaskActivity extends BaseActivity {

    private long startTime;
    private List<TaskResult> waitList = new ArrayList<>();
    private TextView wait_count;
    private ListView wait_list;
    private CommUseAdapter<TaskResult> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_task);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        startTime = bundle.getLong("startTime");
        initview();
        initdata();
    }

    private void initdata() {
        WaitTaskBiz waitTaskBiz = new WaitTaskBiz();
        waitTaskBiz.get_waitdata(this, startTime, new NetCallBack<CommonData<List<TaskResult>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<TaskResult>> listCommonData) {
                List<TaskResult> results = listCommonData.getData();
                LogUtil.e("集合", listCommonData.getData());
                waitList.clear();
                waitList.addAll(results);
                adapter.notifyDataSetChanged();
                if(waitList.size()==1){
                    wait_count.setText("当前阀门正在执行任务中...");
                }else if(waitList.size()>1){
                    wait_count.setText("前面有"+waitList.size()+"个任务正在执行中，预计等待"+waitList.size()*2+"分钟。");
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
    }

    private void initview() {
        wait_count = (TextView)findViewById(R.id.wait_count);
        wait_list = (ListView)findViewById(R.id.wait_list);
        adapter = new CommUseAdapter<>(this,waitList,CommUseAdapter.VIEW_TYPE_COMPLEX_VIEW);
        wait_list.setAdapter(adapter);
    }
}
