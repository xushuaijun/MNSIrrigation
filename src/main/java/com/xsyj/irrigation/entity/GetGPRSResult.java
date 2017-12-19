package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/5/22.
 */

public class GetGPRSResult implements Serializable{
    private int id;
    private String gprs;
    private String gprsName;
    private String ip;
    private int  port;
    private String userId;
    private String userName;
    private String areaId;
    private String areaLevel;
    private String start_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGprs() {
        return gprs;
    }

    public void setGprs(String gprs) {
        this.gprs = gprs;
    }

    public String getGprsName() {
        return gprsName;
    }

    public void setGprsName(String gprsName) {
        this.gprsName = gprsName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    @Override
    public String toString() {
        return "GetGPRSResult{" +
                "id=" + id +
                ", gprs='" + gprs + '\'' +
                ", gprsName='" + gprsName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaLevel='" + areaLevel + '\'' +
                ", start_id='" + start_id + '\'' +
                '}';
    }
}
