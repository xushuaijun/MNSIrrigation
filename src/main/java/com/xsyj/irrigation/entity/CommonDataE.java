package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/27.
 */
public class CommonDataE<T> implements Serializable {

    private T data;

    public CommonDataE() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonDataE{" +
                "data=" + data +
                '}';
    }
}
