package com.xsyj.irrigation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xmh on 2017/7/19.
 */

public class IrrControlList implements Serializable{
    private String irrName;
    private boolean isAll;
    private int status;
    private List<IrrControlList> lists;

    public IrrControlList(String irrName, boolean isAll, int status, List<IrrControlList> lists){
        this.irrName = irrName;
        this.isAll = isAll;
        this.status = status;
        this.lists = lists;
    }

    public String getIrrName() {
        return irrName;
    }

    public void setIrrName(String irrName) {
        this.irrName = irrName;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<IrrControlList> getLists() {
        return lists;
    }

    public void setLists(List<IrrControlList> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "IrrControlList{" +
                "irrName='" + irrName + '\'' +
                ", isAll=" + isAll +
                ", lists=" + lists +
                '}';
    }
}
