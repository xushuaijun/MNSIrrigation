package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/5/24.
 */

public class PumpTask implements Serializable{

    private String userId;
    private String pumpNum;
    private int dowhat;
    private String gprs;
    private String pumpHouseNum;


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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPumpNum() {
        return pumpNum;
    }

    public void setPumpNum(String pumpNum) {
        this.pumpNum = pumpNum;
    }

    public int getDowhat() {
        return dowhat;
    }

    public void setDowhat(int dowhat) {
        this.dowhat = dowhat;
    }


}
