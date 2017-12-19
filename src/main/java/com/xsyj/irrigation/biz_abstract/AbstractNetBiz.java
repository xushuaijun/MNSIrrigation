package com.xsyj.irrigation.biz_abstract;


import android.app.Activity;

import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.GetPumpInfo;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.common.Callback;

import java.util.List;


/**
 * Created by Administrator on 2015/11/27.
 */
public abstract class AbstractNetBiz implements HttpBiz.NetConnect{


    public AbstractNetBiz(){}



    @Override
    public void getSuccess(String url, String result) {
        LogUtil.e(this.getClass().getSimpleName(), "成功" + result);
        netSuccess(url,result);
    }

    @Override
    public void getCanelled(String url, Callback.CancelledException e) {
        LogUtil.e(this.getClass().getSimpleName(), e.getMessage());
        netCanceled(url,e);
    }

    @Override
    public void getError(String url, Throwable ex, boolean b) {
        LogUtil.e(this.getClass().getSimpleName(), ex.getMessage());
        netError(url, ex, b);
    }

    @Override
    public void getFinished(String url) {
        LogUtil.e(this.getClass().getSimpleName(), url+"结束");
        netFinished(url);
    }


    public abstract void netSuccess(String url, String result);
    public abstract void netCanceled(String url,Callback.CancelledException e);
    public abstract void netError(String url, Throwable ex, boolean b);
    public abstract void netFinished(String url);

}
