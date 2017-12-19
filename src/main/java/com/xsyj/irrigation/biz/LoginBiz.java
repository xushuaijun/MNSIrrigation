package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;


import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
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
public class LoginBiz extends AbstractNetBiz {


    private Context context;
    private NetCallBack<User> netcallback;

    public LoginBiz(){
        super();

    }


    /**
     * 登录
     * @param context    上下文
     * @param username   用户名
     * @param password    密码
     * @param netcallback  网络回调实例
     */
    public void gotoLogin(Context context,String username,String password,NetCallBack<User> netcallback){

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
              ToastUtil.toast(context, "用户信息不能为空");
            return;
        }

        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.login);
        params.addBodyParameter("loginName",username);
        params.addBodyParameter("password", password);

        httpbiz.connectNet(HttpBiz.POST,params);
    }

    @Override
    public void netSuccess(String url, String result) {

        LogUtil.e("登录信息haha",result);
        if(!TextUtils.isEmpty(url) && Const.login.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){


           // 获取用户类型
            Type type= new TypeToken<CommonData<User>>(){}.getType();
            // 根据类型返回实体类
            CommonData<User>  datamessage= JsonParserUtil.getObject(result, type);



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
                        ToastUtil.toast(context,"登录失败");
                        LogUtil.e("登录返回","既不是500，也不是200");
                        break;
                }

            }else{
                LogUtil.e("登录返回解析失败",result);
            }
        }else{
            LogUtil.e("登录返回结果为空",result);
        }
    }

    @Override
    public void netCanceled(String url, Callback.CancelledException e) {
        ToastUtil.toast(context, "登录失败");
        if(netcallback!=null){
            netcallback.getNetCanceled(NetCallBack.NET_CANCELLED,url);
        }
    }

    @Override
    public void netError(String url, Throwable ex, boolean b) {
        ToastUtil.toast(context, "登录失败");
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
