package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/8/25.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String areaCode;
    private String token;//用户唯一标识
    private String areaId; // 用户区域id
    private String areaLevel;// 用户级别
    private String type;
    private String userName;


    public User(){
        super();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public User(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "areaCode='" + areaCode + '\'' +
                ", token='" + token + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaLevel='" + areaLevel + '\'' +
                ", type='" + type + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
