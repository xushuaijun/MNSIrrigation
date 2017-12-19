package com.xsyj.irrigation.entity;


import java.io.Serializable;

/**
 * Created by Lenovo on 2016/11/1.
 */
public class Land implements Serializable{

    private int id;               //标识id
    private String landnum;       //地块编号
    private String landname;      //地块名称
    private double landarea;      //地块面积
    private double irrigations;   //灌溉定额
    private String landlocal;     //所属地方
    private String userid;        //用户id
    private String cropid;        //作物id
    private int landid;           //地块id
    private String cropName;      //作物名称
    private String userName;      //地块拥有者
    private String cropintime;    //种植时间
    private String addtime;       //录入时间

    public Land(int id, String landnum, String landname, double landarea, double irrigations, String landlocal, String userid, String cropid, int landid, String cropName, String userName, String cropintime, String addtime) {
        this.id = id;
        this.landnum = landnum;
        this.landname = landname;
        this.landarea = landarea;
        this.irrigations = irrigations;
        this.landlocal = landlocal;
        this.userid = userid;
        this.cropid = cropid;
        this.landid = landid;
        this.cropName = cropName;
        this.userName = userName;
        this.cropintime = cropintime;
        this.addtime = addtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLandnum() {
        return landnum;
    }

    public void setLandnum(String landnum) {
        this.landnum = landnum;
    }

    public String getLandname() {
        return landname;
    }

    public void setLandname(String landname) {
        this.landname = landname;
    }

    public double getLandarea() {
        return landarea;
    }

    public void setLandarea(double landarea) {
        this.landarea = landarea;
    }

    public double getIrrigations() {
        return irrigations;
    }

    public void setIrrigations(double irrigations) {
        this.irrigations = irrigations;
    }

    public String getLandlocal() {
        return landlocal;
    }

    public void setLandlocal(String landlocal) {
        this.landlocal = landlocal;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCropid() {
        return cropid;
    }

    public void setCropid(String cropid) {
        this.cropid = cropid;
    }

    public int getLandid() {
        return landid;
    }

    public void setLandid(int landid) {
        this.landid = landid;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCropintime() {
        return cropintime;
    }

    public void setCropintime(String cropintime) {
        this.cropintime = cropintime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Land{" +
                "id=" + id +
                ", landnum='" + landnum + '\'' +
                ", landname='" + landname + '\'' +
                ", landarea=" + landarea +
                ", irrigations=" + irrigations +
                ", landlocal='" + landlocal + '\'' +
                ", userid='" + userid + '\'' +
                ", cropid='" + cropid + '\'' +
                ", landid=" + landid +
                ", cropName='" + cropName + '\'' +
                ", userName='" + userName + '\'' +
                ", cropintime='" + cropintime + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
