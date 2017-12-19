package com.xsyj.irrigation.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class IrrigationTask extends AdapterGetView implements Comparable<IrrigationTask>, Serializable {


    private Integer id;//*

    private String tapnum;//*

    private String openmouth;//*

    private Date starttime;//*

    private Integer irrigationtime;//*

    private Integer irrigationwater;//*

    private Integer dowhat;//*

    private Integer irrigationtype;//*

    private Date addtime;

    private Integer state;//*

    private String netGateCode;//*

    private String stime;

    private String taskId;

    private int tempstate;
    private String flag;
    private int isDownLoad;

    private List<TapInfo> openmouths;

    public IrrigationTask() {
    }

    public IrrigationTask(Integer id, String tapnum, String openmouth, Date starttime, Integer irrigationtime, Integer irrigationwater, Integer dowhat, Integer irrigationtype, Date addtime, Integer state, String netGateCode, String stime, String taskId, int tempstate, String flag, int isDownLoad, List<TapInfo> openmouths) {
        this.id = id;
        this.tapnum = tapnum;
        this.openmouth = openmouth;
        this.starttime = starttime;
        this.irrigationtime = irrigationtime;
        this.irrigationwater = irrigationwater;
        this.dowhat = dowhat;
        this.irrigationtype = irrigationtype;
        this.addtime = addtime;
        this.state = state;
        this.netGateCode = netGateCode;
        this.stime = stime;
        this.taskId = taskId;
        this.tempstate = tempstate;
        this.flag = flag;
        this.isDownLoad = isDownLoad;
        this.openmouths = openmouths;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTapnum() {
        return tapnum;
    }

    public void setTapnum(String tapnum) {
        this.tapnum = tapnum;
    }

    public String getOpenmouth() {
        return openmouth;
    }

    public void setOpenmouth(String openmouth) {
        this.openmouth = openmouth;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Integer getIrrigationtime() {
        return irrigationtime;
    }

    public void setIrrigationtime(Integer irrigationtime) {
        this.irrigationtime = irrigationtime;
    }

    public Integer getIrrigationwater() {
        return irrigationwater;
    }

    public void setIrrigationwater(Integer irrigationwater) {
        this.irrigationwater = irrigationwater;
    }

    public Integer getDowhat() {
        return dowhat;
    }

    public void setDowhat(Integer dowhat) {
        this.dowhat = dowhat;
    }

    public Integer getIrrigationtype() {
        return irrigationtype;
    }

    public void setIrrigationtype(Integer irrigationtype) {
        this.irrigationtype = irrigationtype;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNetGateCode() {
        return netGateCode;
    }

    public void setNetGateCode(String netGateCode) {
        this.netGateCode = netGateCode;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getTempstate() {
        return tempstate;
    }

    public void setTempstate(int tempstate) {
        this.tempstate = tempstate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getIsDownLoad() {
        return isDownLoad;
    }

    public void setIsDownLoad(int isDownLoad) {
        this.isDownLoad = isDownLoad;
    }

    public List<TapInfo> getOpenmouths() {
        return openmouths;
    }

    public void setOpenmouths(List<TapInfo> openmouths) {
        this.openmouths = openmouths;
    }

    @Override
    public String toString() {
        return "IrrigationTask{" +
                "id=" + id +
                ", tapnum='" + tapnum + '\'' +
                ", openmouth='" + openmouth + '\'' +
                ", starttime=" + starttime +
                ", irrigationtime=" + irrigationtime +
                ", irrigationwater=" + irrigationwater +
                ", dowhat=" + dowhat +
                ", irrigationtype=" + irrigationtype +
                ", addtime=" + addtime +
                ", state=" + state +
                ", netGateCode='" + netGateCode + '\'' +
                ", stime='" + stime + '\'' +
                ", taskId='" + taskId + '\'' +
                ", tempstate=" + tempstate +
                ", flag='" + flag + '\'' +
                ", isDownLoad=" + isDownLoad +
                ", openmouths=" + openmouths +
                '}';
    }

    @Override
    public int compareTo(IrrigationTask o) {
        // TODO Auto-generated method stub
        return o.getNetGateCode().compareToIgnoreCase(this.getNetGateCode());
    }


    @Override
    public View getStringView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        return null;
    }

    @Override
    public View getView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        ViewHolder vh;
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.cb_item, null);
            vh.cb_fm = (TextView) convertView.findViewById(R.id.cb_fm);
            vh.cb_wg = (TextView) convertView.findViewById(R.id.cb_wg);
            vh.cb_td = (TextView) convertView.findViewById(R.id.cb_td);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final IrrigationTask it = (IrrigationTask) object;
        vh.cb_fm.setText(it.getOpenmouth());
        vh.cb_wg.setText(it.getNetGateCode());

        switch (it.getDowhat()){
            case 1:
                vh.cb_td.setText("管道压力");
                break;
            case 2:
                vh.cb_td.setText("电池电压");
                break;
            case 3:
                vh.cb_td.setText("瞬时流量");
                break;
            case 4:
                vh.cb_td.setText("累计流量");
                break;
            case 5:
                vh.cb_td.setText("开关量状态");
                break;
            case 6:
                vh.cb_td.setText("采集时间");
                break;
            case 7:
                vh.cb_td.setText("充电电压");
                break;
            case 8:
                vh.cb_td.setText("路由地址");
                break;
            case 9:
                vh.cb_td.setText("信号强度");
                break;
            case 10:
                vh.cb_td.setText("版本号");
                break;
            default:
                break;
        }
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
    static class ViewHolder {

        public TextView cb_fm;
        public TextView cb_wg;
        public TextView cb_td;
    }

}