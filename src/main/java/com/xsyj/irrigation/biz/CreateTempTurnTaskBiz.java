package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.User;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;


/**
 * Created by xushuaijun on 2015/11/26.
 */
public class CreateTempTurnTaskBiz extends AbstractNetBiz {


    private Context context;
    private NetCallBack<Integer> netcallback;

    public CreateTempTurnTaskBiz(){
        super();

    }


    /**
     * 控制轮灌组开关
     * @param context    上下文
     * @param turnid   轮灌组id
     * @param dowhat    开关指令
     * @param netcallback  网络回调实例
     */
    public void control(Context context,String turnid,String dowhat,NetCallBack<Integer> netcallback){


        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.turn_switch);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("areaCode", MyApplication.mApplication.getAreaCode());
        params.addBodyParameter("areaLevel", MyApplication.mApplication.getAreaLevel());
        params.addBodyParameter("turnid",turnid);
        params.addBodyParameter("dowhat", dowhat);

        httpbiz.connectNet(HttpBiz.POST,params);
    }

    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("控制轮灌组", result);

        if(!TextUtils.isEmpty(url) && Const.turn_switch.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){
            int res=0;
            try{
                res=Integer.valueOf(result);
            }catch (Exception e){
                LogUtil.e("控制轮灌组结果为非数字",result);
            }
            netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,res);

        }else{
            LogUtil.e("控制轮灌组结果为空",result);
        }
    }

    @Override
    public void netCanceled(String url, Callback.CancelledException e) {
        ToastUtil.toast(context, "控制轮灌组失败");
        if(netcallback!=null){
            netcallback.getNetCanceled(NetCallBack.NET_CANCELLED,url);
        }
    }

    @Override
    public void netError(String url, Throwable ex, boolean b) {
        ToastUtil.toast(context, "控制轮灌组失败");
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
