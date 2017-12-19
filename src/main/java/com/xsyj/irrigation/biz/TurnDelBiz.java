package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;


/**
 * Created by xushuaijun on 2015/11/26.
 */
public class TurnDelBiz extends AbstractNetBiz {


    private Context context;
    private NetCallBack<Integer> netcallback;

    public TurnDelBiz(){
        super();

    }


    /**
     * 控制轮灌组开关
     * @param context    上下文
     * @param Name   将要被删除的轮灌组信息
     * @param netcallback  网络回调实例
     */
    public void control(Context context,String Name,NetCallBack<Integer> netcallback){


        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.turn_del);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("Name",Name);
        httpbiz.connectNet(HttpBiz.POST,params);
    }

    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("删除轮灌组", result);

        if(!TextUtils.isEmpty(url) && Const.turn_del.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){
            int res=0;
            try{
                res=Integer.valueOf(result);
            }catch (Exception e){
                LogUtil.e("删除轮灌组结果为非数字",result);
            }
            netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,res);

        }else{
            LogUtil.e("删除轮灌组结果为空",result);
        }
    }

    @Override
    public void netCanceled(String url, Callback.CancelledException e) {
        ToastUtil.toast(context, "删除轮灌组被取消");
        if(netcallback!=null){
            netcallback.getNetCanceled(NetCallBack.NET_CANCELLED,url);
        }
    }

    @Override
    public void netError(String url, Throwable ex, boolean b) {
        LogUtil.e("删除轮灌组",ex.getMessage());
        ToastUtil.toast(context, "删除轮灌组异常"+ex.getMessage());
        if(netcallback!=null){
            netcallback.getNetError(NetCallBack.NET_ERROR,url);
        }
    }

    @Override
    public void netFinished(String url) {
        if(netcallback!=null){
            netcallback.getNetFinished(NetCallBack.NET_FINISHED,url);
        }
    }


}
