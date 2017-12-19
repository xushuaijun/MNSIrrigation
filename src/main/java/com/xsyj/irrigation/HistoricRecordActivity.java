package com.xsyj.irrigation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xsyj.irrigation.adapter.HistoryRecordAdapter;
import com.xsyj.irrigation.biz.HistoryRecordBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.HistoryRecord;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/5/23.
 */

public class HistoricRecordActivity extends BaseActivity{
    private Context context;

    private PullToRefreshListView prl_historyrecord;
    private HistoryRecordAdapter adapter;

    private Dialog loadDialog;

    private List<HistoryRecord> historyRecordList = new ArrayList<HistoryRecord>();
    private int page = 0;
    private int dowhat = 0;

    private final int GETHISTORYRECORD_SUCCESS = 0;
    private final int GETHISTORYRECORD_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETHISTORYRECORD_SUCCESS:
                    CommonData<List<HistoryRecord>> listCommonData = (CommonData<List<HistoryRecord>>)msg.obj;
                    if (dowhat == 0 || dowhat == 1){
                        if (historyRecordList != null){
                            historyRecordList.clear();
                        }
                    }
                    historyRecordList.addAll(listCommonData.getData());
                    adapter.notifyDataSetChanged();
                    switch (dowhat) {
                        case 0:
                            if (historyRecordList.size() == 0) {
                                ToastUtil.toast(context, "暂无数据");
                            }
                            break;
                        case 1:
                            if (historyRecordList.size() == 0) {
                                ToastUtil.toast(context, "暂无数据");
                            }
                            break;
                        case 2:
                            if (listCommonData.getData().size() == 0) {
                                ToastUtil.toast(context, "暂无更多数据");
                            }
                            break;
                    }
                    if(dowhat==1||dowhat==2){
                        prl_historyrecord.onRefreshComplete();
                    }
                    if (loadDialog != null){
                        loadDialog.dismiss();
                    }
                    break;
                case GETHISTORYRECORD_FAIL:
                    if (loadDialog != null){
                        loadDialog.dismiss();
                    }
                    ToastUtil.toast(context,msg.obj.toString());
                    break;
                case GET_NET_ERROR:
                    if (loadDialog != null){
                        loadDialog.dismiss();
                    }
                    ToastUtil.toast(context,msg.obj.toString());
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyrecord);
        context = this;
        initView();
        getHistoryRecord();
    }

    private void initView(){
        prl_historyrecord = (PullToRefreshListView) findViewById(R.id.prl_historyrecord);
        adapter = new HistoryRecordAdapter(historyRecordList,context);
        prl_historyrecord.setAdapter(adapter);

        prl_historyrecord.setMode(PullToRefreshBase.Mode.BOTH);

        // 上拉加载更多，分页加载
        prl_historyrecord.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        prl_historyrecord.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        prl_historyrecord.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        prl_historyrecord.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        prl_historyrecord.getLoadingLayoutProxy(true, false).setRefreshingLabel("刷新中...");
        prl_historyrecord.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");

        prl_historyrecord.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 获取格式化的时间
                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 更新LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                dowhat=1;
                page=0;
                getHistoryRecord();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 获取格式化的时间
                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 更新LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                dowhat=2;
                page=historyRecordList.get(historyRecordList.size()-1).getId();
                getHistoryRecord();
            }
        });
    }

    private void getHistoryRecord(){
        if (dowhat == 0){
            loadDialog = DialogUtil.createLoadingDialog(context,"正在查询历史记录……");
        }
        HistoryRecordBiz historyRecordBiz = new HistoryRecordBiz();
        historyRecordBiz.getHistory(context, String.valueOf(page), new NetCallBack<CommonData<List<HistoryRecord>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<HistoryRecord>> listCommonData) {
                if (listCommonData != null){
                    mHandler.obtainMessage(GETHISTORYRECORD_SUCCESS,listCommonData).sendToTarget();
                }else{
                    mHandler.obtainMessage(GETHISTORYRECORD_FAIL,"获取数据失败!").sendToTarget();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mHandler.obtainMessage(GETHISTORYRECORD_FAIL,"获取数据失败!").sendToTarget();
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                mHandler.obtainMessage(GET_NET_ERROR,"连接服务器失败!").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }
}
