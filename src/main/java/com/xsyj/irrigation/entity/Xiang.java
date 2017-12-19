package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/9/13.
 */
public class Xiang  implements Serializable{

    private static final long serialVersionUID = 1L;
    private String Town_No;//地区编号
    private String Town_Name;//地区名称

    public Xiang() {
        super();
    }

    public Xiang(String town_No, String town_Name) {
        Town_No = town_No;
        Town_Name = town_Name;
    }

    public String getTown_No() {
        return Town_No;
    }

    public void setTown_No(String town_No) {
        Town_No = town_No;
    }

    public String getTown_Name() {
        return Town_Name;
    }

    public void setTown_Name(String town_Name) {
        Town_Name = town_Name;
    }

    @Override
    public String toString() {
        return "Xiang{" +
                "Town_No='" + Town_No + '\'' +
                ", Town_Name='" + Town_Name + '\'' +
                '}';
    }
}
