package com.xsyj.irrigation.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xsyj.irrigation.ChooseAreaActivity;
import com.xsyj.irrigation.NSURLRequest.GetAreasRequest;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.SoilMoistureInfoActivity;
import com.xsyj.irrigation.adapter.SoilMoisAdapter;
import com.xsyj.irrigation.adapter.SoilMoisMonitorAdapter;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.SoilMoistureJsonBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.MyNodeBean;
import com.xsyj.irrigation.entity.SoilMoistureList;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.ToastUtil;
import com.xsyj.irrigation.utils.ViewPagerFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/5/25.
 */

public class SoilMoisMonitorFragment extends ViewPagerFragment implements View.OnClickListener{

    private PullToRefreshListView prl_soilmois_monitor;
    private EditText et_soilmois_num;
    private LinearLayout soilmoismon_choosearea_view;
    private TextView et_soilmois_area;
    private TextView soilmoismon_search;
    private ImageView chooseAreaDelete;


    private SoilMoisMonitorAdapter adapter;

    private CommonData<List<MyNodeBean>> listCommonData;
    private List<MyNodeBean> myNodeBeanList;
    private List<SoilMoistureList> soilMoisMonitorLists = new ArrayList<SoilMoistureList>();
    private String STCD = "";
    private String start_id = "0";
    private int dowhat = 0;
    private String areaId;
    private String areaLevel;
    private Dialog dialog;

    private final int GETSOILMOISMONITOR_SUCCESS = 0;
    private final int GETSOILMOISMONITOR_FAIL = 1;
    private final int GET_NET_ERROR = 3;

    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GetAreasRequest.GETAREAS_SUCCESS:
                    listCommonData = (CommonData<List<MyNodeBean>>) msg.obj;
                    myNodeBeanList = listCommonData.getData();
                    if (myNodeBeanList != null && myNodeBeanList.size() > 0){

                    }else{
                        ToastUtil.toast(getActivity(),"没有区域信息");
                    }
                    getSoilMoisMonitor();


                    break;
                case GetAreasRequest.GETAREAS_FAIL:
                    ToastUtil.toast(getActivity(),"获取区域信息失败");
                    getSoilMoisMonitor();
                    break;
                case GETSOILMOISMONITOR_SUCCESS:
                    CommonData<List<SoilMoistureList>> soilmoistureData = (CommonData<List<SoilMoistureList>>) msg.obj;
                    if (dowhat == 0 || dowhat == 1) {
                        if (soilMoisMonitorLists != null){
                            soilMoisMonitorLists.clear();
                        }
                    }

                    soilMoisMonitorLists.addAll(soilmoistureData.getData());
                    adapter.notifyDataSetChanged();

                    switch (dowhat) {
                        case 0:
                            if (soilMoisMonitorLists.size() == 0) {
                                ToastUtil.toast(getActivity(), "暂无数据");
                            }
                            break;
                        case 1:
                            if (soilMoisMonitorLists.size() == 0) {
                                ToastUtil.toast(getActivity(), "暂无数据");
                            }
                            break;
                        case 2:
                            if (soilmoistureData.getData().size() == 0) {
                                ToastUtil.toast(getActivity(), "暂无更多数据");
                            }
                            break;
                    }
                    if (dowhat == 1 || dowhat == 2) {
                        prl_soilmois_monitor.onRefreshComplete();
                    }
                    if (dialog != null){
                        dialog.dismiss();
                    }

                    break;
                case GETSOILMOISMONITOR_FAIL:
                    ToastUtil.toast(getActivity(), msg.obj.toString());
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    break;
                case GET_NET_ERROR:
                    ToastUtil.toast(getActivity(), msg.obj.toString());
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_soilmoismonitor, container, false);
        initView(v);
        setListener();
        return v;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible){
            initData();
        }
    }

    private void initView(View v){
        et_soilmois_num = (EditText) v.findViewById(R.id.et_soilmois_num);
        soilmoismon_choosearea_view = (LinearLayout) v.findViewById(R.id.soilmoismon_choosearea_view);
        et_soilmois_area = (TextView) v.findViewById(R.id.et_soilmois_area);
        soilmoismon_search = (TextView) v.findViewById(R.id.soilmoismon_search);
        chooseAreaDelete = (ImageView) v.findViewById(R.id.chooseAreaDelete);

        prl_soilmois_monitor = (PullToRefreshListView) v.findViewById(R.id.prl_soilmois_monitor);

        adapter = new SoilMoisMonitorAdapter(soilMoisMonitorLists,getActivity(), mhandler);

        prl_soilmois_monitor.setAdapter(adapter);

        prl_soilmois_monitor.setMode(PullToRefreshBase.Mode.BOTH);

        // 上拉加载更多，分页加载
        prl_soilmois_monitor.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        prl_soilmois_monitor.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        prl_soilmois_monitor.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        prl_soilmois_monitor.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        prl_soilmois_monitor.getLoadingLayoutProxy(true, false).setRefreshingLabel("刷新中...");
        prl_soilmois_monitor.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");
    }

    private void setListener(){
        soilmoismon_choosearea_view.setOnClickListener(this);
        soilmoismon_search.setOnClickListener(this);
        chooseAreaDelete.setOnClickListener(this);

        prl_soilmois_monitor.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新触发的事件
                // 获取格式化的时间
                String label = DateUtils.formatDateTime(
                        getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 更新LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                dowhat = 1;
                start_id = "0";
                getSoilMoisMonitor();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 获取格式化的时间
                String label = DateUtils.formatDateTime(
                        getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                dowhat = 2;
                start_id = String.valueOf(soilMoisMonitorLists.get(soilMoisMonitorLists.size() - 1).getTheSer());
                getSoilMoisMonitor();
            }
        });
    }

    private void initData(){
        areaId = MyApplication.mApplication.getAreaId();
        areaLevel = MyApplication.mApplication.getAreaLevel();
        dialog = DialogUtil.createLoadingDialog(getActivity(), "正在查询数据……");
        getAreas();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.soilmoismon_choosearea_view:
                Intent chooseAreaIntent = new Intent(getActivity(),ChooseAreaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataList",(Serializable)myNodeBeanList);//序列化,要注意转化(Serializable)
                chooseAreaIntent.putExtras(bundle);
                startActivityForResult(chooseAreaIntent,100);
                break;
            case R.id.soilmoismon_search:
                STCD = et_soilmois_num.getText().toString();
                dialog = DialogUtil.createLoadingDialog(getActivity(), "正在查询数据……");
                getSoilMoisMonitor();
                break;
            case R.id.chooseAreaDelete:
                et_soilmois_area.setText("请点击选择区域");
                areaId = MyApplication.mApplication.getAreaId();
                areaLevel = MyApplication.mApplication.getAreaLevel();
                chooseAreaDelete.setVisibility(View.GONE);
                break;
            default:break;
        }
    }

    private void getAreas() {
        GetAreasRequest.getAreas(getActivity(), mhandler);
    }

    private void getSoilMoisMonitor(){
        SoilMoistureJsonBiz soilMoistureJsonBiz = new SoilMoistureJsonBiz();
        soilMoistureJsonBiz.getSoilMoisJson(getActivity(), areaId, areaLevel, STCD, start_id, new NetCallBack<CommonData<List<SoilMoistureList>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<SoilMoistureList>> listCommonData) {
                if (listCommonData != null){
                    mhandler.obtainMessage(GETSOILMOISMONITOR_SUCCESS, listCommonData).sendToTarget();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mhandler.obtainMessage(GETSOILMOISMONITOR_FAIL, "获取数据失败").sendToTarget();
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                mhandler.obtainMessage(GETSOILMOISMONITOR_FAIL, "连接服务器失败").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 100:
                String currArea = data.getStringExtra("currArea");
                if (TextUtils.isEmpty(currArea)){
                    et_soilmois_area.setText("请点击选择区域");
                    areaId = MyApplication.mApplication.getAreaId();
                    areaLevel = MyApplication.mApplication.getAreaLevel();
                    chooseAreaDelete.setVisibility(View.GONE);
                }else{
                    areaId = data.getStringExtra("areaId");
                    areaLevel = data.getStringExtra("areaLevel");
                    et_soilmois_area.setText(currArea);
                    chooseAreaDelete.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

}
