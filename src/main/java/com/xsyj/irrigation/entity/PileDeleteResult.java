package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/5/19.
 */

public class PileDeleteResult implements Serializable{
    private boolean success;
    private String msg;
    private boolean error;
    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "PileDeleteResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", error=" + error +
                ", result=" + result +
                '}';
    }
}
