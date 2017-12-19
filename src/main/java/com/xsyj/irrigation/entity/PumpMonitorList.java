package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/5/23.
 */

public class PumpMonitorList implements Serializable{

    private int id;
    private String sTCD;
    private String pumpNum;
    private String pumpName;
    private String pumpHouseNum;
    private String pumpStatus; //状态
    private float pressure;//压力
    private float insFlow;//瞬时流量
    private float cumFlow;//累计水量
    private float elecurrent;
    private float voltage;
    private float electricity;
    private float level1;
    private float level2;
    private String times;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsTCD() {
        return sTCD;
    }

    public void setsTCD(String sTCD) {
        this.sTCD = sTCD;
    }

    public String getPumpNum() {
        return pumpNum;
    }

    public void setPumpNum(String pumpNum) {
        this.pumpNum = pumpNum;
    }

    public String getPumpName() {
        return pumpName;
    }

    public void setPumpName(String pumpName) {
        this.pumpName = pumpName;
    }

    public String getPumpHouseNum() {
        return pumpHouseNum;
    }

    public void setPumpHouseNum(String pumpHouseNum) {
        this.pumpHouseNum = pumpHouseNum;
    }

    public String getPumpStatus() {
        return pumpStatus;
    }

    public void setPumpStatus(String pumpStatus) {
        this.pumpStatus = pumpStatus;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getInsFlow() {
        return insFlow;
    }

    public void setInsFlow(float insFlow) {
        this.insFlow = insFlow;
    }

    public float getCumFlow() {
        return cumFlow;
    }

    public void setCumFlow(float cumFlow) {
        this.cumFlow = cumFlow;
    }

    public float getElecurrent() {
        return elecurrent;
    }

    public void setElecurrent(float elecurrent) {
        this.elecurrent = elecurrent;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public float getElectricity() {
        return electricity;
    }

    public void setElectricity(float electricity) {
        this.electricity = electricity;
    }

    public float getLevel1() {
        return level1;
    }

    public void setLevel1(float level1) {
        this.level1 = level1;
    }

    public float getLevel2() {
        return level2;
    }

    public void setLevel2(float level2) {
        this.level2 = level2;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "PumpMonitorList{" +
                "id=" + id +
                ", sTCD='" + sTCD + '\'' +
                ", pumpNum='" + pumpNum + '\'' +
                ", pumpName='" + pumpName + '\'' +
                ", pumpHouseNum='" + pumpHouseNum + '\'' +
                ", pumpStatus='" + pumpStatus + '\'' +
                ", pressure=" + pressure +
                ", insFlow=" + insFlow +
                ", cumFlow=" + cumFlow +
                ", elecurrent=" + elecurrent +
                ", voltage=" + voltage +
                ", electricity=" + electricity +
                ", level1=" + level1 +
                ", level2=" + level2 +
                ", times='" + times + '\'' +
                '}';
    }
}
