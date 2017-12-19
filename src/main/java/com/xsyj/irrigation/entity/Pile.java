package com.xsyj.irrigation.entity;


import java.io.Serializable;

/**
 * Created by Lenovo on 2016/11/1.
 */
public class Pile implements Serializable{


    private int id;           //标识id
    private String pilenum;   //出地桩编号
    private String userid;    //用户id
    private int landid;       //地块id
    private String pilename;  //出地桩名称
    private String landName;  //地块名称
    private String userName;

    public Pile(int id, String pilenum, String userid, int landid, String pilename, String landName, String userName) {
        this.id = id;
        this.pilenum = pilenum;
        this.userid = userid;
        this.landid = landid;
        this.pilename = pilename;
        this.landName = landName;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPilenum() {
        return pilenum;
    }

    public void setPilenum(String pilenum) {
        this.pilenum = pilenum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getLandid() {
        return landid;
    }

    public void setLandid(int landid) {
        this.landid = landid;
    }

    public String getPilename() {
        return pilename;
    }

    public void setPilename(String pilename) {
        this.pilename = pilename;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "Pile{" +
                "id=" + id +
                ", pilenum='" + pilenum + '\'' +
                ", userid=" + userid +
                ", landid=" + landid +
                ", pilename='" + pilename + '\'' +
                ", landName='" + landName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
