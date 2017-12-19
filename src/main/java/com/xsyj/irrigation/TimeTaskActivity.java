package com.xsyj.irrigation;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.xsyj.irrigation.adapter.CommUseAdapter;
import com.xsyj.irrigation.biz.TimeTaskBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.TaskResult;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class TimeTaskActivity extends BaseActivity {

    private long startTime;
    private String tapNum;
    private String openMouth;
    private ListView time_list;
    private List<TaskResult> resultList = new ArrayList<>();
    private CommUseAdapter<TaskResult> adapter;
    private TextView time_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_task);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        startTime = bundle.getLong("startTime");
        tapNum = bundle.getString("tapNum");
        openMouth = bundle.getString("openMouth");
        LogUtil.e("传过来的参数",startTime+"/"+tapNum+"/"+openMouth);
        initview();
        initdata();
    }

    private void initdata() {
        TimeTaskBiz timeTaskBiz = new TimeTaskBiz();
        timeTaskBiz.get_timedata(this, startTime, tapNum, openMouth, new NetCallBack<CommonData<List<TaskResult>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<TaskResult>> listCommonData) {
                List<TaskResult> results = listCommonData.getData();
                LogUtil.e("集合",listCommonData.getData());
                resultList.clear();
                resultList.addAll(results);
                adapter.notifyDataSetChanged();
                time_count.setText("此阀门还有"+resultList.size()+"个任务等待执行");

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
        time_list = (ListView)findViewById(R.id.time_list);
        adapter = new CommUseAdapter<>(this,resultList,CommUseAdapter.VIEW_TYPE_SIMPLE_STRING);
        time_count = (TextView)findViewById(R.id.time_count);
        time_list.setAdapter(adapter);
    }
}
