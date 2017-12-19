package com.xsyj.irrigation.biz;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.SoilMoistureList;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Lenovo on 2017/5/8.
 */

public class SoilMoistureDetailBiz extends AbstractNetBiz {
    private Context context;
    private NetCallBack<CommonData<SoilMoistureList>> netcallback;

    public SoilMoistureDetailBiz(){
        super();

    }

    public void getSoilMoisDetail(Context context, String STCD,
                            NetCallBack<CommonData<SoilMoistureList>> netcallback){
        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.soilmoisture_details);
        params.addQueryStringParameter("token", MyApplication.mApplication.getToken());
        params.addQueryStringParameter("STCD",STCD);
        httpbiz.connectNet(HttpBiz.GET, params);
    }



    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("绑定网关", result);
        Type type= new TypeToken<CommonData<SoilMoistureList>>(){}.getType();
        LogUtil.e("出地桩基本信息列表", "解析前");
        CommonData<SoilMoistureList> datamessage = JsonParserUtil.getObject(result, type);
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
        }


    }

    @Override
    public void netCanceled(String url, Callback.CancelledException e) {

    }

    @Override
    public void netError(String url, Throwable ex, boolean b) {

    }

    @Override
    public void netFinished(String url) {

    }
}
