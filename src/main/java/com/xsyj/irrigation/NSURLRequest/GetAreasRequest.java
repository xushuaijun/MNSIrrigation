package com.xsyj.irrigation.NSURLRequest;

import android.content.Context;
import android.os.Handler;

import com.xsyj.irrigation.biz.GetAreasBiz;
import com.xsyj.irrigation.biz.PumpHouseListBiz;
import com.xsyj.irrigation.biz.PumpListByHouseNumBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.MyNodeBean;
import com.xsyj.irrigation.entity.PumpHouseList;
import com.xsyj.irrigation.entity.PumpListByHouseNum;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;

import java.util.List;

/**
 * Created by Lenovo on 2017/5/12.
 */

public class GetAreasRequest {

    public final static int GETAREAS_SUCCESS = 110;
    public final static int GETAREAS_FAIL = 112;
    public final static int GETPUMPLISTBYHOUSENUM_SUCCESS = 113;
    public final static int GETPUMPLISTBYHOUSENUM_FAIL = 114;
    public final static int GETPUMPHOUSELIST_SUCCESS = 115;
    public final static int GETPUMPHOUSELIST_FAIL = 116;
    private final static int GET_NET_ERROR = 3;

    public static void getAreas(Context context, final Handler mHandler){
        GetAreasBiz getAreasBiz = new GetAreasBiz();
        getAreasBiz.getAreas(context, new NetCallBack<CommonData<List<MyNodeBean>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<MyNodeBean>> myNodeBeanCommonData) {
                if (myNodeBeanCommonData != null) {

                    mHandler.obtainMessage(GETAREAS_SUCCESS, myNodeBeanCommonData).sendToTarget();
                }

            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mHandler.obtainMessage(GETAREAS_FAIL, "获取区域失败").sendToTarget();

            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                mHandler.obtainMessage(GET_NET_ERROR, "连接服务器失败").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }

    // 获取水源列表
    public static void getPumpList(Context context, String token, String areaId, String areaLevel, final Handler mHandler) {
        PumpHouseListBiz pumpHouseListBiz=new PumpHouseListBiz();
        pumpHouseListBiz.getPumpHouseList(context, token, areaId, areaLevel, new NetCallBack<CommonData<List<PumpHouseList>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<PumpHouseList>> listCommonData) {
                mHandler.obtainMessage(GETPUMPHOUSELIST_SUCCESS, listCommonData).sendToTarget();
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mHandler.obtainMessage(GETPUMPHOUSELIST_FAIL, "获取水源首部列表").sendToTarget();
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                mHandler.obtainMessage(GET_NET_ERROR, "连接服务器失败").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }


    // 获取水源机井
    public static void getPumpListByHouseNum(Context context, String pumphousenum, final Handler mHandler) {
        PumpListByHouseNumBiz pumpListByHouseNumBiz=new PumpListByHouseNumBiz();
        pumpListByHouseNumBiz.getPumpListByHouseNum(context, pumphousenum, new NetCallBack<CommonData<List<PumpListByHouseNum>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<PumpListByHouseNum>> listCommonData) {
                mHandler.obtainMessage(GETPUMPLISTBYHOUSENUM_SUCCESS, listCommonData).sendToTarget();
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mHandler.obtainMessage(GETPUMPLISTBYHOUSENUM_FAIL, "获取水源机井失败").sendToTarget();
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                mHandler.obtainMessage(GET_NET_ERROR, "连接服务器失败").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }

}
