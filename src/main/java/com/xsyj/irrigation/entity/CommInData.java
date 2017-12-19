package com.xsyj.irrigation.entity;

import java.io.Serializable;

public class CommInData<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private T list;
    private String msg;
    private int s;

    public CommInData() {
        super();
        // TODO Auto-generated constructor stub
    }


    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "CommInData{" +
                "list=" + list +
                ", msg='" + msg + '\'' +
                ", s=" + s +
                '}';
    }
}
