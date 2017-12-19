package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/5/15.
 */

public class GetBindTaps implements Serializable{

    private int id;
    private String tapnum;
    private String tapname;
    private int irrigationarea;
    private int providevoltage;
    private int cellvoltage;
    private int pipelinepress;
    private String userid;
    private String taptype;
    private String tapbatch;
    private String netgatecode;
    private String tapparam;
    private int pileid;
    private int pumpid;
    private String pileName;
    private String pumpName;
    private String userName;
    private int isBindA;
    private int isBindB;
    private String openMouth;
    private String stcd;
    private String start_id;
    private String page_size;
    private String areaLevel;
    private String areaId;
    private int isSelected; // 0 未选 ，1 选A，2 选B

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTapnum() {
        return tapnum;
    }

    public void setTapnum(String tapnum) {
        this.tapnum = tapnum;
    }

    public String getTapname() {
        return tapname;
    }

    public void setTapname(String tapname) {
        this.tapname = tapname;
    }

    public int getIrrigationarea() {
        return irrigationarea;
    }

    public void setIrrigationarea(int irrigationarea) {
        this.irrigationarea = irrigationarea;
    }

    public int getProvidevoltage() {
        return providevoltage;
    }

    public void setProvidevoltage(int providevoltage) {
        this.providevoltage = providevoltage;
    }

    public int getCellvoltage() {
        return cellvoltage;
    }

    public void setCellvoltage(int cellvoltage) {
        this.cellvoltage = cellvoltage;
    }

    public int getPipelinepress() {
        return pipelinepress;
    }

    public void setPipelinepress(int pipelinepress) {
        this.pipelinepress = pipelinepress;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTaptype() {
        return taptype;
    }

    public void setTaptype(String taptype) {
        this.taptype = taptype;
    }

    public String getTapbatch() {
        return tapbatch;
    }

    public void setTapbatch(String tapbatch) {
        this.tapbatch = tapbatch;
    }

    public String getNetgatecode() {
        return netgatecode;
    }

    public void setNetgatecode(String netgatecode) {
        this.netgatecode = netgatecode;
    }

    public String getTapparam() {
        return tapparam;
    }

    public void setTapparam(String tapparam) {
        this.tapparam = tapparam;
    }

    public int getPileid() {
        return pileid;
    }

    public void setPileid(int pileid) {
        this.pileid = pileid;
    }

    public int getPumpid() {
        return pumpid;
    }

    public void setPumpid(int pumpid) {
        this.pumpid = pumpid;
    }

    public String getPileName() {
        return pileName;
    }

    public void setPileName(String pileName) {
        this.pileName = pileName;
    }

    public String getPumpName() {
        return pumpName;
    }

    public void setPumpName(String pumpName) {
        this.pumpName = pumpName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIsBindA() {
        return isBindA;
    }

    public void setIsBindA(int isBindA) {
        this.isBindA = isBindA;
    }

    public int getIsBindB() {
        return isBindB;
    }

    public void setIsBindB(int isBindB) {
        this.isBindB = isBindB;
    }

    public String getOpenMouth() {
        return openMouth;
    }

    public void setOpenMouth(String openMouth) {
        this.openMouth = openMouth;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
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

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "GetBindTaps{" +
                "id=" + id +
                ", tapnum='" + tapnum + '\'' +
                ", tapname='" + tapname + '\'' +
                ", irrigationarea=" + irrigationarea +
                ", providevoltage=" + providevoltage +
                ", cellvoltage=" + cellvoltage +
                ", pipelinepress=" + pipelinepress +
                ", userid='" + userid + '\'' +
                ", taptype='" + taptype + '\'' +
                ", tapbatch='" + tapbatch + '\'' +
                ", netgatecode='" + netgatecode + '\'' +
                ", tapparam='" + tapparam + '\'' +
                ", pileid=" + pileid +
                ", pumpid=" + pumpid +
                ", pileName='" + pileName + '\'' +
                ", pumpName='" + pumpName + '\'' +
                ", userName='" + userName + '\'' +
                ", isBindA=" + isBindA +
                ", isBindB='" + isBindB + '\'' +
                ", openMouth='" + openMouth + '\'' +
                ", stcd='" + stcd + '\'' +
                ", start_id='" + start_id + '\'' +
                ", page_size='" + page_size + '\'' +
                ", areaLevel='" + areaLevel + '\'' +
                ", areaId='" + areaId + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
