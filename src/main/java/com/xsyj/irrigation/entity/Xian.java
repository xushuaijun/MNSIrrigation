package com.xsyj.irrigation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 2016/9/13.
 */
public class Xian implements Serializable{

    private static final long serialVersionUID = 1L;
    private String Coun_No;//地区编号
    private String Coun_Name;//地区名称

    private List<Xiang> xiangList;

    public Xian() {
        super();
    }

    public Xian(String coun_No, String coun_Name) {
        Coun_No = coun_No;
        Coun_Name = coun_Name;
    }

    public String getCoun_No() {
        return Coun_No;
    }

    public void setCoun_No(String coun_No) {
        Coun_No = coun_No;
    }

    public String getCoun_Name() {
        return Coun_Name;
    }

    public void setCoun_Name(String coun_Name) {
        Coun_Name = coun_Name;
    }

    public List<Xiang> getXiangList() {
        return xiangList;
    }

    public void setXiangList(List<Xiang> xiangList) {
        this.xiangList = xiangList;
    }

    @Override
    public String toString() {
        return "Xian{" +
                "Coun_No='" + Coun_No + '\'' +
                ", Coun_Name='" + Coun_Name + '\'' +
                '}';
    }
}
