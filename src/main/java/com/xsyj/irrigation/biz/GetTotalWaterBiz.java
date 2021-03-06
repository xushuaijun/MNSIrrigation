package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.GetTotalWater;
import com.xsyj.irrigation.entity.IrrigationTurnInfo;
import com.xsyj.irrigation.entity.Pile;
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

public class GetTotalWaterBiz extends AbstractNetBiz {
    private Context context;
    private NetCallBack<CommonData<GetTotalWater>> netcallback;

    public GetTotalWaterBiz(){
        super();

    }

    public void getInfo(Context context,NetCallBack<CommonData<GetTotalWater>> netcallback){
        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.total_water);
        params.addQueryStringParameter("token", MyApplication.mApplication.getToken());
        params.addQueryStringParameter("areaId", MyApplication.mApplication.getAreaId());
        params.addQueryStringParameter("areaLevel",MyApplication.mApplication.getAreaLevel());
//        params.addQueryStringParameter("areaCode",MyApplication.mApplication.getAreaCode());
        httpbiz.connectNet(HttpBiz.GET, params);
    }



    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("绑定网关", result);

        // 获取用户类型
        Type type= new TypeToken<CommonData<GetTotalWater>>(){}.getType();
        LogUtil.e("出地桩基本信息列表", "解析前");
        CommonData<GetTotalWater> datamessage = JsonParserUtil.getObject(result, type);
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
//       if (!TextUtils.isEmpty(result)){
//           GetTotalWater getTotalWater = gson.fromJson(result,GetTotalWater.class);
//           if(netcallback!=null){
//               netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,datamessage);
//           }
//       }else{
//           if(netcallback!=null){
//               netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
//           }
//       }


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
