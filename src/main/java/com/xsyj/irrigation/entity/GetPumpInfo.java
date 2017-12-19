package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/5/15.
 */

public class GetPumpInfo implements Serializable{

    private String STCD;
    private String InDate;
    private String SystemStatus;
    private String PumpStatus;
    private String StatusA;
    private String StatusB;
    private String VoltageA;
    private String VoltageB;
    private String VoltageC;
    private String CurrentA;
    private String CurrentB;
    private String CurrentC;
    private String LJSL;
    private String LJDL;
    private String SSLL;
    private String YSXL;
    private String PH;
    private String EC;
    private String PressureA;
    private String PressureB;
    private String SystemAStatus;
    private String SWA;
    private String EntranceA;
    private String ExitA;
    private String SWALow;
    private String SWAHigh;
    private String SystemBStatus;
    private String SWB;
    private String EntranceB;
    private String ExitB;
    private String SWBLow;
    private String SWBHigh;

    public String getSTCD() {
        return STCD;
    }

    public void setSTCD(String STCD) {
        this.STCD = STCD;
    }

    public String getInDate() {
        return InDate;
    }

    public void setInDate(String inDate) {
        InDate = inDate;
    }

    public String getSystemStatus() {
        return SystemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        SystemStatus = systemStatus;
    }

    public String getPumpStatus() {
        return PumpStatus;
    }

    public void setPumpStatus(String pumpStatus) {
        PumpStatus = pumpStatus;
    }

    public String getStatusA() {
        return StatusA;
    }

    public void setStatusA(String statusA) {
        StatusA = statusA;
    }

    public String getStatusB() {
        return StatusB;
    }

    public void setStatusB(String statusB) {
        StatusB = statusB;
    }

    public String getVoltageA() {
        return VoltageA;
    }

    public void setVoltageA(String voltageA) {
        VoltageA = voltageA;
    }

    public String getVoltageB() {
        return VoltageB;
    }

    public void setVoltageB(String voltageB) {
        VoltageB = voltageB;
    }

    public String getVoltageC() {
        return VoltageC;
    }

    public void setVoltageC(String voltageC) {
        VoltageC = voltageC;
    }

    public String getCurrentA() {
        return CurrentA;
    }

    public void setCurrentA(String currentA) {
        CurrentA = currentA;
    }

    public String getCurrentB() {
        return CurrentB;
    }

    public void setCurrentB(String currentB) {
        CurrentB = currentB;
    }

    public String getCurrentC() {
        return CurrentC;
    }

    public void setCurrentC(String currentC) {
        CurrentC = currentC;
    }

    public String getLJSL() {
        return LJSL;
    }

    public void setLJSL(String LJSL) {
        this.LJSL = LJSL;
    }

    public String getLJDL() {
        return LJDL;
    }

    public void setLJDL(String LJDL) {
        this.LJDL = LJDL;
    }

    public String getSSLL() {
        return SSLL;
    }

    public void setSSLL(String SSLL) {
        this.SSLL = SSLL;
    }

    public String getYSXL() {
        return YSXL;
    }

    public void setYSXL(String YSXL) {
        this.YSXL = YSXL;
    }

    public String getPH() {
        return PH;
    }

    public void setPH(String PH) {
        this.PH = PH;
    }

    public String getEC() {
        return EC;
    }

    public void setEC(String EC) {
        this.EC = EC;
    }

    public String getPressureA() {
        return PressureA;
    }

    public void setPressureA(String pressureA) {
        PressureA = pressureA;
    }

    public String getPressureB() {
        return PressureB;
    }

    public void setPressureB(String pressureB) {
        PressureB = pressureB;
    }

    public String getSystemAStatus() {
        return SystemAStatus;
    }

    public void setSystemAStatus(String systemAStatus) {
        SystemAStatus = systemAStatus;
    }

    public String getSWA() {
        return SWA;
    }

    public void setSWA(String SWA) {
        this.SWA = SWA;
    }

    public String getEntranceA() {
        return EntranceA;
    }

    public void setEntranceA(String entranceA) {
        EntranceA = entranceA;
    }

    public String getExitA() {
        return ExitA;
    }

    public void setExitA(String exitA) {
        ExitA = exitA;
    }

    public String getSWALow() {
        return SWALow;
    }

    public void setSWALow(String SWALow) {
        this.SWALow = SWALow;
    }

    public String getSWAHigh() {
        return SWAHigh;
    }

    public void setSWAHigh(String SWAHigh) {
        this.SWAHigh = SWAHigh;
    }

    public String getSystemBStatus() {
        return SystemBStatus;
    }

    public void setSystemBStatus(String systemBStatus) {
        SystemBStatus = systemBStatus;
    }

    public String getSWB() {
        return SWB;
    }

    public void setSWB(String SWB) {
        this.SWB = SWB;
    }

    public String getEntranceB() {
        return EntranceB;
    }

    public void setEntranceB(String entranceB) {
        EntranceB = entranceB;
    }

    public String getExitB() {
        return ExitB;
    }

    public void setExitB(String exitB) {
        ExitB = exitB;
    }

    public String getSWBLow() {
        return SWBLow;
    }

    public void setSWBLow(String SWBLow) {
        this.SWBLow = SWBLow;
    }

    public String getSWBHigh() {
        return SWBHigh;
    }

    public void setSWBHigh(String SWBHigh) {
        this.SWBHigh = SWBHigh;
    }

    @Override
    public String toString() {
        return "GetPumpInfo{" +
                "STCD='" + STCD + '\'' +
                ", InDate='" + InDate + '\'' +
                ", SystemStatus='" + SystemStatus + '\'' +
                ", PumpStatus='" + PumpStatus + '\'' +
                ", StatusA='" + StatusA + '\'' +
                ", StatusB='" + StatusB + '\'' +
                ", VoltageA='" + VoltageA + '\'' +
                ", VoltageB='" + VoltageB + '\'' +
                ", VoltageC='" + VoltageC + '\'' +
                ", CurrentA='" + CurrentA + '\'' +
                ", CurrentB='" + CurrentB + '\'' +
                ", CurrentC='" + CurrentC + '\'' +
                ", LJSL='" + LJSL + '\'' +
                ", LJDL='" + LJDL + '\'' +
                ", SSLL='" + SSLL + '\'' +
                ", YSXL='" + YSXL + '\'' +
                ", PH='" + PH + '\'' +
                ", EC='" + EC + '\'' +
                ", PressureA='" + PressureA + '\'' +
                ", PressureB='" + PressureB + '\'' +
                ", SystemAStatus='" + SystemAStatus + '\'' +
                ", SWA='" + SWA + '\'' +
                ", EntranceA='" + EntranceA + '\'' +
                ", ExitA='" + ExitA + '\'' +
                ", SWALow='" + SWALow + '\'' +
                ", SWAHigh='" + SWAHigh + '\'' +
                ", SystemBStatus='" + SystemBStatus + '\'' +
                ", SWB='" + SWB + '\'' +
                ", EntranceB='" + EntranceB + '\'' +
                ", ExitB='" + ExitB + '\'' +
                ", SWBLow='" + SWBLow + '\'' +
                ", SWBHigh='" + SWBHigh + '\'' +
                '}';
    }
}
