package com.xsyj.irrigation.biz;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.AppVersion;
import com.xsyj.irrigation.entity.MyNodeBean;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Lenovo on 2017/5/11.
 */

public class SelectApkVersionBiz extends AbstractNetBiz {

    private Context context;
    private NetCallBack<CommonData<List<AppVersion>>> netcallback;

    public SelectApkVersionBiz(){
        super();

    }

    public void getApkVersion(Context context,NetCallBack<CommonData<List<AppVersion>>> netcallback){
        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.APKVERSION);
        String token = MyApplication.mApplication.getToken();
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("areaCode", MyApplication.mApplication.getAreaCode());
        httpbiz.connectNet(HttpBiz.POST, params);
    }

    @Override
    public void netSuccess(String url, String result) {
        Type type= new TypeToken<CommonData<List<AppVersion>>>(){}.getType();
        LogUtil.e("版本", "解析前");
        CommonData<List<AppVersion>> datamessage = JsonParserUtil.getObject(result, type);
        if (datamessage != null){
            switch (datamessage.getS()){
                case 200:
                    netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,datamessage);
                    break;
                case 500:
                    netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
                    break;
                default:break;
            }
        }else{
            netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
        }
    }

    @Override
    public void netCanceled(String url, Callback.CancelledException e) {

    }

    @Override
    public void netError(String url, Throwable ex, boolean b) {
        netcallback.getNetError(NetCallBack.NET_ERROR, url);
    }

    @Override
    public void netFinished(String url) {

    }
}
