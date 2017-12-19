package com.xsyj.irrigation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xsyj.irrigation.adapter.PumpWellAdapter;
import com.xsyj.irrigation.biz.PumpWellBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.entity.PumpWell;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class PumpWellActivity extends BaseActivity implements View.OnClickListener{

    private Context context;
    private List<PumpWell> pumpWell_List=new ArrayList<>();

    private PullToRefreshListView prl_watersoce;
    private PumpWellAdapter pumpWell_adapter;
    private String pumpname="";
    private int page;
    private int page_size=15;
    private int dowhat;

    private TextView btn_search;
    private EditText etSearch;
    private ImageView ivDeleteText;

    private final int DELPUMPWELL_SUCCESS = 0;
    private final int DELPUMPWELL_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    private Dialog loadDialog;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DELPUMPWELL_SUCCESS:
                    PileDeleteResult pileDeleteResult = (PileDeleteResult) msg.obj;
                    if (pileDeleteResult != null){
                        ToastUtil.toast(context,pileDeleteResult.getMsg());
                        getPumpWell();
                    }

                    break;
                case DELPUMPWELL_FAIL:
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
        setContentView(R.layout.activity_pumpwell_source);
        context = this;
        initview();
        seltListener();
        getPumpWell();
    }

    private void initview() {

        btn_search = (TextView) findViewById(R.id.btn_search);
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setHint("机井名称");
        ivDeleteText = (ImageView)findViewById(R.id.ivDeleteText);

        prl_watersoce=(PullToRefreshListView)findViewById(R.id.prl_watersoce);

        pumpWell_adapter = new PumpWellAdapter(pumpWell_List,this, mHandler);

        prl_watersoce.setAdapter(pumpWell_adapter);

        prl_watersoce.setMode(PullToRefreshBase.Mode.BOTH);

        // 上拉加载更多，分页加载
        prl_watersoce.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        prl_watersoce.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        prl_watersoce.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        prl_watersoce.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        prl_watersoce.getLoadingLayoutProxy(true, false).setRefreshingLabel("刷新中...");
        prl_watersoce.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");

    }


    private void seltListener() {

        btn_search.setOnClickListener(this);
        ivDeleteText.setOnClickListener(this);

        prl_watersoce.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {//下拉刷新
                // 获取格式化的时间
                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 更新LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                dowhat=1;
                page=0;
                getPumpWell();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//上滑加载
                // 获取格式化的时间
                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 更新LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                dowhat=2;
                page=pumpWell_List.get(pumpWell_List.size()-1).getId();
                getPumpWell();
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
                pumpname=etSearch.getText().toString();
            }
        });
    }



    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.btn_search:
                 page = 0;
                 dowhat = 0;
                 getPumpWell();
                 break;
             case R.id.ivDeleteText:
                 etSearch.setText("");
                 pumpname = "";
                 break;
             default:break;
         }
    }

    private void getPumpWell(){
        if (dowhat == 0){
            loadDialog= DialogUtil.createLoadingDialog(context,"正在查询数据...");
        }
        PumpWellBiz sourceBiz = new PumpWellBiz();
        sourceBiz.pumpwell_getdata(this, pumpname, String.valueOf(page), String.valueOf(page_size), new NetCallBack<CommonData<List<PumpWell>>>() {


            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<PumpWell>> listCommonData) {
                List<PumpWell> recordlist = listCommonData.getData();
                if (dowhat == 0 || dowhat==1 ){
                    pumpWell_List.clear();
                }
                pumpWell_List.addAll(recordlist);
                pumpWell_adapter.notifyDataSetChanged();
                switch (dowhat) {
                    case 0:
                        if (pumpWell_List.size() == 0) {
                            ToastUtil.toast(PumpWellActivity.this, "暂无数据");
                        }
                        break;
                    case 1:
                        if (pumpWell_List.size() == 0) {
                            ToastUtil.toast(PumpWellActivity.this, "暂无数据");
                        }
                        break;
                    case 2:
                        if (recordlist.size() == 0) {
                            ToastUtil.toast(PumpWellActivity.this, "暂无更多数据");
                        }
                        break;
                }

                if(dowhat==1||dowhat==2){
                    prl_watersoce.onRefreshComplete();
                }

                if (loadDialog != null){
                    loadDialog.dismiss();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                ToastUtil.toast(context, "获取数据失败");
                if(dowhat==1||dowhat==2){
                    prl_watersoce.onRefreshComplete();
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
                if(dowhat==1||dowhat==2){
                    prl_watersoce.onRefreshComplete();
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

}
