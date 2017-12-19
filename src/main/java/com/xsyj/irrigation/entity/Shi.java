package com.xsyj.irrigation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 2016/9/13.
 */
public class Shi implements Serializable{

    private static final long serialVersionUID = 1L;
    private String Shi_No;//地区编码
    private String Shi_Name;//地区名称
    private List<Xian> xianList;

    public Shi() {
        super();
    }

    public Shi(String shi_No, String shi_Name) {
        Shi_No = shi_No;
        Shi_Name = shi_Name;
    }

    public String getShi_No() {
        return Shi_No;
    }

    public void setShi_No(String shi_No) {
        Shi_No = shi_No;
    }

    public String getShi_Name() {
        return Shi_Name;
    }

    public void setShi_Name(String shi_Name) {
        Shi_Name = shi_Name;
    }

    public List<Xian> getXianList() {
        return xianList;
    }

    public void setXianList(List<Xian> xianList) {
        this.xianList = xianList;
    }

    @Override
    public String toString() {
        return "Shi{" +
                "Shi_No='" + Shi_No + '\'' +
                ", Shi_Name='" + Shi_Name + '\'' +
                '}';
    }
}
