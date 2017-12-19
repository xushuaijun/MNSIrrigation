package com.xsyj.irrigation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 2017/5/8.
 */

public class GetNetGateList implements Serializable{

    private int netGateOnLineLength;
//    private List<NetLists> NetList;
    private List<NetGate> NetList;
    public int getNetGateOnLineLength() {
        return netGateOnLineLength;
    }

    public void setNetGateOnLineLength(int netGateOnLineLength) {
        this.netGateOnLineLength = netGateOnLineLength;
    }

    public List<NetGate> getNetList() {
        return NetList;
    }

    public void setNetList(List<NetGate> netList) {
        NetList = netList;
    }

    //    public List<NetLists> getNetList() {
//        return NetList;
//    }
//
//    public void setNetList(List<NetLists> netList) {
//        NetList = netList;
//    }

    @Override
    public String toString() {
        return "GetNetGateList{" +
                "netGateOnLineLength=" + netGateOnLineLength +
                ", NetList=" + NetList +
                '}';
    }

    public class NetLists{


        private int id;
        private String netGateCode;
        private String netGateName;
        private String netIp;
        private int netPort;
        private int bindTaps;
        private String flag;
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

        public NetLists() {
            super();
            // TODO Auto-generated constructor stub
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

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
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
            return "NetLists{" +
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
                    '}';
        }
    }
}
