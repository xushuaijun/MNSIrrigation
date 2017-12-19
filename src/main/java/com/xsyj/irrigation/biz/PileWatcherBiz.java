package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.TapWatcher;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;


/**
 * 出地桩监控
 * Created by xushuaijun on 2015/11/26.
 */
public class PileWatcherBiz extends AbstractNetBiz {


    private Context context;
    private NetCallBack<List<TapWatcher>> netcallback;

    public PileWatcherBiz(){
        super();

    }


    /**
     * 登录
     * @param context    上下文
     * @param netcallback  网络回调实例
     * @param turnId 轮灌组id
     */
    public void getinfo(Context context,int turnId,int pileId,int tapState,String netGateCode,String start_id,String page_size,String search,NetCallBack<List<TapWatcher>> netcallback){

        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);
       /* String token = MyApplication.mApplication.getToken();
        String areaCode = MyApplication.mApplication.getAreaCode();
        String areaId = MyApplication.mApplication.getAreaId();*/
        RequestParams params=new RequestParams(Const.pile_watcher);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("areaId", MyApplication.mApplication.getAreaId());
        params.addBodyParameter("areaLevel",MyApplication.mApplication.getAreaLevel());
        params.addBodyParameter("areaCode",MyApplication.mApplication.getAreaCode());
        params.addBodyParameter("turnId", String.valueOf(turnId));
        params.addBodyParameter("pileId", String.valueOf(pileId));
        params.addBodyParameter("tapState", String.valueOf(tapState));
        params.addBodyParameter("netGateCode",netGateCode);
        params.addBodyParameter("start_id",start_id);
        params.addBodyParameter("page_size",page_size);
        params.addBodyParameter("search",search);
       /* LogUtil.e("参数值", MyApplication.mApplication.getToken() + "," + String.valueOf(turnId) + "," + String.valueOf(pileId)
                + "," + String.valueOf(tapState) + "," +netGateCode + "," +start_id + "," +page_size + "," +search+","+MyApplication.mApplication.getAreaId()+","+MyApplication.mApplication.getAreaLevel());*/
        httpbiz.connectNet(HttpBiz.POST, params);
    }

    @Override
    public void netSuccess(String url, String result) {
         LogUtil.e("出地桩监控信息", result+"------");

        if(!TextUtils.isEmpty(url) && Const.pile_watcher.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){

           // 获取用户类型
            Type type= new TypeToken<CommonData<List<TapWatcher>>>(){}.getType();

            LogUtil.e("出地桩监控", "解析前");
            CommonData<List<TapWatcher>> datamessage = JsonParserUtil.getObject(result, type);
           

            LogUtil.e("出地桩监控", "解析后"+(datamessage.toString()));
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
                netcallback.getNetFauiled(NetCallBack.NET_FAILUED, "返回解析失败");
            }
        }else{
            LogUtil.e("返回结果为空",result);
            netcallback.getNetFauiled(NetCallBack.NET_FAILUED, "返回结果为空");
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
