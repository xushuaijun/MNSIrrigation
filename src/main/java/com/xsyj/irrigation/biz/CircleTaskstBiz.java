package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.IrrigationTaskTurn;
import com.xsyj.irrigation.entity.IrrigationTurnInfo;
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
public class CircleTaskstBiz extends AbstractNetBiz {


    private Context context;
    private NetCallBack<List<IrrigationTaskTurn>> netcallback;

    public CircleTaskstBiz(){
        super();

    }


    /**
     * 登录
     * @param context    上下文
     * @param circlueCount  轮灌周期数
     * @param netcallback  网络回调实例
     */
    public void getinfo(Context context,int circlueCount, String pumpnum, NetCallBack<List<IrrigationTaskTurn>> netcallback){

        this.context=context;
        this.netcallback=netcallback;

        HttpBiz httpbiz=new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params=new RequestParams(Const.turn_circle_tasks);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        params.addBodyParameter("circlueCount", String.valueOf(circlueCount));
        params.addBodyParameter("pump",pumpnum);
        httpbiz.connectNet(HttpBiz.POST, params);
    }

    @Override
    public void netSuccess(String url, String result) {
        LogUtil.e("周期下的轮灌组任务", result);
        if(!TextUtils.isEmpty(url) && Const.turn_circle_tasks.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)){



           // 获取类型
            Type type= new TypeToken<CommonData<List<IrrigationTaskTurn>>>(){}.getType();
             new GsonBuilder().setDateFormat("");

            LogUtil.e("周期下的轮灌组任务列表", "解析前");
            CommonData<List<IrrigationTaskTurn>> datamessage = JsonParserUtil.getDateObject(result, type);
           

            LogUtil.e("周期下的轮灌组任务", "解析后"+(datamessage.toString()));
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
                            netcallback.getNetFauiled(NetCallBack.NET_FAILUED, datamessage.getMsg());
                        }
                        break;
                    case 100:
                        if(netcallback!=null){
                            netcallback.getNetFauiled(NetCallBack.NET_FAILUED, datamessage.getMsg());
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
