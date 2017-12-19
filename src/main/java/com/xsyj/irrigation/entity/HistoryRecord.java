package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/5/23.
 */

public class HistoryRecord implements Serializable{
    private int id;
    private String sTCD;
    private String pumpNum;
    private String pumpName;
    private String pumpHouseNum;
    private String pumpStatus;
    private double  pressure;
    private double insFlow;
    private int cumFlow;
    private double elecurrent;
    private double voltage;
    private double electricity;
    private double level1;
    private double level2;
    private String times;
    private String start_id;

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

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getInsFlow() {
        return insFlow;
    }

    public void setInsFlow(double insFlow) {
        this.insFlow = insFlow;
    }

    public int getCumFlow() {
        return cumFlow;
    }

    public void setCumFlow(int cumFlow) {
        this.cumFlow = cumFlow;
    }

    public double getElecurrent() {
        return elecurrent;
    }

    public void setElecurrent(double elecurrent) {
        this.elecurrent = elecurrent;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getLevel1() {
        return level1;
    }

    public void setLevel1(double level1) {
        this.level1 = level1;
    }

    public double getLevel2() {
        return level2;
    }

    public void setLevel2(double level2) {
        this.level2 = level2;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    @Override
    public String toString() {
        return "HistoryRecord{" +
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
                ", start_id='" + start_id + '\'' +
                '}';
    }
}
