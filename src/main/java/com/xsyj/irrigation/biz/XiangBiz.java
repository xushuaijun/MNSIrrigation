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
import com.xsyj.irrigation.entity.Xiang;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.SharePrefrenceUtil;
import com.xsyj.irrigation.utils.ToastUtil;
//import com.xsyj.myapplication.cons.Const;
//import com.xsyj.myapplication.entity.CommInData;
//import com.xsyj.myapplication.entity.CommonData;
//import com.xsyj.myapplication.entity.Xiang;
//import com.xsyj.myapplication.utils.JsonParserUtil;
//import com.xsyj.myapplication.utils.LogUtil;
//import com.xsyj.myapplication.utils.SharePrefrenceUtil;
//import com.xsyj.myapplication.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Lenovo on 2016/9/25.
 */
public class XiangBiz extends AbstractNetBiz {

    private Context context;
    private NetCallBack<CommonDataE<CommInData<List<Xiang>>>> netcallback;

    public XiangBiz() {
    }

    public void getdata(Context context,String type,String XiangNo,String ADDVCD,NetCallBack<CommonDataE<CommInData<List<Xiang>>>> netcallback) {
        this.context = context;
        this.netcallback = netcallback;

        HttpBiz httpbiz = new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params = new RequestParams(Const.XIANG_LISt_URL);

        params.addQueryStringParameter("ADDVCD", String.valueOf(SharePrefrenceUtil.getParam(context, "ADDVCD", "")));
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("XiangNo", XiangNo);

        httpbiz.connectNet(HttpBiz.GET, params);
    }

    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("++++++乡列表++++++", result);
        if(!TextUtils.isEmpty(url) && Const.XIANG_LISt_URL.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){
            // 获取用户类型
            Type type= new TypeToken<CommonDataE<CommInData<List<Xiang>>>>(){}.getType();
            // 根据类型返回实体类
            CommonDataE<CommInData<List<Xiang>>>  datamessage= JsonParserUtil.getObject(result, type);

            //判断解析数据是否为空
            if(datamessage!=null){
                switch (datamessage.getData().getS()){
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
                        ToastUtil.toast(context, "返回乡列表");
                        LogUtil.e("返回乡列表","既不是500，也不是200");
                        break;
                }
            }else{
                LogUtil.e("返回乡列表解析失败",result);
            }
        }else{
            LogUtil.e("返回乡列表结果为空",result);
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
