package com.xsyj.irrigation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xsyj.irrigation.adapter.TapAdapter;
import com.xsyj.irrigation.biz.BDWGBiz;
import com.xsyj.irrigation.biz.JBWGBiz;
import com.xsyj.irrigation.biz.TapInfoBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.IrrigationTask;
import com.xsyj.irrigation.entity.PileDeleteResult;
import com.xsyj.irrigation.entity.Tap;
import com.xsyj.irrigation.entity.TapWatcher;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.PoupWindowUtil;
import com.xsyj.irrigation.utils.ScreenUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class TapActivity extends BaseActivity implements View.OnClickListener{

    private Context context;
    public static final int WGBD=1;//网关绑定
    public static final int WGJB=2;//网关解绑

    private PullToRefreshListView prl_tap;

    private List<Tap> tap_List = new ArrayList<>();
    private Dialog dialog;
    private int what;
    private Dialog dialog1;

    public List<Tap> getTap_List() {
        return tap_List;
    }

    private TapAdapter tap_adapter;

    private int page;   // 初始id
    private int page_size = 15;
    private int dowhat;
    private String tapname = "";
    private TextView menu;
    private PopupWindow pop;

    private TextView btn_search;
    private EditText etSearch;
    private ImageView ivDeleteText;

    private final int DETAP_SUCCESS = 0;
    private final int DETAP_FAIL = 1;
    private final int GET_NET_ERROR = 2;

    private Dialog loadDialog;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DETAP_SUCCESS:
                    PileDeleteResult pileDeleteResult = (PileDeleteResult) msg.obj;
                    if (pileDeleteResult != null){
                        ToastUtil.toast(context,pileDeleteResult.getMsg());
                        getTapInfo();
                    }

                    break;
                case DETAP_FAIL:
                    ToastUtil.toast(context,"删除失败");
                    break;
                case GET_NET_ERROR:
                    String netError = msg.obj.toString();
                    ToastUtil.toast(context,netError);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);
        context = this;
        initview();
        setlistener();
        getTapInfo();
    }

    private void getTapInfo() {
        if (dowhat == 0){
            loadDialog= DialogUtil.createLoadingDialog(context,"正在查询数据...");
        }
        TapInfoBiz tapInfoBiz = new TapInfoBiz();
        tapInfoBiz.tap_getdata(this, tapname, String.valueOf(page), String.valueOf(page_size), new NetCallBack<CommonData<List<Tap>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<Tap>> listCommonData) {
                List<Tap> recordlist = listCommonData.getData();
                LogUtil.e("taplist", listCommonData.getData().toString());
                if (dowhat == 0 || dowhat == 1) {
                    tap_List.clear();
                }
                tap_List.addAll(recordlist);
                tap_adapter.notifyDataSetChanged();
                switch (dowhat) {
                    case 0:
                        if (tap_List.size() == 0) {
                            ToastUtil.toast(TapActivity.this, "暂无数据");
                        }
                        break;
                    case 1:
                        if (tap_List.size() == 0) {
                            ToastUtil.toast(TapActivity.this, "暂无数据");
                        }
                        break;
                    case 2:
                        if (recordlist.size() == 0) {
                            ToastUtil.toast(TapActivity.this, "暂无更多数据");
                        }
                        break;
                }
                if (dowhat == 1 || dowhat == 2) {
                    prl_tap.onRefreshComplete();
                }
                if (loadDialog != null){
                    loadDialog.dismiss();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                ToastUtil.toast(context, "获取数据失败");
                if (dowhat == 1 || dowhat == 2) {
                    prl_tap.onRefreshComplete();
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
                if (dowhat == 1 || dowhat == 2) {
                    prl_tap.onRefreshComplete();
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

    private void setlistener() {

        btn_search.setOnClickListener(this);
        ivDeleteText.setOnClickListener(this);

        menu.setOnClickListener(this);

        prl_tap.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
                dowhat = 1;
                page = 0;
                getTapInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 获取格式化的时间
                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                dowhat = 2;
                page = tap_List.get(tap_List.size() - 1).getId();
                getTapInfo();
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
                tapname = etSearch.getText().toString();
            }
        });
    }

    private void initview() {

        btn_search = (TextView) findViewById(R.id.btn_search);
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setHint("阀门编号");
        ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);

        menu = (TextView)findViewById(R.id.btn_tap_menu);

        prl_tap = (PullToRefreshListView) findViewById(R.id.prl_tap);

        tap_adapter = new TapAdapter(tap_List,this, handler);

        prl_tap.setAdapter(tap_adapter);

        prl_tap.setMode(PullToRefreshBase.Mode.BOTH);

        // 上拉加载更多，分页加载
        prl_tap.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        prl_tap.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        prl_tap.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        prl_tap.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        prl_tap.getLoadingLayoutProxy(true, false).setRefreshingLabel("刷新中...");
        prl_tap.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_tap_menu:
                if (pop != null && pop.isShowing()) {
                    pop.dismiss();
                } else {
                    pop(v); // 弹出控制界面
                }
                break;
//            case R.id.tv_tap_add://添加
//                break;
            case R.id.tv_tap_wgbd://网关绑定
                binding();
                pop.dismiss();
                break;
            case R.id.tv_tap_wgjb://网关解绑
                Unbundling();
                pop.dismiss();
                break;
            case R.id.btn_search:
                page = 0;
                dowhat = 0;
                getTapInfo();
                break;
            case R.id.ivDeleteText:
                etSearch.setText("");
                tapname = "";
                break;
        }
    }

    private void Unbundling() {
        dialog1 = DialogUtil.createLoadingDialog(this, "正在解绑...");
        /**
         * 处理数据,转变成json,上传到服务器上
         */
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<IrrigationTask> temptasks = createJson();
                JBWGBiz jbwgBiz = new JBWGBiz();
                LogUtil.e("解析数据",gson.toJson(temptasks));
                jbwgBiz.getInfo(TapActivity.this, gson.toJson(temptasks), new NetCallBack<Integer>() {
                    @Override
                    public void getNetSuccess(int statu, String url, Integer integer) {
                        LogUtil.e("解绑值",integer);
                        if(integer.intValue()>0) {
                            ToastUtil.toast(TapActivity.this, "解绑成功");
                        }else if(integer.intValue() == -1){
                            ToastUtil.toast(TapActivity.this, "解绑失败");
                        }else if(integer.intValue() == -4){
                            ToastUtil.toast(TapActivity.this, "网关不在线");
                        }

                        dowhat = 1;
                        page = 0;
                        getTapInfo();
                        dialog1.dismiss();
                    }

                    @Override
                    public void getNetFauiled(int statu, String url) {

                    }

                    @Override
                    public void getNetCanceled(int statu, String url) {

                    }

                    @Override
                    public void getNetError(int statu, String url) {

                    }

                    @Override
                    public void getNetFinished(int statu, String url) {

                    }
                });
            }
        });
    }

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

    private void binding() {
        dialog = DialogUtil.createLoadingDialog(this, "正在绑定...");

        /**
         * 处理数据,转变成json,上传到服务器上
         */
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<IrrigationTask> temptasks = createJson();
                BDWGBiz bdwgBiz = new BDWGBiz();
                LogUtil.e("解析数据",gson.toJson(temptasks));
                bdwgBiz.getInfo(TapActivity.this, gson.toJson(temptasks), new NetCallBack<Integer>() {
                    @Override
                    public void getNetSuccess(int statu, String url, Integer integer) {
                        LogUtil.e("绑定值",integer);
                        if(integer.intValue()>0){
                            ToastUtil.toast(TapActivity.this, "绑定成功");
                        }else if(integer.intValue() == -1){
                            ToastUtil.toast(TapActivity.this, "绑定失败");
                        }else if(integer.intValue() == -4){
                            ToastUtil.toast(TapActivity.this, "网关不在线");
                        }

                        dowhat = 1;
                        page = 0;
                        getTapInfo();
                        dialog.dismiss();
                    }

                    @Override
                    public void getNetFauiled(int statu, String url) {

                    }

                    @Override
                    public void getNetCanceled(int statu, String url) {

                    }

                    @Override
                    public void getNetError(int statu, String url) {

                    }

                    @Override
                    public void getNetFinished(int statu, String url) {

                    }
                });
            }
        });
    }

    private List<IrrigationTask> createJson() {
        List<IrrigationTask> list = new ArrayList<>();
        for(int i = 0; i < tap_List.size(); i++){
            IrrigationTask irrigationTask = new IrrigationTask();
            Tap tap = tap_List.get(i);
            if(tap.isSelected()){
                irrigationTask.setTapnum(tap.getTapnum());
                irrigationTask.setOpenmouth("");
                irrigationTask.setDowhat(0);
                irrigationTask.setNetGateCode(tap.getNetgatecode());
                irrigationTask.setIrrigationtype(0);
                irrigationTask.setIsDownLoad(tap.getIsDownLoad());
                irrigationTask.setState(-1);
                list.add(irrigationTask);
            }
        }
        return list;
    }


    /**
     * 勾选完之后，刷新数据
     */
    public void refreshData() {
        tap_adapter.notifyDataSetChanged();
    }


    /**
     * 弹出控制菜单
     */
    private void pop(View parent) {
        View v = LayoutInflater.from(this).inflate(R.layout.tap_menu, null);
//        TextView tv_tap_add = (TextView)v.findViewById(R.id.tv_tap_add);
//        tv_tap_add.setOnClickListener(this);
        TextView tv_tap_wgbd = (TextView)v.findViewById(R.id.tv_tap_wgbd);
        tv_tap_wgbd.setOnClickListener(this);
        TextView tv_tap_wgjb = (TextView)v.findViewById(R.id.tv_tap_wgjb);
        tv_tap_wgjb.setOnClickListener(this);

        pop = PoupWindowUtil.getInstance(v, null, 0, false, ScreenUtil.getScreenWidth(this) / 3 + 20, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.showAsDropDown(parent);

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                if (pop != null && pop.isShowing()) {
                    pop.dismiss();

                }

            }
        });
    }
}
