package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/11/1.
 */
public class PumpWell implements Serializable{

    private int theser;
    private int id;                             //标识id
    private String pumpnum;                     //首部编号
    private String pumpname;                    //首部名称
    private double warnpropumppress;            //水泵前管道压力报警值
    private double warnaftpumppress;            //水泵后管道压力报警值
    private double warnavoltage;                //A相电压警告值
    private double warnbvoltage;                //B相电压警告值
    private double warncvoltage;                //C相电压警告值
    private double warnaelectricity;            //A相电流警告值
    private double warnbelectricity;            //B相电流警告值
    private double warncelectricity;            //C相电流警告值
    private String userid;                      //用户ID
    private String userName;                    //用户名称
    private String pumpHouseNum;
    private String gprs;
    private String areaLevel;
    private String areaId;
    private String start_id;
    private int page_size;

    public PumpWell(int theser, int id, String pumpnum, String pumpname, double warnpropumppress, double warnaftpumppress, double warnavoltage, double warnbvoltage, double warncvoltage, double warnaelectricity, double warnbelectricity, double warncelectricity, String userid, String userName) {
        this.id = id;
        this.pumpnum = pumpnum;
        this.pumpname = pumpname;
        this.warnpropumppress = warnpropumppress;
        this.warnaftpumppress = warnaftpumppress;
        this.warnavoltage = warnavoltage;
        this.warnbvoltage = warnbvoltage;
        this.warncvoltage = warncvoltage;
        this.warnaelectricity = warnaelectricity;
        this.warnbelectricity = warnbelectricity;
        this.warncelectricity = warncelectricity;
        this.userid = userid;
        this.userName = userName;
    }

    public int getTheser() {
        return theser;
    }

    public void setTheser(int theser) {
        this.theser = theser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPumpnum() {
        return pumpnum;
    }

    public void setPumpnum(String pumpnum) {
        this.pumpnum = pumpnum;
    }

    public String getPumpname() {
        return pumpname;
    }

    public void setPumpname(String pumpname) {
        this.pumpname = pumpname;
    }

    public double getWarnpropumppress() {
        return warnpropumppress;
    }

    public void setWarnpropumppress(double warnpropumppress) {
        this.warnpropumppress = warnpropumppress;
    }

    public double getWarnaftpumppress() {
        return warnaftpumppress;
    }

    public void setWarnaftpumppress(double warnaftpumppress) {
        this.warnaftpumppress = warnaftpumppress;
    }

    public double getWarnavoltage() {
        return warnavoltage;
    }

    public void setWarnavoltage(double warnavoltage) {
        this.warnavoltage = warnavoltage;
    }

    public double getWarnbvoltage() {
        return warnbvoltage;
    }

    public void setWarnbvoltage(double warnbvoltage) {
        this.warnbvoltage = warnbvoltage;
    }

    public double getWarncvoltage() {
        return warncvoltage;
    }

    public void setWarncvoltage(double warncvoltage) {
        this.warncvoltage = warncvoltage;
    }

    public double getWarnaelectricity() {
        return warnaelectricity;
    }

    public void setWarnaelectricity(double warnaelectricity) {
        this.warnaelectricity = warnaelectricity;
    }

    public double getWarnbelectricity() {
        return warnbelectricity;
    }

    public void setWarnbelectricity(double warnbelectricity) {
        this.warnbelectricity = warnbelectricity;
    }

    public double getWarncelectricity() {
        return warncelectricity;
    }

    public void setWarncelectricity(double warncelectricity) {
        this.warncelectricity = warncelectricity;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPumpHouseNum() {
        return pumpHouseNum;
    }

    public void setPumpHouseNum(String pumpHouseNum) {
        this.pumpHouseNum = pumpHouseNum;
    }

    public String getGprs() {
        return gprs;
    }

    public void setGprs(String gprs) {
        this.gprs = gprs;
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

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    @Override
    public String toString() {
        return "PumpWell{" +
                "id=" + id +
                ", pumpnum='" + pumpnum + '\'' +
                ", pumpname='" + pumpname + '\'' +
                ", warnpropumppress=" + warnpropumppress +
                ", warnaftpumppress=" + warnaftpumppress +
                ", warnavoltage=" + warnavoltage +
                ", warnbvoltage=" + warnbvoltage +
                ", warncvoltage=" + warncvoltage +
                ", warnaelectricity=" + warnaelectricity +
                ", warnbelectricity=" + warnbelectricity +
                ", warncelectricity=" + warncelectricity +
                ", userid='" + userid + '\'' +
                ", userName='" + userName + '\'' +
                ", pumpHouseNum='" + pumpHouseNum + '\'' +
                ", gprs='" + gprs + '\'' +
                ", areaLevel='" + areaLevel + '\'' +
                ", areaId='" + areaId + '\'' +
                ", start_id='" + start_id + '\'' +
                ", page_size=" + page_size +
                '}';
    }
}
