package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.Tap;
import com.xsyj.irrigation.entity.TapInfo;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/1.
 */
public class TapInfoBiz extends AbstractNetBiz{

    private Context context;
    private NetCallBack<CommonData<List<Tap>>> netcallback;

    public TapInfoBiz(){
        super();

    }

    public void tap_getdata(Context context,String tapname,String start_id,String page_size,NetCallBack<CommonData<List<Tap>>> netcallback){
        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        String token = MyApplication.mApplication.getToken();
        String id = MyApplication.mApplication.getAreaId();
        String level = MyApplication.mApplication.getAreaLevel();

        RequestParams params=new RequestParams(Const.tap_list);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("areaId", MyApplication.mApplication.getAreaId());
        params.addBodyParameter("areaCode", MyApplication.mApplication.getAreaCode());
        params.addBodyParameter("areaLevel", MyApplication.mApplication.getAreaLevel());
        params.addBodyParameter("tapname",tapname);
        params.addBodyParameter("start_id",start_id);
        params.addBodyParameter("page_size", page_size);
        LogUtil.e("阀门参数",start_id+"/"+page_size );

        httpbiz.connectNet(HttpBiz.POST, params);
    }

    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("阀门信息列表", result);
        if(!TextUtils.isEmpty(url) && Const.tap_list.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){

            // 获取用户类型
            Type type= new TypeToken<CommonData<List<Tap>>>(){}.getType();
            LogUtil.e("阀门信息列表", "解析前");
            CommonData<List<Tap>> datamessage = JsonParserUtil.getObject(result, type);

            LogUtil.e("阀门信息列表", "解析后"+(datamessage.toString()));
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
