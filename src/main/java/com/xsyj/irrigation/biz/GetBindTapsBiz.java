package com.xsyj.irrigation.biz;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.GetBindTaps;
import com.xsyj.irrigation.entity.PumpHouseList;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Lenovo on 2017/5/12.
 */

public class GetBindTapsBiz extends AbstractNetBiz {

    private Context context;
    private NetCallBack<CommonData<List<GetBindTaps>>> netCallBack;

    public GetBindTapsBiz(){
        super();

    }

    public void getBindTaps(Context context, String pumpnum, String gprs, NetCallBack<CommonData<List<GetBindTaps>>> netCallBack){
        this.context=context;
        this.netCallBack = netCallBack;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.get_bindTaps);
        String token = MyApplication.mApplication.getToken();
        String areaLevel = MyApplication.mApplication.getAreaLevel();
        String areaId = MyApplication.mApplication.getAreaId();
        params.addBodyParameter("areaLevel", areaLevel);
        params.addBodyParameter("areaId", areaId);
        params.addBodyParameter("token", token);
        params.addBodyParameter("pumpnum", pumpnum);
        params.addBodyParameter("gprs", gprs);
        params.addBodyParameter("turnId",null);

        httpbiz.connectNet(HttpBiz.POST, params);
    }
    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("result",result);
        Type type= new TypeToken<CommonData<List<GetBindTaps>>>(){}.getType();
        LogUtil.e("出地桩基本信息列表", "解析前");
        CommonData<List<GetBindTaps>> datamessage = JsonParserUtil.getObject(result, type);
        if (datamessage != null){
            switch (datamessage.getS()){
                case 200:
                    netCallBack.getNetSuccess(NetCallBack.NET_SUCCESS,url,datamessage);
                    break;
                case 500:
                    netCallBack.getNetFauiled(NetCallBack.NET_FAILUED, url);
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
