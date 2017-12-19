package com.xsyj.irrigation;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
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
import com.xsyj.irrigation.customview.AbstractSpinerAdapter;
import com.xsyj.irrigation.customview.LJListView;
import com.xsyj.irrigation.customview.SpinerPopWindow;
import com.xsyj.irrigation.entity.NetGate;
import com.xsyj.irrigation.fragment.HomePageFragment;
import com.xsyj.irrigation.utils.DateTimePickerDialog;
import com.xsyj.irrigation.utils.DateTimePickerDialog1;
import com.xsyj.irrigation.utils.TimeUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xmh on 2017/7/3.
 */

public class TapTaskStatusChooseActivity extends Activity implements View.OnClickListener,
       AbstractSpinerAdapter.IOnItemSelectListener{
    private Context context;

    private EditText et_search_tapnum;
    private EditText et_search_netgate;

    private RelativeLayout view_task_type;
    private TextView tv_task_type;
    private RelativeLayout view_task_operastatus;
    private TextView tv_task_operastatus;

    private RelativeLayout view_task_stime;
    private TextView tv_task_stime;
    private RelativeLayout view_task_etime;
    private TextView tv_task_etime;

    private Button btn_task_reset;
    private Button btn_task_sure;

    private SpinerPopWindow mSpinerPopWindowType;
    private SpinerPopWindow mSpinerPopWindowStatus;

    private List<String> taskTypes = new ArrayList<String>();
    private List<String> operaStatus = new ArrayList<String>();

    private int spinPopId = 0;// 0 任务类型 1 执行状态

    private int tasktype = -1;
    private int state = -1;
    private String tapNum;
    private String netGateCode;
    private String maxAddTime;
    private String minAddTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_taskstatus_layout);
        context = this;
        initView();
    }

    private void initView(){

        et_search_tapnum = (EditText) findViewById(R.id.et_search_tapnum);
        et_search_netgate = (EditText) findViewById(R.id.et_search_netgate);

        view_task_type = (RelativeLayout) findViewById(R.id.view_task_type);
        view_task_type.setOnClickListener(this);
        tv_task_type = (TextView) findViewById(R.id.tv_task_type);
        view_task_operastatus = (RelativeLayout) findViewById(R.id.view_task_operastatus);
        view_task_operastatus.setOnClickListener(this);
        tv_task_operastatus = (TextView) findViewById(R.id.tv_task_operastatus);

        view_task_stime = (RelativeLayout) findViewById(R.id.view_task_stime);
        view_task_stime.setOnClickListener(this);
        tv_task_stime = (TextView) findViewById(R.id.tv_task_stime);
        view_task_etime = (RelativeLayout) findViewById(R.id.view_task_etime);
        view_task_etime.setOnClickListener(this);
        tv_task_etime = (TextView) findViewById(R.id.tv_task_etime);

        btn_task_reset = (Button) findViewById(R.id.btn_task_reset);
        btn_task_reset.setOnClickListener(this);
        btn_task_sure = (Button) findViewById(R.id.btn_task_sure);
        btn_task_sure.setOnClickListener(this);

        initData();

    }


    private void initData(){
        Intent data = getIntent();
        tasktype = data.getIntExtra("tasktype",-1);
        state = data.getIntExtra("state", -1);
        tapNum = data.getStringExtra("tapNum");
        netGateCode = data.getStringExtra("netGateCode");
        maxAddTime = data.getStringExtra("maxAddTime");
        minAddTime = data.getStringExtra("minAddTime");

        if (!TextUtils.isEmpty(tapNum)){
            et_search_tapnum.setText(tapNum);
        }
        if (!TextUtils.isEmpty(netGateCode)){
            et_search_netgate.setText(netGateCode);
        }
        if (!TextUtils.isEmpty(maxAddTime)){
            tv_task_etime.setText(maxAddTime);
        }
        if (!TextUtils.isEmpty(minAddTime)){
            tv_task_stime.setText(minAddTime);
        }

        taskTypes.add("请选择");
        taskTypes.add("单控");
        taskTypes.add("计划轮灌");
        taskTypes.add("临时轮灌");
        taskTypes.add("定时任务");

        operaStatus.add("请选择");
        operaStatus.add("失败");
        operaStatus.add("成功");
        operaStatus.add("命令下发");
        operaStatus.add("等待中");
        operaStatus.add("执行中");

        initSpinPopType();
        initSpinPopStatus();

    }

    private void initSpinPopType(){
        mSpinerPopWindowType = new SpinerPopWindow(context);
        if (tasktype != -1){
            mSpinerPopWindowType.refreshData(taskTypes,tasktype);
            tv_task_type.setText(taskTypes.get(tasktype));
        }else{
            mSpinerPopWindowType.refreshData(taskTypes, 0);
            tv_task_type.setText(taskTypes.get(0));
        }
        mSpinerPopWindowType.setItemListener(this);

    }
    private void showSpinWindowType(){
        Log.e("", "showSpinWindow");
        if (mSpinerPopWindowType != null){
            mSpinerPopWindowType.setWidth(view_task_type.getWidth());
            mSpinerPopWindowType.showAsDropDown(view_task_type);
        }

    }

    private void initSpinPopStatus(){
        mSpinerPopWindowStatus = new SpinerPopWindow(context);
        if (state != -1){
            mSpinerPopWindowStatus.refreshData(operaStatus, state + 1);
            tv_task_operastatus.setText(operaStatus.get(state + 1));
        }else{
            mSpinerPopWindowStatus.refreshData(operaStatus, 0);
            tv_task_operastatus.setText(operaStatus.get(0));
        }
        mSpinerPopWindowStatus.setItemListener(this);

    }
    private void showSpinWindowStatus(){
        Log.e("", "showSpinWindow");
        if (mSpinerPopWindowStatus != null){
            mSpinerPopWindowStatus.setWidth(view_task_operastatus.getWidth());
            mSpinerPopWindowStatus.showAsDropDown(view_task_operastatus);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.view_task_type:
                spinPopId = 0;
                showSpinWindowType();
                break;
            case R.id.view_task_operastatus:
                spinPopId = 1;
                showSpinWindowStatus();
                break;
            case R.id.view_task_stime:
                DateTimePickerDialog1 ks_dateTimePickerDialog = new DateTimePickerDialog1((Activity)context,new Date());
                ks_dateTimePickerDialog.dateTimePicKDialog(tv_task_stime);
                ks_dateTimePickerDialog.setOnTimeClickListener(new DateTimePickerDialog1.OnTimeClickListener() {
                    @Override
                    public void OnSubmit(String dateTime) {
                        if (dateTime.contains("选择")){
                            minAddTime = "";
                        }else{
                            minAddTime = dateTime;
                        }

                    }
                });
                break;
            case R.id.view_task_etime:
                DateTimePickerDialog1 js_dateTimePickerDialog = new DateTimePickerDialog1((Activity)context,new Date());
                js_dateTimePickerDialog.dateTimePicKDialog(tv_task_etime);
                js_dateTimePickerDialog.setOnTimeClickListener(new DateTimePickerDialog1.OnTimeClickListener() {
                    @Override
                    public void OnSubmit(String dateTime) {
                        if (dateTime.contains("选择")){
                            maxAddTime = "";
                        }else{
                            maxAddTime = dateTime;
                        }
                    }
                });
                break;
            case R.id.btn_task_reset:
                reset();
                break;
            case R.id.btn_task_sure:
                tapNum = et_search_tapnum.getText().toString();
                netGateCode = et_search_netgate.getText().toString();
                sure();
                break;
            default:break;
        }
    }

    private void reset(){
        tasktype = -1;
        state = -1;
        tapNum = "";
        netGateCode = "";
        maxAddTime = "";
        minAddTime = "";
        et_search_tapnum.setText("");
        et_search_tapnum.setText("");
        tv_task_type.setText("请选择");
        tv_task_operastatus.setText("请选择");
        tv_task_stime.setText("");
        tv_task_etime.setText("");
        sure();
    }

    private void sure(){
        Intent intent = new Intent();
        intent.putExtra("tasktype",tasktype);
        intent.putExtra("state",state);
        intent.putExtra("tapNum",tapNum);
        intent.putExtra("netGateCode",netGateCode);
        intent.putExtra("maxAddTime",maxAddTime);
        intent.putExtra("minAddTime",minAddTime);
        setResult(100, intent);
        finish();
    }


    @Override
    public void onItemClick(int pos) {
        switch (spinPopId){
            case 0:
                tv_task_type.setText(taskTypes.get(pos));
                if (pos == 0){
                    tasktype = -1;
                }else{
                    tasktype = pos;
                }
                break;
            case 1:
                tv_task_operastatus.setText(operaStatus.get(pos));
                state = pos - 1;
                break;

            default:break;
        }
    }

}
