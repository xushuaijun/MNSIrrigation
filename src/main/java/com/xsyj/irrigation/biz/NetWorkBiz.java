package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.NetWork;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Lenovo on 2016/12/23.
 */
public class NetWorkBiz extends AbstractNetBiz{

    private Context context;
    private NetCallBack<CommonData<List<NetWork>>> netCallBack;

    public NetWorkBiz(){
        super();
    }

    public void getInfo(Context context,NetCallBack<CommonData<List<NetWork>>> netCallBack){
        this.context=context;
        this.netCallBack=netCallBack;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.DWCB);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("areaCode", MyApplication.mApplication.getAreaCode());
        params.addBodyParameter("areaLevel", MyApplication.mApplication.getAreaLevel());
        params.addBodyParameter("areaId", MyApplication.mApplication.getAreaId());
        LogUtil.e("TOKEN",MyApplication.mApplication.getToken());
        httpbiz.connectNet(HttpBiz.POST, params);
    }

    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("多网抄表", result);
        if(!TextUtils.isEmpty(url) && Const.DWCB.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){

            // 获取用户类型
            Type type= new TypeToken<CommonData<List<NetWork>>>(){}.getType();
            LogUtil.e("多网", "解析前");
            CommonData<List<NetWork>> datamessage = JsonParserUtil.getObject(result, type);

            LogUtil.e("多网", "解析后"+(datamessage.toString()));
            //判断解析数据是否为空
            if(datamessage!=null){
                switch (datamessage.getS()){
                    case 200:  // 返回成功

                        if(netCallBack!=null){
                            netCallBack.getNetSuccess(NetCallBack.NET_SUCCESS,url,datamessage);
                        }

                        break;
                    case 500:  // 返回失败
                        if(netCallBack!=null){
                            netCallBack.getNetFauiled(NetCallBack.NET_FAILUED, url);
                        }

                        break;
                    default:

                        break;
                }

            }else{
                LogUtil.e("返回解析失败",result);
            }
        }else{
            LogUtil.e("返回结果为空",result);
        }

    }


    @Override
    public void netCanceled(String url, Callback.CancelledException e) {
        if(netCallBack!=null){
            netCallBack.getNetCanceled(NetCallBack.NET_CANCELLED,url);
        }
    }

    @Override
    public void netError(String url, Throwable ex, boolean b) {
        if(netCallBack!=null){
            netCallBack.getNetFauiled(NetCallBack.NET_ERROR, url);
        }
    }

    @Override
    public void netFinished(String url) {
        if(netCallBack!=null){
            netCallBack.getNetFinished(NetCallBack.NET_FINISHED,url);
        }
    }

}
