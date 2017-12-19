package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.IrrigationTurnInfo;
import com.xsyj.irrigation.entity.TapInfo;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by xushuaijun on 2015/11/26.
 */
public class UnBindTapsBiz extends AbstractNetBiz {


    private Context context;
    private NetCallBack<List<TapInfo>> netcallback;

    public UnBindTapsBiz(){
        super();

    }


    /**
     * 登录
     * @param context    上下文
     * @param netcallback  网络回调实例
     */
    public void getinfo(Context context,String wellNum,String turnId, String gprs, NetCallBack<List<TapInfo>> netcallback){

        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.unbind_taps);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("pumpnum", wellNum);
        params.addBodyParameter("turnId", turnId);
        params.addBodyParameter("gprs", gprs);
  /*      RequestParams params=new RequestParams("http://lifeplus.applinzi.com/exercise/statistics");
        params.addBodyParameter("page", "1");
        params.addBodyParameter("limit", "20");
        params.addHeader("token","39f3c878b37f769e357457cb64c9e09a18801158487");*/
        httpbiz.connectNet(HttpBiz.POST,params);
    }

    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("未绑定的阀口列表信息", result);

        if(!TextUtils.isEmpty(url) && Const.unbind_taps.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){



           // 获取用户类型
            Type type= new TypeToken<CommonData<List<TapInfo>>>(){}.getType();

            LogUtil.e("未绑定的阀口列表信息", "解析前");
            CommonData<List<TapInfo>> datamessage = JsonParserUtil.getObject(result, type);
           

            LogUtil.e("未绑定的阀口列表信息", "解析后"+(datamessage.toString()));
            //判断解析数据是否为空
            if(datamessage!=null){
                switch (datamessage.getS()){
                    case 200:  // 返回成功

                        if(netcallback!=null){
                            netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,datamessage.getData());
                        }

                        break;
                    case 500:  // 返回失败
                        if(netcallback!=null){
                            netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
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
