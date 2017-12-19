package com.xsyj.irrigation.interfaces;

/**
 * Created by Administrator on 2015/11/26.
 */


/**
 * 联网回调
 * @param <T>  具体解析对象
 */
public interface NetCallBack<T>{

    /**
     * 联网成功，有返回，并且数据正确
     */
       int NET_SUCCESS =100 ;
    /**
     * 联网成功，有返回，但是数据不正确
     */
       int NET_FAILUED =99 ;
    /**
     * 联网失败，出现错误
     */
       int NET_ERROR =98 ;
    /**
     * 联网被取消
     */
       int NET_CANCELLED =97 ;
    /**
     * 联网结束
     */
       int NET_FINISHED =96 ;
    /**
     * 轮灌组周期中没有选择机井
     */
    int NET_TOAST =9 ;

    /**
     * 联网成功，有返回，并且数据正确 对象
     * @param statu   联网状态
     * @param url     联网地址
     * @param t       解析返回具体类型 对象时
     */
    void getNetSuccess(int statu, String url, T t);


    /**
     * 联网成功，有返回，但是数据错误
     * @param statu   联网状态
     * @param url     联网地址
     */
    void getNetFauiled(int statu, String url);

    /**
     * 联网被取消
     * @param statu   联网状态
     * @param url    联网地址
     */
    void getNetCanceled(int statu, String url);

    /**
     * 联网出现错误
     * @param statu  联网状态
     * @param url    联网地址
     */
    void getNetError(int statu, String url);
    /**
     * 联网结束
     * @param statu  联网状态
     * @param url    联网地址
     */
    void getNetFinished(int statu, String url);
}