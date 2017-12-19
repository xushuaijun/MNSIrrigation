package com.xsyj.irrigation.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.TimeUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Android on 2017/1/13.
 */
public class TaskResult extends AdapterGetView implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String taskid;
    private Integer tasktype;
    private Integer instructtype;
    private long addtime;
    private Integer state;
    private String note;
    private Integer turnid;
    private String tapNum;
    private String succtaps;
    private String failetaps;
    private long startTime;
    private long endTime;
    private long updateTime;
    private String userId;

    public TaskResult(){
        super();
    }

    public TaskResult(Integer id, String taskid, Integer tasktype, Integer instructtype, long addtime, Integer state, String note, Integer turnid, String tapNum, String succtaps, String failetaps, long startTime, long endTime, long updateTime, String userId) {
        this.id = id;
        this.taskid = taskid;
        this.tasktype = tasktype;
        this.instructtype = instructtype;
        this.addtime = addtime;
        this.state = state;
        this.note = note;
        this.turnid = turnid;
        this.tapNum = tapNum;
        this.succtaps = succtaps;
        this.failetaps = failetaps;
        this.startTime = startTime;
        this.endTime = endTime;
        this.updateTime = updateTime;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public Integer getTasktype() {
        return tasktype;
    }

    public void setTasktype(Integer tasktype) {
        this.tasktype = tasktype;
    }

    public Integer getInstructtype() {
        return instructtype;
    }

    public void setInstructtype(Integer instructtype) {
        this.instructtype = instructtype;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getTurnid() {
        return turnid;
    }

    public void setTurnid(Integer turnid) {
        this.turnid = turnid;
    }

    public String getTapNum() {
        return tapNum;
    }

    public void setTapNum(String tapNum) {
        this.tapNum = tapNum;
    }

    public String getSucctaps() {
        return succtaps;
    }

    public void setSucctaps(String succtaps) {
        this.succtaps = succtaps;
    }

    public String getFailetaps() {
        return failetaps;
    }

    public void setFailetaps(String failetaps) {
        this.failetaps = failetaps;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "id=" + id +
                ", taskid='" + taskid + '\'' +
                ", tasktype=" + tasktype +
                ", instructtype=" + instructtype +
                ", addtime=" + addtime +
                ", state=" + state +
                ", note='" + note + '\'' +
                ", turnid=" + turnid +
                ", tapNum='" + tapNum + '\'' +
                ", succtaps='" + succtaps + '\'' +
                ", failetaps='" + failetaps + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", updateTime=" + updateTime +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
    public View getStringView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        ViewHolder vh1;
        if(convertView == null) {
            vh1 = new ViewHolder();
            convertView = inflater.inflate(R.layout.task_item, null);
            vh1.time = (TextView) convertView.findViewById(R.id.time);
            vh1.task_type = (TextView) convertView.findViewById(R.id.task_type);
            vh1.task_state = (TextView) convertView.findViewById(R.id.task_state);
            vh1.task_cz = (TextView) convertView.findViewById(R.id.task_cz);
            convertView.setTag(vh1);
        }else{
            vh1 = (ViewHolder) convertView.getTag();
        }
        final TaskResult tr = (TaskResult) object;
        //任务开始时间
        vh1.time.setText(TimeUtil.datetimestring(new Date(tr.getStartTime())));
        LogUtil.e("执行了",String.valueOf(tr.getStartTime()));
        //任务类型
        switch(tr.getTasktype()){
            case 1:
                vh1.task_type.setText("单控");
                break;
            case 2:
                vh1.task_type.setText("轮灌");
                break;
            case 3:
                vh1.task_type.setText("临时轮灌");
                break;
        }
        //任务操作
        switch(tr.getInstructtype()){
            case 0:
                vh1.task_cz.setText("关");
                break;
            case 1:
                vh1.task_cz.setText("开");
                break;
            case 2:
                vh1.task_cz.setText("复位");
                break;
            case 3:
                vh1.task_cz.setText("多网抄表");
                break;
            case 4:
                vh1.task_cz.setText("全网抄表");
                break;
            case 5:
                vh1.task_cz.setText("绑定网关");
                break;
            case 6:
                vh1.task_cz.setText("解除绑定");
                break;
        }
        vh1.task_state.setText("等待执行");

        return convertView;
    }

    @Override
    public View getView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        ViewHolder vh;
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.task_item, null);
            vh.time = (TextView) convertView.findViewById(R.id.time);
            vh.task_type = (TextView) convertView.findViewById(R.id.task_type);
            vh.task_state = (TextView) convertView.findViewById(R.id.task_state);
            vh.task_cz = (TextView) convertView.findViewById(R.id.task_cz);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final TaskResult tr = (TaskResult) object;
        //任务开始时间
        vh.time.setText(TimeUtil.datetimestring(new Date(tr.getStartTime())));
        //任务类型
        switch(tr.getTasktype()){
            case 1:
                vh.task_type.setText("单控");
                break;
            case 2:
                vh.task_type.setText("轮灌");
                break;
            case 3:
                vh.task_type.setText("临时轮灌");
                break;
        }
        //任务操作
        switch(tr.getInstructtype()){
            case 0:
                vh.task_cz.setText("关");
                break;
            case 1:
                vh.task_cz.setText("开");
                break;
            case 2:
                vh.task_cz.setText("复位");
                break;
            case 3:
                vh.task_cz.setText("多网抄表");
                break;
            case 4:
                vh.task_cz.setText("全网抄表");
                break;
            case 5:
                vh.task_cz.setText("绑定网关");
                break;
            case 6:
                vh.task_cz.setText("解除绑定");
                break;
        }
//        switch (tr.getState()){
//            case -1:
//                vh.task_state.setText("网关指令成功");
//                break;
//            case 0:
//                vh.task_state.setText("失败");
//                break;
//            case 1:
//                vh.task_state.setText("成功");
//                break;
//            case 2:
//                vh.task_state.setText("等待中……");
//                break;
//            case 3:
//                vh.task_state.setText("执行中……");
//                break;
//        }

        vh.task_state.setText("执行中……");
        return convertView;
    }

    @Override
    public View getView1(Object object, int position, View convertView, LayoutInflater inflater, Context context) {

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    public static class ViewHolder {

        public TextView time;
        public TextView task_type;
        public TextView task_state;
        public TextView task_cz;
    }
}
