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
import com.xsyj.irrigation.adapter.CommUseAdapter;
import com.xsyj.irrigation.adapter.PileAdapter;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.PileBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.Pile;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class PileActivity extends BaseActivity implements View.OnClickListener{

    private Context context;
    private List<Pile> pile_List=new ArrayList<>();
    private PullToRefreshListView prl_cdz;
    private String pilename="";
    private int page=0;
    private int page_size=15;
    private int dowhat;
    private PileAdapter pile_adapter;
    private ImageButton btn_pile_add;
    private TextView btn_pile_search;
    private EditText pile_etSearch;
    private ImageView pile_ivDeleteText;

    private final int DELPILE_SUCCESS = 0;
    private final int DELPILE_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    private Dialog loadDialog;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DELPILE_SUCCESS:
                    PileDeleteResult pileDeleteResult = (PileDeleteResult) msg.obj;
                    if (pileDeleteResult != null){
                        ToastUtil.toast(context,pileDeleteResult.getMsg());
                        getPile();
                    }

                    break;
                case DELPILE_FAIL:
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
        setContentView(R.layout.activity_pile);
        context = this;
        initview();
        seltListener();
        getPile();
    }

    private void initview() {

        btn_pile_add = (ImageButton) findViewById(R.id.btn_pile_add);
        btn_pile_search = (TextView) findViewById(R.id.btn_pile_search);
        pile_etSearch = (EditText) findViewById(R.id.pile_etSearch);
        pile_ivDeleteText = (ImageView) findViewById(R.id.pile_ivDeleteText);

        prl_cdz = (PullToRefreshListView) findViewById(R.id.prl_cdz);

        pile_adapter = new PileAdapter(pile_List,this,handler);

        prl_cdz.setAdapter(pile_adapter);

        prl_cdz.setMode(PullToRefreshBase.Mode.BOTH);

        // 上拉加载更多，分页加载
        prl_cdz.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        prl_cdz.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        prl_cdz.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        prl_cdz.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        prl_cdz.getLoadingLayoutProxy(true, false).setRefreshingLabel("刷新中...");
        prl_cdz.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");

    }

    private void seltListener() {

        btn_pile_add.setOnClickListener(this);
        btn_pile_search.setOnClickListener(this);
        pile_ivDeleteText.setOnClickListener(this);

        prl_cdz.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
                getPile();
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
                page=pile_List.get(pile_List.size()-1).getId();
                getPile();
            }
        });

        pile_etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    pile_ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
                } else {
                    pile_ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉

                }
                pilename=pile_etSearch.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pile_add:
                Intent intent = new Intent(this,AddPileActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
                break;
            case R.id.btn_pile_search:
                page = 0;
                dowhat = 0;
                getPile();
                break;
            case R.id.pile_ivDeleteText:
                pile_etSearch.setText("");
                pilename = "";
                break;
            default:break;
        }


    }

    private void getPile(){
        if (dowhat == 0){
            loadDialog= DialogUtil.createLoadingDialog(context,"正在查询数据...");
        }
        PileBiz pileBiz = new PileBiz();
        pileBiz.pile_getdata(MyApplication.mApplication.getApplicationContext(), pilename, String.valueOf(page), String.valueOf(page_size), new NetCallBack<CommonData<List<Pile>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<Pile>> listCommonData) {

                List<Pile> recordlist = listCommonData.getData();
                if (dowhat == 0 || dowhat==1 ){
                    pile_List.clear();
                }
                pile_List.addAll(recordlist);
                pile_adapter.notifyDataSetChanged();
                switch (dowhat) {
                    case 0:
                        if (pile_List.size() == 0) {
                            ToastUtil.toast(PileActivity.this, "暂无数据");
                        }
                        break;
                    case 1:
                        if (pile_List.size() == 0) {
                            ToastUtil.toast(PileActivity.this, "暂无数据");
                        }
                        break;
                    case 2:
                        if (recordlist.size() == 0) {
                            ToastUtil.toast(PileActivity.this, "暂无更多数据");
                        }
                        break;
                }
                if (dowhat == 1||dowhat==2) {
                    prl_cdz.onRefreshComplete();
                }
                if (loadDialog != null){
                    loadDialog.dismiss();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                 ToastUtil.toast(context,"获取数据失败");
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
