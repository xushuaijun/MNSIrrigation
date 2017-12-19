package com.xsyj.irrigation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xsyj.irrigation.adapter.NetGateAdapter;
import com.xsyj.irrigation.biz.NetGateBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.NetGate;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class NetGateActivity extends BaseActivity implements View.OnClickListener {

    private Context context;

    private PullToRefreshListView prl_net;

    private List<NetGate> net_List = new ArrayList<>();
    private NetGateAdapter net_adapter;

    private TextView btn_search;
    private EditText etSearch;
    private ImageView ivDeleteText;

    private int page=0;//Integer.MAX_VALUE;   // 初始id
    private int page_size = 15;
    private int dowhat;
    private String netGateName = "";

    private final int DELNETGATE_SUCCESS = 0;
    private final int DELNETGATE_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    private Dialog loadDialog;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DELNETGATE_SUCCESS:
                    PileDeleteResult pileDeleteResult = (PileDeleteResult) msg.obj;
                    if (pileDeleteResult != null){
                        ToastUtil.toast(context,pileDeleteResult.getMsg());
                        getNetInfo();
                    }

                    break;
                case DELNETGATE_FAIL:
                    ToastUtil.toast(context,"删除失败");
                    break;
                case GET_NET_ERROR:
                    String netError = msg.obj.toString();
                    ToastUtil.toast(context,netError);
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_gate);
        context = this;
        initview();
        setlistener();
        getNetInfo();
    }

    private void initview() {

        btn_search = (TextView) findViewById(R.id.btn_search);
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setHint("网关名称");
        ivDeleteText = (ImageView)findViewById(R.id.ivDeleteText);

        prl_net = (PullToRefreshListView) findViewById(R.id.prl_net);

        ImageButton ib_netgate_blue = (ImageButton) findViewById(R.id.ib_netgate_blue);
        ib_netgate_blue.setOnClickListener(this);

        net_adapter = new NetGateAdapter(net_List,this, mHandler);

        prl_net.setAdapter(net_adapter);

        prl_net.setMode(PullToRefreshBase.Mode.BOTH);

        // 上拉加载更多，分页加载
        prl_net.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        prl_net.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        prl_net.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        prl_net.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        prl_net.getLoadingLayoutProxy(true, false).setRefreshingLabel("刷新中...");
        prl_net.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");

    }

    private void setlistener() {

        btn_search.setOnClickListener(this);
        ivDeleteText.setOnClickListener(this);

        prl_net.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新触发的事件
                // 获取格式化的时间
                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 更新LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                dowhat=1;
                page=0;
                getNetInfo();
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
                if(net_List!=null && net_List.size()>0){
                    page=net_List.get(net_List.size()-1).getId();
                }
                getNetInfo();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉

                }
                netGateName=etSearch.getText().toString();
            }
        });

    }



    private void getNetInfo() {


        if (dowhat == 0){
            loadDialog= DialogUtil.createLoadingDialog(context,"正在查询数据...");
        }
        NetGateBiz netGateBiz = new NetGateBiz();
        netGateBiz.net_getdata(this, netGateName, String.valueOf(page), String.valueOf(page_size), new NetCallBack<CommonData<List<NetGate>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<NetGate>> listCommonData) {
                List<NetGate> recordlist = listCommonData.getData();
                LogUtil.e("taplist", listCommonData.getData().toString());
                if (dowhat == 0 || dowhat==1 ) {
                    net_List.clear();
                }
                net_List.addAll(recordlist);
                net_adapter.notifyDataSetChanged();
                switch (dowhat) {
                    case 0:
                        if (net_List.size() == 0) {
                            ToastUtil.toast(NetGateActivity.this, "暂无数据");
                        }
                        break;
                    case 1:
                        if (net_List.size() == 0) {
                            ToastUtil.toast(NetGateActivity.this, "暂无数据");
                        }
                        break;
                    case 2:
                        if (recordlist.size() == 0) {
                            ToastUtil.toast(NetGateActivity.this, "暂无更多数据");
                        }
                        break;
                }
                if (dowhat == 1||dowhat==2) {
                    prl_net.onRefreshComplete();
                }
                if (loadDialog != null){
                    loadDialog.dismiss();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                ToastUtil.toast(context, "获取数据失败");
                if (dowhat == 1||dowhat==2) {
                    prl_net.onRefreshComplete();
                }
                if (loadDialog != null){
                    loadDialog.dismiss();
                }
            }

            @Override
            public void getNetCanceled(int statu, String url) {
                if (loadDialog != null){
                    loadDialog.dismiss();
                }
            }

            @Override
            public void getNetError(int statu, String url) {
                if (dowhat == 1||dowhat==2) {
                    prl_net.onRefreshComplete();
                }
                if (loadDialog != null){
                    loadDialog.dismiss();
                }
                ToastUtil.toast(context,"连接服务器失败");
            }

            @Override
            public void getNetFinished(int statu, String url) {
                if (loadDialog != null){
                    loadDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_netgate_blue:
                   startActivity(new Intent(this,NetBlueSetActivity.class));
//                startActivity(new Intent(this,NetSetActivity.class));
                break;
            case R.id.btn_search:
                page = 0;
                dowhat = 0;
                getNetInfo();
                break;
            case R.id.ivDeleteText:
                etSearch.setText("");
                netGateName = "";
                break;
        }
    }
}
