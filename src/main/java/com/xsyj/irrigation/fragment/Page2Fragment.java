package com.xsyj.irrigation.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xsyj.irrigation.FragmentActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.CommUseAdapter;
import com.xsyj.irrigation.adapter.PullToRefreshListViewAdapter;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.TurnDelBiz;
import com.xsyj.irrigation.biz.TurnInfoListBiz;
import com.xsyj.irrigation.biz.TurnWatcherBiz;
import com.xsyj.irrigation.customview.MyGridView;
import com.xsyj.irrigation.entity.IrrigationTurnInfo;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ScreenUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class Page2Fragment extends Fragment implements View.OnClickListener, PullToRefreshListViewAdapter.OnDelTurnLisitener {

    private FragmentActivity context;
    private LinearLayout btn_page2_sx;
    private ImageView iv_page2_sx;
    private static final int GET_TAPWATCHER_SUCCESS = 0;
    private static final int GET_TAPWATCHER_FAIL = 1;
    private static final int GET_NET_ERROR = 2;
    private static final int GET_SELECTOR_FAIL = 3;

    private PullToRefreshListView mPullToRefreshListView;
    private PullToRefreshListViewAdapter adapter;
    private MyGridView gv_turnwatcher_lgz;
    private CommUseAdapter<IrrigationTurnInfo> lgzadapter;
    private PopupWindow popupWindow;
    private Dialog loadDialog;
    private Button btn_page2_reset;
    private Button btn_page2_sure;
    private int turnId;
    private List<IrrigationTurnInfo> turnwatchers;
    private int lastlgzposition;
    private List<IrrigationTurnInfo> lgz_list;
    private int dowhat;
    private int page;
    private int page_size;
    private int count = 0;
    private Dialog dialog;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_TAPWATCHER_SUCCESS:
                    List<IrrigationTurnInfo> irrigationTurnInfos = (List<IrrigationTurnInfo>)msg.obj;
                    if (dowhat == 0 || dowhat == 1) {
                        turnwatchers.clear();
                    }
                    turnwatchers.addAll(irrigationTurnInfos);
                    adapter.notifyDataSetChanged();
                    switch (dowhat) {
                        case 0:
                            if (irrigationTurnInfos.size() == 0) {
                                ToastUtil.toast(context, "暂无数据");
//                                if(dialog!=null){
//                                    dialog.dismiss();
//                                }
                            }
                            break;
                        case 1:
                            if (irrigationTurnInfos.size() == 0) {
                                ToastUtil.toast(context, "暂无数据");
                            }
                            break;
                        case 2:
                            if (irrigationTurnInfos.size() == 0) {
                                ToastUtil.toast(context, "暂无更多数据");
                            }
                            break;
                    }


//                    if (dialog != null) {
//                        dialog.dismiss();
//                    }
                    if (loadDialog != null){
                        loadDialog.dismiss();
                    }
                    if (dowhat == 1||dowhat==2) {
                        mPullToRefreshListView.onRefreshComplete();
                    }
                    getSelector();
                    break;
                case GET_TAPWATCHER_FAIL:
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
                case GET_SELECTOR_FAIL:
                    ToastUtil.toast(context,msg.obj.toString());
                    break;
                default:break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_page2_fragment,container,false);
        context = (FragmentActivity) this.getActivity();
        initview(v);
        setlistener();
        initData();
//        getSelector();

        return v;
    }

    private void initData(){
        dowhat=0;
        page=0;
        page_size=15;
        loadDialog=DialogUtil.createLoadingDialog(context,"正在加载轮灌组信息...");

        getTapWatcher();
    }

    private void setlistener() {
        btn_page2_sx.setOnClickListener(this);
        // 监听滚动
        mPullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                adapter.close();
            }
        });

      //   监听下拉刷新
//        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//
//            //模拟一下刷新数据，额
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                dowhat=1;
//                getTapWatcher();
//
//            }
//        });
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                dowhat=1;
                page=1;
                page_size=15;
                getTapWatcher();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

//                dowhat=2;
//                page=lgz_list.get(lgz_list.size()-1).getIds()+1;
//                page_size=page+15-1;
//                getTapWatcher();
            }
        });

        btn_page2_reset.setOnClickListener(this);
        btn_page2_sure.setOnClickListener(this);


    }

    public void  getTapWatcher() {

        TurnWatcherBiz turnWatcherBiz=new TurnWatcherBiz();
        turnWatcherBiz.getinfo(MyApplication.mApplication.getApplicationContext(), turnId, String.valueOf(page), String.valueOf(page_size), new NetCallBack<List<IrrigationTurnInfo>>() {
            @Override
            public void getNetSuccess(int statu, String url, List<IrrigationTurnInfo> irrigationTurnInfos) {
                if (irrigationTurnInfos != null){
                    mHandler.obtainMessage(GET_TAPWATCHER_SUCCESS, irrigationTurnInfos).sendToTarget();
                }

            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mHandler.obtainMessage(GET_TAPWATCHER_FAIL, "获取数据失败").sendToTarget();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==2){
            switch (resultCode){
                case 2:
                        refresh();
                    break;
            }
        }
    }

    private void initview(View v) {

        //获取fragment中的控件
        btn_page2_sx = (LinearLayout) v.findViewById(R.id.btn_page2_sx);//筛选按钮
        iv_page2_sx = (ImageView) v.findViewById(R.id.iv_page2_sx);//筛选按钮小箭头

        mPullToRefreshListView = (PullToRefreshListView) v.findViewById(R.id.lgz_ptfl);//带下拉刷新的ListView
        if(turnwatchers==null){
            turnwatchers = new ArrayList<>();
        }
        adapter = new PullToRefreshListViewAdapter(turnwatchers,context);
        mPullToRefreshListView.setAdapter(adapter);
        adapter.setOnDelTurnLisitener(this);

        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//DISABLED BOTH

        // 上拉加载更多，分页加载
//        mPullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
//        mPullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
//        mPullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("刷新中...");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");


        //获取PopuWindow中的控件
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_layout2, null);
        gv_turnwatcher_lgz = (MyGridView) view.findViewById(R.id.gv_turnwatcher_lgz);
        btn_page2_reset=(Button)view.findViewById(R.id.btn_page2_reset);
        btn_page2_sure=(Button)view.findViewById(R.id.btn_page2_sure);
        gv_turnwatcher_lgz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTurnSel(position);
            }
        });

        if(lgz_list==null){
            lgz_list=new ArrayList<>();
        }
        // 设置筛选轮灌组数据
        setTurnSelecorInfo(lgz_list.size());



        //自适配长、框设置
        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.bg_white));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);


    }

    public void setTurnSel(int position) {
        lgz_list.get(position).setIsSelected(!lgz_list.get(position).isSelected());
        lgz_list.get(lastlgzposition).setIsSelected(!lgz_list.get(lastlgzposition).isSelected());
        lastlgzposition=position;
        lgzadapter.notifyDataSetChanged();
        return;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("Page2Fragment","onResume");


    }

    @Override
    public void onPause() {
        super.onPause();
        if (loadDialog != null){
            loadDialog.dismiss();
//            loadDialog = null;
        }
    }

    private void getSelector() {
        // 获取轮灌组条件列表
        TurnInfoListBiz turnInfoListBiz=new TurnInfoListBiz();
        turnInfoListBiz.getinfo(MyApplication.mApplication.getApplicationContext(), new NetCallBack<List<IrrigationTurnInfo>>() {
            @Override
            public void getNetSuccess(int statu, String url, List<IrrigationTurnInfo> irrigationTurnInfos) {
                if (irrigationTurnInfos != null) {
                    lgz_list.clear();
                    lgz_list.addAll(irrigationTurnInfos);
                    IrrigationTurnInfo tempinfo=new IrrigationTurnInfo();
                    tempinfo.setId(0);
                    tempinfo.setTurnname("全部");
                    lgz_list.add(0, tempinfo);
                    lgz_list.get(lastlgzposition).setIsSelected(true);
                    setTurnSelecorInfo(lgz_list.size());

                    if (count == 0){
                        count = 1;
                    }
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mHandler.obtainMessage(GET_SELECTOR_FAIL, "获取筛选条件失败").sendToTarget();
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
//                mHandler.obtainMessage(GET_NET_ERROR, "连接服务器失败").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }

    /**
     * 设置轮灌组筛选条件列表信息
     */
    public void setTurnSelecorInfo(int datasize) {
        int column=3;
        if(datasize>0){

            if(datasize>9){
                column=datasize%3==0?datasize/3:(datasize/3)+1;
            }

            LinearLayout.LayoutParams ddparams = new LinearLayout.LayoutParams(
                    ScreenUtil.getScreenWidth(getActivity()) / 3 * column, LinearLayout.LayoutParams.WRAP_CONTENT);
            gv_turnwatcher_lgz.setLayoutParams(ddparams); // 重点
            gv_turnwatcher_lgz.setNumColumns(column); // 设置列数
            gv_turnwatcher_lgz.setVerticalSpacing(30); // 行 距
        }


        if(lgzadapter==null){
            lgzadapter = new CommUseAdapter<>(context, lgz_list, CommUseAdapter.VIEW_TYPE_SIMPLE_STRING);
            gv_turnwatcher_lgz.setAdapter(lgzadapter);
        }else{
            lgzadapter.notifyDataSetChanged();
        }



    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_page2_sx:
                if (!popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(btn_page2_sx, 0, 0);
                }
                break;
            case R.id.btn_page2_reset:  // 重置
                  setTurnSel(0);
                break;
            case R.id.btn_page2_sure: // 确认
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                 turnId=lgz_list.get(lastlgzposition).getId();
                 loadDialog=DialogUtil.createLoadingDialog(context,"正在查询...");
                getTapWatcher();
                break;
        }
    }


    /**
     * 删除轮灌组
     * @param params
     */
    @Override
    public void delTurn(String params,int pos) {
        loadDialog= DialogUtil.createLoadingDialog(context,"正在删除...");
        delturn(params,pos);
    }

    /**
     * 删除轮灌组
     * @param params  轮灌组阀口信息
     * @param pos 轮灌组位置
     */
    private void delturn(String params,final int pos) {
        if(!TextUtils.isEmpty(params)){
            TurnDelBiz turnDelBiz=new TurnDelBiz();
            turnDelBiz.control(MyApplication.mApplication.getApplicationContext(), params, new NetCallBack<Integer>() {
                @Override
                public void getNetSuccess(int statu, String url, Integer integer) {
                         if(integer==1){
                             turnwatchers.remove(pos);
                             adapter.notifyDataSetChanged();
                             if(lastlgzposition>0){
                                 lastlgzposition=lastlgzposition-1;
                             }else{
                                 lastlgzposition=0;
                             }

                             getSelector();
                             context.refreshPage1();
                             ToastUtil.toast(context,"删除成功");
                         }

                    if(loadDialog!=null){
                        loadDialog.dismiss();
                    }
                }

                @Override
                public void getNetFauiled(int statu, String url) {
                    if(loadDialog!=null){
                        loadDialog.dismiss();
                    }
                }

                @Override
                public void getNetCanceled(int statu, String url) {
                    if(loadDialog!=null){
                        loadDialog.dismiss();
                    }
                }

                @Override
                public void getNetError(int statu, String url) {
                    if(loadDialog!=null){
                        loadDialog.dismiss();
                    }
                    mHandler.obtainMessage(GET_NET_ERROR, "连接服务器失败").sendToTarget();
                }

                @Override
                public void getNetFinished(int statu, String url) {
                    if(loadDialog!=null){
                        loadDialog.dismiss();
                    }
                }
            });
        }else{
               ToastUtil.toast(MyApplication.mApplication.getApplicationContext(), "缺少删除信息");
            if(loadDialog!=null){
                loadDialog.dismiss();
            }
        }

    }

    // 刷新数据
    public void refresh() {
          getTapWatcher();
    }
}
