package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.entity.PumpMonitorList;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;

/**
 * Created by Lenovo on 2017/5/19.
 */

public class PumpControlBiz extends AbstractNetBiz {


    private Context context;
    private NetCallBack<Integer> netcallback;

    public PumpControlBiz(){
        super();

    }

    public void pumpControl(Context context,String pumpTask ,
                             NetCallBack<Integer> netcallback){
        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.pumpControl);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("userId", MyApplication.mApplication.getToken());
        params.setBodyContent(pumpTask);
        httpbiz.connectNet(HttpBiz.POST, params);
    }
    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("result",result);
        if ("0".equals(result)) {
            ToastUtil.toast(context,"操作失败!");
            if(netcallback!=null){
                netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
            }
        } else if ("1".equals(result)) {
            ToastUtil.toast(context,"任务正在后台操作!");
            if(netcallback!=null){
                netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,null);
            }
        }else if (result.equals("-4")){
            ToastUtil.toast(context,"操作失败,网关不在线!");
            if(netcallback!=null){
                netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
            }
        }else{
            ToastUtil.toast(context,"出现异常!");
            if(netcallback!=null){
                netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
            }
        }
    }

    @Override
    public void netCanceled(String url, Callback.CancelledException e) {
        if(netcallback!=null){
            netcallback.getNetCanceled(NetCallBack.NET_CANCELLED,url);
        }
    }

    @Override
    public void netError(String url, Throwable ex, boolean b) {
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
