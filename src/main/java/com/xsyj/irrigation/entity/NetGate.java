package com.xsyj.irrigation.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/11/14.
 */
public class NetGate extends AdapterGetView implements Serializable {

    private int id;
    private String netGateCode;    //网关编号
    private String netGateName;    //网关名称
    private String netIp;         //网关ip
    private int netPort;           //网关端口
    private int bindTaps;         //与网关绑定的阀门数
    private int flag;
    private String userid;
    private String start_id;
    private String page_size;
    private String inDate;
    private String tapNum;
    private String tapName;
    private String pileName;
    private String loginTime;
    private String areaId;
    private String areaLevel;
    private boolean isSelected;

    public NetGate() {
        super();
        // TODO Auto-generated constructor stub
    }


    public NetGate(int id, String netGateCode, String netGateName, String netIp, int netPort, int bindTaps, boolean isSelected) {
        this.id = id;
        this.netGateCode = netGateCode;
        this.netGateName = netGateName;
        this.netIp = netIp;
        this.netPort = netPort;
        this.bindTaps = bindTaps;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNetGateCode() {
        return netGateCode;
    }

    public void setNetGateCode(String netGateCode) {
        this.netGateCode = netGateCode;
    }

    public String getNetGateName() {
        return netGateName;
    }

    public void setNetGateName(String netGateName) {
        this.netGateName = netGateName;
    }

    public String getNetIp() {
        return netIp;
    }

    public void setNetIp(String netIp) {
        this.netIp = netIp;
    }

    public int getNetPort() {
        return netPort;
    }

    public void setNetPort(int netPort) {
        this.netPort = netPort;
    }

    public int getBindTaps() {
        return bindTaps;
    }

    public void setBindTaps(int bindTaps) {
        this.bindTaps = bindTaps;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getTapNum() {
        return tapNum;
    }

    public void setTapNum(String tapNum) {
        this.tapNum = tapNum;
    }

    public String getTapName() {
        return tapName;
    }

    public void setTapName(String tapName) {
        this.tapName = tapName;
    }

    public String getPileName() {
        return pileName;
    }

    public void setPileName(String pileName) {
        this.pileName = pileName;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    @Override
    public String toString() {
        return "NetGate{" +
                "id=" + id +
                ", netGateCode='" + netGateCode + '\'' +
                ", netGateName='" + netGateName + '\'' +
                ", netIp='" + netIp + '\'' +
                ", netPort=" + netPort +
                ", bindTaps=" + bindTaps +
                ", flag='" + flag + '\'' +
                ", userid='" + userid + '\'' +
                ", start_id='" + start_id + '\'' +
                ", page_size='" + page_size + '\'' +
                ", inDate='" + inDate + '\'' +
                ", tapNum='" + tapNum + '\'' +
                ", tapName='" + tapName + '\'' +
                ", pileName='" + pileName + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaLevel='" + areaLevel + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

    @Override
    public View getStringView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.griditem, null);
            vh.btn_net = (TextView) convertView.findViewById(R.id.btn_item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final NetGate gate = (NetGate) object;
        vh.btn_net.setText(gate.getNetGateCode());
        vh.btn_net.setSelected(gate.isSelected());
        return convertView;
}


    @Override
    public View getView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        return null;
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
    TextView btn_net;
}
}
