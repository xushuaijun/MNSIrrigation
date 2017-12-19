package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/11/1.
 */
public class Tap implements Serializable{

    private int id;                   //标识id
    private String tapnum;            //阀门编号
    private String tapname;           //阀门名称
    private double irrigationarea;    //灌溉面积
    private double providevoltage;    //供电电压
    private double cellvoltage;       //电池电压
    private double pipelinepress;     //管道压力
    private String userid;            //用户id
    private String taptype;           //规格型号
    private String tapbatch;          //生产批次
    private String netgatecode;       //网关编号
    private String netgatename;       //网关名称
    private String tapparam;          //
    private int pileid;               //出地桩id
    private int pumpid;               //
    private String pileName;          //出地桩id
    private String pumpName;          //首部id
    private String userName;          //阀门用户id
    private int isBindA;              //
    private int isBindB;              //
    private String openMouth;         //
    private String stcd;              //
    private int isDownLoad;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Tap(int id, String tapnum, String tapname, double irrigationarea, double providevoltage, double cellvoltage, double pipelinepress, String userid, String taptype, String tapbatch, String netgatecode, String netgatename, String tapparam, int pileid, int pumpid, String pileName, String pumpName, String userName, int isBindA, int isBindB, String openMouth, String stcd, int isDownLoad) {
        this.id = id;
        this.tapnum = tapnum;
        this.tapname = tapname;
        this.irrigationarea = irrigationarea;
        this.providevoltage = providevoltage;
        this.cellvoltage = cellvoltage;
        this.pipelinepress = pipelinepress;
        this.userid = userid;
        this.taptype = taptype;
        this.tapbatch = tapbatch;
        this.netgatecode = netgatecode;
        this.netgatename = netgatename;
        this.tapparam = tapparam;
        this.pileid = pileid;
        this.pumpid = pumpid;
        this.pileName = pileName;
        this.pumpName = pumpName;
        this.userName = userName;
        this.isBindA = isBindA;
        this.isBindB = isBindB;
        this.openMouth = openMouth;
        this.stcd = stcd;
        this.isDownLoad = isDownLoad;
    }

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

    public double getIrrigationarea() {
        return irrigationarea;
    }

    public void setIrrigationarea(double irrigationarea) {
        this.irrigationarea = irrigationarea;
    }

    public double getProvidevoltage() {
        return providevoltage;
    }

    public void setProvidevoltage(double providevoltage) {
        this.providevoltage = providevoltage;
    }

    public double getCellvoltage() {
        return cellvoltage;
    }

    public void setCellvoltage(double cellvoltage) {
        this.cellvoltage = cellvoltage;
    }

    public double getPipelinepress() {
        return pipelinepress;
    }

    public void setPipelinepress(double pipelinepress) {
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

    public String getNetgatename() {
        return netgatename;
    }

    public void setNetgatename(String netgatename) {
        this.netgatename = netgatename;
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

    public int getIsDownLoad() {
        return isDownLoad;
    }

    public void setIsDownLoad(int isDownLoad) {
        this.isDownLoad = isDownLoad;
    }

    @Override
    public String toString() {
        return "Tap{" +
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
                ", netgatename='" + netgatename + '\'' +
                ", tapparam='" + tapparam + '\'' +
                ", pileid=" + pileid +
                ", pumpid=" + pumpid +
                ", pileName='" + pileName + '\'' +
                ", pumpName='" + pumpName + '\'' +
                ", userName='" + userName + '\'' +
                ", isBindA=" + isBindA +
                ", isBindB=" + isBindB +
                ", openMouth='" + openMouth + '\'' +
                ", stcd='" + stcd + '\'' +
                ", isDownLoad=" + isDownLoad +
                '}';
    }
}
