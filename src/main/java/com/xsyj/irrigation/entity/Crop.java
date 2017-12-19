package com.xsyj.irrigation.entity;


import java.io.Serializable;

/**
 * Created by Lenovo on 2016/11/1.
 */
public class Crop implements Serializable{

    private String cropid;     //作物id
    private Long theser;       //
    private String cropname;   //作物名称
    private double cropde;     //灌水定额
    private String latitude;   //纬度
    private double height;     //海拔高度
    private String addvcd;     //
    private String indate;     //种植时间
    private String cropShenhe; //审核
    private String res1;       //
    private String res2;       //
    private String userId;     //用户id

    public Crop(String cropid, Long theser, String cropname, double cropde, String latitude, double height, String addvcd, String indate, String cropShenhe, String res1, String res2, String userId) {
        this.cropid = cropid;
        this.theser = theser;
        this.cropname = cropname;
        this.cropde = cropde;
        this.latitude = latitude;
        this.height = height;
        this.addvcd = addvcd;
        this.indate = indate;
        this.cropShenhe = cropShenhe;
        this.res1 = res1;
        this.res2 = res2;
        this.userId = userId;
    }

    public String getCropid() {
        return cropid;
    }

    public void setCropid(String cropid) {
        this.cropid = cropid;
    }

    public Long getTheser() {
        return theser;
    }

    public void setTheser(Long theser) {
        this.theser = theser;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public double getCropde() {
        return cropde;
    }

    public void setCropde(double cropde) {
        this.cropde = cropde;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getAddvcd() {
        return addvcd;
    }

    public void setAddvcd(String addvcd) {
        this.addvcd = addvcd;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getCropShenhe() {
        return cropShenhe;
    }

    public void setCropShenhe(String cropShenhe) {
        this.cropShenhe = cropShenhe;
    }

    public String getRes1() {
        return res1;
    }

    public void setRes1(String res1) {
        this.res1 = res1;
    }

    public String getRes2() {
        return res2;
    }

    public void setRes2(String res2) {
        this.res2 = res2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Crop{" +
                "cropid='" + cropid + '\'' +
                ", theser=" + theser +
                ", cropname='" + cropname + '\'' +
                ", cropde=" + cropde +
                ", latitude='" + latitude + '\'' +
                ", height=" + height +
                ", addvcd='" + addvcd + '\'' +
                ", indate='" + indate + '\'' +
                ", cropShenhe='" + cropShenhe + '\'' +
                ", res1='" + res1 + '\'' +
                ", res2='" + res2 + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
