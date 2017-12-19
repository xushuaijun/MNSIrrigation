package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.CommInData;
import com.xsyj.irrigation.entity.CommonDataE;
import com.xsyj.irrigation.entity.GetPumpInfo;
import com.xsyj.irrigation.entity.Shi;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.SharePrefrenceUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

//import com.xsyj.myapplication.cons.Const;
//import com.xsyj.myapplication.entity.CommInData;
//import com.xsyj.myapplication.entity.CommonDataE;
//import com.xsyj.myapplication.entity.Shi;
//import com.xsyj.myapplication.utils.JsonParserUtil;
//import com.xsyj.myapplication.utils.LogUtil;
//import com.xsyj.myapplication.utils.SharePrefrenceUtil;
//import com.xsyj.myapplication.utils.ToastUtil;

/**
 * Created by Lenovo on 2016/9/25.
 */
public class PumpInfoBiz extends AbstractNetBiz {

    private Context context;
    private NetCallBack<CommonData<List<GetPumpInfo>>> netcallback;

    public PumpInfoBiz() {
    }

    public void getpumpinfo(Context context,String stcd,NetCallBack<CommonData<List<GetPumpInfo>>> netcallback){
        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.pump_info);

        params.addQueryStringParameter("stcd", "10111187");


        httpbiz.connectNet(HttpBiz.GET,params);
    }

    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("水源首部新协议数据", result);
        if(!TextUtils.isEmpty(url) && Const.pump_info.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){
            // 获取用户类型
            Type type= new TypeToken<CommonData<List<GetPumpInfo>>>(){}.getType();
            // 根据类型返回实体类
            CommonData<List<GetPumpInfo>>  datamessage= JsonParserUtil.getObject(result, type);

            //判断解析数据是否为空
            if(datamessage!=null){
                switch (datamessage.getS()){
                    case 200:  // 返回成功

                        if(netcallback!=null){
                            netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,datamessage);
                        }

                        break;
                    case 500:  // 返回失败
                        if(netcallback!=null){
                            netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
                        }

                        break;
                    default:
                        ToastUtil.toast(context, "返回市列表");
                        LogUtil.e("返回市列表","既不是500，也不是200");
                        break;
                }
            }else{
                LogUtil.e("返回市列表解析失败",result);
            }
        }else{
            LogUtil.e("返回市列表结果为空",result);
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
