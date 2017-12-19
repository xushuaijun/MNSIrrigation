package com.xsyj.irrigation;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xsyj.irrigation.adapter.TaskStatusAdapter;
import com.xsyj.irrigation.biz.TaskToViewBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.customview.AbstractSpinerAdapter;
import com.xsyj.irrigation.customview.LJListView;
import com.xsyj.irrigation.entity.NetGate;
import com.xsyj.irrigation.customview.SpinerPopWindow;
import com.xsyj.irrigation.entity.TaskToView;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmh on 2017/7/3.
 */

public class TapTaskStatusActivity extends BaseActivity implements View.OnClickListener,
        LJListView.IXListViewListener{
    private Context context;
    private TextView titleTxt;
    private TextView tv_search;
    private LJListView lj_taskstatus;

    private PopupWindow popupWindow;
    private SpinerPopWindow mSpinerPopWindowType;
    private SpinerPopWindow mSpinerPopWindowStatus;

    private TaskStatusAdapter adapter;
    private List<NetGate> list = new ArrayList<NetGate>();
    private List<String> taskTypes = new ArrayList<String>();
    private List<String> operaStatus = new ArrayList<String>();
    private List<TaskToView> taskToViewList = new ArrayList<TaskToView>();

    private String start_id = "0";
    private int tasktype = -1;
    private int state = -1;
    private String tapNum;
    private String netGateCode;
    private String maxAddTime;
    private String minAddTime;

    private final int TASKTOVIEW_SUCCESS = 0;
    private final int TASKTOVIEW_FAIL = 1;
    private final int NET_GET_ERROR = 2;

    private Dialog dialog;
    private int dowhat = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TASKTOVIEW_SUCCESS:
                    CommonData<List<TaskToView>> listCommonData = ( CommonData<List<TaskToView>>)msg.obj;
                    if (dowhat != 2){
                        if(taskToViewList != null){
                            taskToViewList.clear();
                        }
                    }
                    taskToViewList.addAll(listCommonData.getData());
                    adapter.notifyDataSetChanged();
                    switch (dowhat){
                        case 0:
                        case 1:
                            if (taskToViewList.size() == 0){
                                ToastUtil.toast(context, "暂无数据");
                            }
                            listStop();
                            break;
                        case 2:
                            if (listCommonData.getData() == null || listCommonData.getData().size() == 0){
                                ToastUtil.toast(context, "没有更多数据");
                            }
                            listStop();
                            break;
                        default:break;
                    }
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    break;
                case TASKTOVIEW_FAIL:
                    ToastUtil.toast(context,msg.obj.toString());
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    if(dowhat != 0){
                        listStop();
                    }
                    break;
                case NET_GET_ERROR:
                    ToastUtil.toast(context,msg.obj.toString());
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    if(dowhat != 0){
                        listStop();
                    }
                    break;
                default:break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_taskstatus);
        context = this;
        initView();
    }

    private void initView(){
        titleTxt = (TextView) findViewById(R.id.titleTxt);
        titleTxt.setText("任务状态");
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setVisibility(View.VISIBLE);
        tv_search.setOnClickListener(this);
        lj_taskstatus = (LJListView) findViewById(R.id.lj_taskstatus);
        adapter = new TaskStatusAdapter(taskToViewList, context);
        lj_taskstatus.setAdapter(adapter);

        lj_taskstatus.setPullLoadEnable(true, "加载更多……"); // 如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,"")
        lj_taskstatus.setPullRefreshEnable(true);
        lj_taskstatus.setIsAnimation(true);
        lj_taskstatus.setXListViewListener(this);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_taskstatus_layout, null);
//        initPopView(view);
        //自适配长、框设置
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.bg_white));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        initData();

    }

    private void initData(){
        dialog = DialogUtil.createLoadingDialog(context,"正在获取数据……");
        getTask();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                Intent intent = new Intent(this,TapTaskStatusChooseActivity.class);
                intent.putExtra("tasktype",tasktype);
                intent.putExtra("state",state);
                intent.putExtra("tapNum",tapNum);
                intent.putExtra("netGateCode",netGateCode);
                intent.putExtra("maxAddTime",maxAddTime);
                intent.putExtra("minAddTime",minAddTime);
                startActivityForResult(intent,100);
                break;
            default:break;
        }
    }


    @Override
    public void onRefresh() {
        //下拉刷新
        dowhat = 1;
        start_id = "0";
        getTask();
    }

    @Override
    public void onLoadMore() {
        //上滑加载
        dowhat = 2;
        start_id = taskToViewList.get(taskToViewList.size() - 1).getId()+"";
        getTask();

    }

    private void listStop(){
        lj_taskstatus.setCount(String.valueOf(taskToViewList.size()));
        lj_taskstatus.stopRefresh();
        lj_taskstatus.stopLoadMore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100){
            tasktype = data.getIntExtra("tasktype",-1);
            state = data.getIntExtra("state", -1);
            tapNum = data.getStringExtra("tapNum");
            netGateCode = data.getStringExtra("netGateCode");
            maxAddTime = data.getStringExtra("maxAddTime");
            minAddTime = data.getStringExtra("minAddTime");
            dialog = DialogUtil.createLoadingDialog(context,"正在查询数据……");
            dowhat = 0;
            start_id = "0";
            getTask();
        }

    }

    private void getTask(){
        TaskToViewBiz taskToViewBiz = new TaskToViewBiz();
        taskToViewBiz.getInfo(context, start_id, tasktype, state, tapNum, netGateCode, maxAddTime,
                minAddTime, new NetCallBack<CommonData<List<TaskToView>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<TaskToView>> listCommonData) {
                LogUtil.e("任务详情", listCommonData);
                if (listCommonData != null){
                    handler.obtainMessage(TASKTOVIEW_SUCCESS,listCommonData).sendToTarget();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                handler.obtainMessage(TASKTOVIEW_FAIL,"获取数据失败").sendToTarget();
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                handler.obtainMessage(NET_GET_ERROR,"连接服务器失败").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }

}
