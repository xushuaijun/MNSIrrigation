package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

/**
 * Created by Lenovo on 2016/12/6.
 */
public class SMBDWGBiz extends AbstractNetBiz{

    private Context context;
    private NetCallBack<Integer> netcallback;

    public SMBDWGBiz(){
        super();

    }

    public void getInfo(Context context,String singleTasks,NetCallBack<Integer> netcallback){
        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.SM_BDWG);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        LogUtil.e("singleTasks:", singleTasks);
        params.setBodyContent(singleTasks);
        httpbiz.connectNet(HttpBiz.POST, params);
    }
    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("绑定网关", result);
        if(!TextUtils.isEmpty(url) && Const.SM_BDWG.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)) {
            int res=0;
            try{
                res=Integer.valueOf(result);
            }catch (Exception e){
                LogUtil.e("绑定网关",result);
            }
            netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,res);

        }else{
            LogUtil.e("网关未绑定",result);
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
