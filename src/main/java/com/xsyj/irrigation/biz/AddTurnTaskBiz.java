package com.xsyj.irrigation.biz;

import android.content.Context;
import android.text.TextUtils;

import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.AbstractNetBiz;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.entity.IrrigationTaskTurn;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by Lenovo on 2016/10/19.
 */
public class AddTurnTaskBiz extends AbstractNetBiz {

    private Context context;
    private NetCallBack<IrrigationTaskTurn> netcallback;

    public AddTurnTaskBiz() {
        super();

    }

    public void getdata(Context context, String taskTurnInfo, NetCallBack<IrrigationTaskTurn> netcallback) {

        this.context = context;
        this.netcallback = netcallback;

        HttpBiz httpbiz = new HttpBiz();
        httpbiz.setNetConnect(this);

        RequestParams params = new RequestParams(Const.add_turntask);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());

        params.setBodyContent(taskTurnInfo);

        httpbiz.connectNet(HttpBiz.POST, params);
    }

    @Override
    public void netSuccess(String url, String result) {

        LogUtil.e("批量添加轮刮组计划列表信息", result);

        if (!TextUtils.isEmpty(url) && Const.add_turntask.equalsIgnoreCase(url) && !TextUtils.isEmpty(result)) {
            if ("0".equals(result)) {
                ToastUtil.toast(context,"添加失败!");
                if(netcallback!=null){
                    netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
                }
            } else if ("1".equals(result)) {
                ToastUtil.toast(context,"添加成功!");
                if(netcallback!=null){
                    netcallback.getNetSuccess(NetCallBack.NET_SUCCESS,url,null);
                }
            } else if ("2".equals(result)) {
                ToastUtil.toast(context, "网关未绑定!");
                if (netcallback != null) {
                    netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
                }
            }else if ("-1".equals(result)) {
                ToastUtil.toast(context, "网关未登录!");
                if (netcallback != null) {
                    netcallback.getNetFauiled(NetCallBack.NET_FAILUED, url);
                }
            }
        }
    }

    @Override
    public void netCanceled(String url, Callback.CancelledException e) {

    }

    @Override
    public void netError(String url, Throwable ex, boolean b) {

    }

    @Override
    public void netFinished(String url) {

    }
}
