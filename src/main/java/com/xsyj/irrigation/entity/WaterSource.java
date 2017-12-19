package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/5/22.
 */

public class WaterSource implements Serializable {
    private int id;
    private String pumphousenum;
    private String userid;
    private String areaId;
    private String areaLevel;
    private String pumphousename;
    private String latitude;
    private String longtitude;
    private String userName;
    private String areaName;
    private String start_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPumphousenum() {
        return pumphousenum;
    }

    public void setPumphousenum(String pumphousenum) {
        this.pumphousenum = pumphousenum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getPumphousename() {
        return pumphousename;
    }

    public void setPumphousename(String pumphousename) {
        this.pumphousename = pumphousename;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    @Override
    public String toString() {
        return "WaterSource{" +
                "id=" + id +
                ", pumphousenum='" + pumphousenum + '\'' +
                ", userid='" + userid + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaLevel='" + areaLevel + '\'' +
                ", pumphousename='" + pumphousename + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longtitude='" + longtitude + '\'' +
                ", userName='" + userName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", start_id='" + start_id + '\'' +
                '}';
    }
}
