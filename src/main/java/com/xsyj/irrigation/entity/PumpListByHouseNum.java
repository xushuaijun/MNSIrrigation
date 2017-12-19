package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/5/12.
 */

public class PumpListByHouseNum implements Serializable {

    private int id;
    private String pumpnum;
    private String pumpname;
    private int warnpropumppress;
    private int warnaftpumppress;
    private int warnavoltage;
    private int warnbvoltage;
    private int warncvoltage;
    private int warnaelectricity;
    private int warnbelectricity;
    private int warncelectricity;
    private String userid;
    private String userName;
    private String pumpHouseNum;
    private String gprs;
    private String areaLevel;
    private String areaId;
    private String start_id;
    private String page_size;

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

    public int getWarnpropumppress() {
        return warnpropumppress;
    }

    public void setWarnpropumppress(int warnpropumppress) {
        this.warnpropumppress = warnpropumppress;
    }

    public int getWarnaftpumppress() {
        return warnaftpumppress;
    }

    public void setWarnaftpumppress(int warnaftpumppress) {
        this.warnaftpumppress = warnaftpumppress;
    }

    public int getWarnavoltage() {
        return warnavoltage;
    }

    public void setWarnavoltage(int warnavoltage) {
        this.warnavoltage = warnavoltage;
    }

    public int getWarnbvoltage() {
        return warnbvoltage;
    }

    public void setWarnbvoltage(int warnbvoltage) {
        this.warnbvoltage = warnbvoltage;
    }

    public int getWarncvoltage() {
        return warncvoltage;
    }

    public void setWarncvoltage(int warncvoltage) {
        this.warncvoltage = warncvoltage;
    }

    public int getWarnaelectricity() {
        return warnaelectricity;
    }

    public void setWarnaelectricity(int warnaelectricity) {
        this.warnaelectricity = warnaelectricity;
    }

    public int getWarnbelectricity() {
        return warnbelectricity;
    }

    public void setWarnbelectricity(int warnbelectricity) {
        this.warnbelectricity = warnbelectricity;
    }

    public int getWarncelectricity() {
        return warncelectricity;
    }

    public void setWarncelectricity(int warncelectricity) {
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

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    @Override
    public String toString() {
        return "PumpListByHouseNum{" +
                "id=" + id +
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
                ", page_size='" + page_size + '\'' +
                '}';
    }
}
