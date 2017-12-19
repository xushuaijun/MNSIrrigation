package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.Crop;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Lenovo on 2017/5/19.
 */

public class DeletePileBiz extends AbstractNetBiz {


    private Context context;
    private NetCallBack<PileDeleteResult> netcallback;

    public DeletePileBiz(){
        super();

    }

    public void delPile(Context context,int pilenum,
                             NetCallBack<PileDeleteResult> netcallback){
        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.delete_landpile);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("pilenum",pilenum+"");
        httpbiz.connectNet(HttpBiz.POST, params);
    }
    @Override
    public void netSuccess(String url, String result) {
        if(!TextUtils.isEmpty(url) && Const.delete_landpile.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)) {

            // 获取用户类型
            Type type = new TypeToken<PileDeleteResult>() {
            }.getType();
            LogUtil.e("删除出地桩", "解析前");
            PileDeleteResult datamessage = JsonParserUtil.getObject(result, type);

            LogUtil.e("删除出地桩", "解析后" + (datamessage.toString()));
            netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,datamessage);
        }else{
            if(netcallback!=null){
                netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
            }
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
