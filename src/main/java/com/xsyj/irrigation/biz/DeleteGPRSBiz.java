package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;

/**
 * Created by Lenovo on 2017/5/19.
 */

public class DeleteGPRSBiz extends AbstractNetBiz {


    private Context context;
    private NetCallBack<PileDeleteResult> netcallback;

    public DeleteGPRSBiz(){
        super();

    }

    public void delGprs(Context context,String id ,
                             NetCallBack<PileDeleteResult> netcallback){
        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.gprs_delete);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("id",id);
        httpbiz.connectNet(HttpBiz.POST, params);
    }
    @Override
    public void netSuccess(String url, String result) {
        if(!TextUtils.isEmpty(url) && Const.gprs_delete.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)) {

            // 获取用户类型
            Type type = new TypeToken<PileDeleteResult>() {
            }.getType();
            LogUtil.e("删除GPRS", "解析前");
            PileDeleteResult datamessage = JsonParserUtil.getObject(result, type);

            LogUtil.e("删除GPRS", "解析后" + (datamessage.toString()));
            netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,datamessage);
        }else{
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
