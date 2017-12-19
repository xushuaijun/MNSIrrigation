package com.xsyj.irrigation.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/5.
 */
public class AppVersion implements Serializable {

    private int id;
    private int versionCode; // 版本号
    private String versionName; // 版本名称
    private String msg;  // 更新信息
    private String url; // 版本地址
    private String data;

    public AppVersion() {
    }

    public AppVersion(int versionCode, String versionName, String msg, String url) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.msg = msg;
        this.url = url;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "id=" + id +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", msg='" + msg + '\'' +
                ", url='" + url + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
