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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xsyj.irrigation.ChooseAreaActivity;
import com.xsyj.irrigation.FragmentActivity;
import com.xsyj.irrigation.NSURLRequest.GetAreasRequest;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.CommUseAdapter;
import com.xsyj.irrigation.adapter.LGZadapter;
import com.xsyj.irrigation.adapter.PullToRefreshListViewAdapter1;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.AddTurnTaskBiz;
import com.xsyj.irrigation.biz.CircleTaskstBiz;
import com.xsyj.irrigation.biz.DelIrrTurnCommandBiz;
import com.xsyj.irrigation.biz.MaxCircleBiz;
import com.xsyj.irrigation.biz.TurnListByPumpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.customview.MyGridView;
import com.xsyj.irrigation.customview.WheelView;
import com.xsyj.irrigation.entity.IrrigationTaskTurn;
import com.xsyj.irrigation.entity.IrrigationTurnInfo;
import com.xsyj.irrigation.entity.MyNodeBean;
import com.xsyj.irrigation.entity.PumpHouseList;
import com.xsyj.irrigation.entity.PumpListByHouseNum;
import com.xsyj.irrigation.entity.ZQ;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DensityUtil;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ScreenUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Page3Fragment extends Fragment implements View.OnClickListener {

    private FragmentActivity context;
    private LinearLayout btn_page3_sx;
    private ImageView iv_page3_sx;
    private PullToRefreshListView mPullToRefreshListView;
    private List<IrrigationTaskTurn> lgz_set_list;
    private boolean isNull;
    private int lastposition;
    private Button btn_turnset_reset,btn_turnset_sure;
    private int dowhat;  //0:默认状态  1：刷新状态

    public List<IrrigationTaskTurn> getLgz_set_list() {
        return lgz_set_list;
    }

    private PullToRefreshListViewAdapter1 adapter;
    private MyGridView gv_turnZQ;
    // 轮灌周期
    private List<ZQ> ZQ_list;

    private PopupWindow popupWindow;
    private PopupWindow popupWindow1;

    private PopupWindow popupWindow2;
    private WheelView wv_sysb;
    private Button btn_add_cancel;
    private Button btn_add_confirm;


    private ImageButton ib_lgz_page3_add;
    private ListView lv_btn_lgz;
    private LGZadapter lgz_name_adapter;

    private LinearLayout ll_page3_title;
    private CommUseAdapter<ZQ> ZQadapter;

    public int getMaxCircle() {
        return maxCircle;
    }

    private int maxCircle; // 最大周期数

    public int getCurrCircle() {
        return currCircle;
    }

    private int currCircle = 1; // 当前周期数
    private Dialog dialog;
    private TextView tv_cur_circle; // 显示当前周期数
    private List<IrrigationTurnInfo> turnwatchers;

    //区域 首部 机井
    private RelativeLayout view_turnset_area;
    private RelativeLayout view_turnset_sysb;
    private RelativeLayout view_turnset_syjj;

    private TextView tv_turnset_area;
    private TextView tv_turnset_sysb;
    private TextView tv_turnset_syjj;

    private CommonData<List<MyNodeBean>> listCommonData;
    private List<MyNodeBean> myNodeBeanList;
    private List<PumpHouseList> pumpHouseLists;
    private List<PumpListByHouseNum> pumpListByHouseNumList;
    private ScheduledExecutorService scheduledExecutorService;
    private List<String> wells = new ArrayList<String>();

    //WheelView里的两个量
    private int index;  //选择的机井列标
    private String sysb_item="全部";

    private int clickType = 0;

    private String token;
    private String areaId;
    private String areaLevel;
    private String pumphousenum;

    private final int DELIRRTURNCOMM_SUCCESS = 1;
    private final int DELIRRTURNCOMM_FAIL = 2;

    private final int GET_NET_ERROR = 3;

    private String currPump = "水源机井";

    public String getPumpnum() {
        return pumpnum;
    }

    public void setPumpnum(String pumpnum) {
        this.pumpnum = pumpnum;
    }

    private String pumpnum;




    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GetAreasRequest.GETAREAS_SUCCESS:
                    listCommonData = (CommonData<List<MyNodeBean>>) msg.obj;
                    myNodeBeanList = listCommonData.getData();
                    if (myNodeBeanList != null && myNodeBeanList.size() > 0){

                    }else{
                        ToastUtil.toast(context,"没有区域信息");
                    }
                    getMaxCircleR();


                    break;
                case GetAreasRequest.GETAREAS_FAIL:
                    ToastUtil.toast(context,"获取区域信息失败");
                    getMaxCircleR();
//                    getPumpList(token, areaId, areaLevel);
                    break;
                case GetAreasRequest.GETPUMPLISTBYHOUSENUM_SUCCESS:
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    CommonData<List<PumpListByHouseNum>> pumpListByNumData = (CommonData<List<PumpListByHouseNum>>)msg.obj;
                    if (pumpListByNumData != null){
                        if (pumpListByHouseNumList != null){
                            pumpListByHouseNumList.clear();
                        }
                        pumpListByHouseNumList.addAll(pumpListByNumData.getData());
                        PumpListByHouseNum pumpListByHouseNum = new PumpListByHouseNum();
                        pumpListByHouseNum.setPumpname("水源机井");
                        pumpListByHouseNumList.add(0,pumpListByHouseNum);
                    }
                    break;
                case GetAreasRequest.GETPUMPLISTBYHOUSENUM_FAIL:
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    break;
                case GetAreasRequest.GETPUMPHOUSELIST_SUCCESS:
                    CommonData<List<PumpHouseList>> pumpHoseData = (CommonData<List<PumpHouseList>>)msg.obj;
                    if (pumpHoseData != null){
                        if (pumpHouseLists != null){
                            pumpHouseLists.clear();
                        }
                        pumpHouseLists.addAll(pumpHoseData.getData());
                        PumpHouseList pumpHouseList = new PumpHouseList();
                        pumpHouseList.setPumphousename("水源首部");
                        pumpHouseLists.add(0,pumpHouseList);
                    }
                    getPumpListByHouseNum(pumphousenum);
                    break;
                case GetAreasRequest.GETPUMPHOUSELIST_FAIL:
                    getPumpListByHouseNum(pumphousenum);
                    break;
                case DELIRRTURNCOMM_SUCCESS:
                    int res = (int)msg.obj;
                    String message = "";
                    switch (res){
                        case 1:
                            message = "操作成功";
                            getMaxCircleR();
                            break;
                        case 2:
                            message = "操作失败";
                            break;
                        case -1:
                            message = "操作失败网关不在线";
                            break;
                        case 3:
                            message = "已经灌溉完毕";
                            break;
                        default:break;
                    }
                    ToastUtil.toast(context,message);
                    break;
                case DELIRRTURNCOMM_FAIL:
                    ToastUtil.toast(context,msg.obj.toString());
                    break;
                case GET_NET_ERROR:
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    ToastUtil.toast(context,msg.obj.toString());
                    break;
                default:break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_page3_fragment, container, false);
        context = (FragmentActivity) this.getActivity();
        initview(v);
        setlistener();

        return v;
    }

    private void initview(View v) {
        dialog = DialogUtil.createLoadingDialog(context, "正在查询数据...");
        getAreas();

        myNodeBeanList = new ArrayList<MyNodeBean>();
        pumpHouseLists = new ArrayList<PumpHouseList>();
        pumpListByHouseNumList = new ArrayList<PumpListByHouseNum>();

        token = MyApplication.mApplication.getToken();
        areaId = MyApplication.mApplication.getAreaId();
        areaLevel = MyApplication.mApplication.getAreaLevel();

        //获取fragment中的控件
        ll_page3_title = (LinearLayout) v.findViewById(R.id.ll_page3_title);
        btn_page3_sx = (LinearLayout) v.findViewById(R.id.btn_page3_sx);
        iv_page3_sx = (ImageView) v.findViewById(R.id.iv_page3_sx);
        ib_lgz_page3_add = (ImageButton) v.findViewById(R.id.ib_lgz_page3_add);
        tv_cur_circle = (TextView) v.findViewById(R.id.tv_cur_circle);

        mPullToRefreshListView = (PullToRefreshListView) v.findViewById(R.id.lgz_set_list);

        view_turnset_area = (RelativeLayout) v.findViewById(R.id.view_turnset_area);
        view_turnset_sysb = (RelativeLayout) v.findViewById(R.id.view_turnset_sysb);
        view_turnset_syjj = (RelativeLayout) v.findViewById(R.id.view_turnset_syjj);

        tv_turnset_area = (TextView) v.findViewById(R.id.tv_turnset_area);
        tv_turnset_sysb = (TextView) v.findViewById(R.id.tv_turnset_sysb);
        tv_turnset_syjj = (TextView) v.findViewById(R.id.tv_turnset_syjj);

        // 轮灌周期下的任务列表
        if (lgz_set_list == null) {
            lgz_set_list = new ArrayList<>();
        } else {
            lgz_set_list.clear();
        }


        refreshCicleTasks();


        //获取PopuWindow1 任务周期
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_layout3, null);
        gv_turnZQ = (MyGridView) view.findViewById(R.id.gv_turnZQ);

        // 重置轮灌周期选择
        btn_turnset_reset = (Button) view.findViewById(R.id.btn_turnset_reset);

        btn_turnset_sure = (Button) view.findViewById(R.id.btn_turnset_sure);


        // 任务周期列表
        if (ZQ_list == null) {
            ZQ_list = new ArrayList<>();
        } else {
            ZQ_list.clear();
        }
        // 初始化周期lie
        setTurnCircleInfo(0);

        //自适配长、框设置，任务周期
        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.bg_white));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);


        //获取，轮灌组列表 ，显示布局
        View view1 = inflater.inflate(R.layout.pop_menu, null);
        //获取popwindow中的布局的控件
        lv_btn_lgz = (ListView) view1.findViewById(R.id.lv_btn_lgz);

        // 轮灌组条件列表
        if (turnwatchers == null) {
            turnwatchers = new ArrayList<>();
        }

        lgz_name_adapter = new LGZadapter(context, turnwatchers);
        lv_btn_lgz.setAdapter(lgz_name_adapter);

        // 获取加号按钮的位置信息
        int[] arrLoc = ScreenUtil.getLocationOnScreen(ib_lgz_page3_add);
        //自适配长、框设置
        popupWindow1 = new PopupWindow(view1,
                ScreenUtil.getScreenWidth(getActivity()) / 4,
                ScreenUtil.getScreenHeight(getActivity()) - (arrLoc[1] + ib_lgz_page3_add.getHeight()) - DensityUtil.dip2px(getActivity(), 248));
        popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.color.bg_white));
        popupWindow1.setOutsideTouchable(false);
        popupWindow1.setAnimationStyle(R.style.PopumenupAnimation);
        popupWindow1.update();
        popupWindow1.setTouchable(true);
        popupWindow1.setFocusable(true);

        //PopuWindow2内部构造
        LayoutInflater inflater2 = LayoutInflater.from(getActivity());
        View view2 = inflater2.inflate(R.layout.pop_wheel, null);

        //自适配长、框设置
        popupWindow2 = new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow2.setBackgroundDrawable(getResources().getDrawable(R.color.bg_gray));
        popupWindow2.setOutsideTouchable(true);
        popupWindow2.setAnimationStyle(R.style.PopupAnimation);
        popupWindow2.update();
        popupWindow2.setTouchable(true);
        popupWindow2.setFocusable(true);

        btn_add_cancel = (Button) view2.findViewById(R.id.btn_add_cancel);
        btn_add_confirm = (Button) view2.findViewById(R.id.btn_add_confirm);

        //WheelView的使用
        wv_sysb = (WheelView) view2.findViewById(R.id.wv_sysb);
        wv_sysb.setOffset(2);
        wv_sysb.setSeletion(4);
        wv_sysb.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                index = selectedIndex;
                sysb_item = item;
            }
        });


    }

    public void initdata() {
        final AddTurnTaskBiz taskBiz = new AddTurnTaskBiz();
        if (lgz_set_list == null || lgz_set_list.size()==0) {
            ToastUtil.toast(context,"请添加轮灌组计划");
            return;
        }
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < lgz_set_list.size(); i++) {
                    LogUtil.e("lgz_set_list.get(i)" + i, lgz_set_list.get(i).toString());
                    if (lgz_set_list.get(i).getEndtime() == null || lgz_set_list.get(i).getStarttime() == null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isNull = true;
                                ToastUtil.toast(context, "开始或结束时间不能为空");

                            }
                        });
                        break;
                    }
                }
                if (!isNull) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog = DialogUtil.createLoadingDialog(context, "正在添加计划...");
                            taskBiz.getdata(context, gson.toJson(lgz_set_list), new NetCallBack<IrrigationTaskTurn>() {
                                @Override
                                public void getNetSuccess(int statu, String url, IrrigationTaskTurn irrigationTaskTurn) {

//                                    ToastUtil.toast(context, "添加成功");
                                    lgz_set_list.clear();
                                    refreshNewTasks();
                                    if (dialog != null) {
                                        dialog.dismiss();
                                    }

                                    // 获取最大任务周期数
                                    getMaxCircleR();
                                    getTurnList(pumpnum);

                                }

                                @Override
                                public void getNetFauiled(int statu, String url) {
//                                    ToastUtil.toast(context, "添加失败");
                                    if (dialog != null) {
                                        dialog.dismiss();
                                    }
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
                    });
                }

            }
        });
    }

    /**
     * 将机井列表转换成只有名字的字符串列表
     */
    private void handlerWellList() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                if (wells != null){
                    wells.clear();
                }
                switch (clickType){
                    case 0:
                        for (int i = 0; i < pumpHouseLists.size(); i++) {
                            wells.add(pumpHouseLists.get(i).getPumphousename());
                        }
                        break;
                    case 1:
                        for (int i = 0; i < pumpListByHouseNumList.size(); i++) {
                            wells.add(pumpListByHouseNumList.get(i).getPumpname());
                        }
                        break;
                    case 2:
                        break;
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wv_sysb.delViews();
                        wv_sysb.setItems(wells);

                        switch (clickType){
                            case 0:
                                if (!popupWindow2.isShowing()) {
                                    popupWindow2.showAsDropDown(tv_cur_circle, 0, 0);
                                }
                                break;
                            case 1:
                                if (!popupWindow2.isShowing()) {
                                    popupWindow2.showAsDropDown(tv_cur_circle, 0, 0);
                                }
                                break;
                            case 2:
                                break;
                        }
                    }
                });
            }
        });
    }

    private void getAreas() {
        GetAreasRequest.getAreas(context, mHandler);
    }
    // 获取水源列表
    private void getPumpList(String token, String areaId, String areaLevel) {
        GetAreasRequest.getPumpList(getActivity(), token, areaId, areaLevel, mHandler);
    }


    // 获取水源机井
    private void getPumpListByHouseNum(String pumphousenum) {
        GetAreasRequest.getPumpListByHouseNum(getActivity(), pumphousenum, mHandler);
    }


    private void setTurnSel(int position) {
        ZQ_list.get(position).setIsSelected(!ZQ_list.get(position).isSelected());
        ZQ_list.get(lastposition).setIsSelected(!ZQ_list.get(lastposition).isSelected());
        lastposition=position;
        ZQadapter.notifyDataSetChanged();
    }

    /**
     * 刷新周期下的任务数据
     */
    private void refreshCicleTasks() {
        adapter = null;
        adapter = new PullToRefreshListViewAdapter1(lgz_set_list, context, 1);
        mPullToRefreshListView.setAdapter(adapter);
        if(dowhat==1){
            mPullToRefreshListView.onRefreshComplete();
        }
    }

    /**
     * 刷新新建任务数据
     */
    private void refreshNewTasks() {
        adapter = null;
        adapter = new PullToRefreshListViewAdapter1(lgz_set_list, context, 0);
        mPullToRefreshListView.setAdapter(adapter);
    }


    /**
     * 设置轮灌任务周期
     */
    public void setTurnCircleInfo(int datasize) {
        int column = 3;
        if (datasize > 0) {

            if (datasize > 9) {
                column = datasize % 3 == 0 ? datasize / 3 : (datasize / 3) + 1;
            }

            LinearLayout.LayoutParams ddparams = new LinearLayout.LayoutParams(
                    ScreenUtil.getScreenWidth(getActivity()) / 3 * column, LinearLayout.LayoutParams.WRAP_CONTENT);
            gv_turnZQ.setLayoutParams(ddparams); // 重点
            gv_turnZQ.setNumColumns(column); // 设置列数
            gv_turnZQ.setVerticalSpacing(30); // 行 距
        }


        if (ZQadapter == null) {
            ZQadapter = new CommUseAdapter<ZQ>(context, ZQ_list,CommUseAdapter.VIEW_TYPE_SIMPLE_STRING);
            gv_turnZQ.setAdapter(ZQadapter);
        } else {
            ZQadapter.notifyDataSetChanged();
        }


    }


    @Override
    public void onResume() {
        super.onResume();

        // 获取最大任务周期数

//        if (lgz_set_list != null){
//            lgz_set_list.clear();
//            refreshCicleTasks();
//        }
//        getTurnList();

    }



    // 获取轮灌组条件列表
    public void getTurnList(String pumpnum) {
        TurnListByPumpBiz turnListByPumpBiz = new TurnListByPumpBiz();
        turnListByPumpBiz.getinfo(MyApplication.mApplication.getApplicationContext(), pumpnum, new NetCallBack<List<IrrigationTurnInfo>>() {
            @Override
            public void getNetSuccess(int statu, String url, List<IrrigationTurnInfo> irrigationTurnInfos) {
                if (irrigationTurnInfos != null) {

                    turnwatchers.clear();
                    turnwatchers.addAll(irrigationTurnInfos);
                    lgz_name_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void getNetFauiled(int statu, String url) {

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

    /**
     * 获取最大周期数
     */
    private void getMaxCircleR() {
        MaxCircleBiz maxCircleBiz = new MaxCircleBiz();
        maxCircleBiz.getInfo(context, pumpnum, new NetCallBack<Integer>() {
            @Override
            public void getNetSuccess(int statu, String url, Integer integer) {
                ZQ_list.clear();
                maxCircle = integer.intValue() + 1;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (maxCircle == 1) {
                            ZQ zq = new ZQ();
                            zq.setCircleCount(1);
                            ZQ_list.add(zq);
                        } else {
                            for (int i = 1; i <= maxCircle; i++) {
                                ZQ zq = new ZQ();
                                zq.setCircleCount(i);
                                ZQ_list.add(zq);
                            }
                        }

                        lastposition = ZQ_list.size() - 1;
                        ZQ_list.get(lastposition).setIsSelected(true);
                        setTurnCircleInfo(ZQ_list.size());
                        currCircle = maxCircle;
//                        if (!(currCircle < maxCircle)){
                        tv_cur_circle.setText("周期" + ZQ_list.size());
                        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
//                        }
                        if (lgz_set_list != null){
                            lgz_set_list.clear();
                            refreshCicleTasks();
                        }
                        getPumpList(token, areaId, areaLevel);

                    }
                }, 300);
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                if (dialog != null){
                    dialog.dismiss();
                }
                getPumpList(token, areaId, areaLevel);
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

    private void setlistener() {
        btn_page3_sx.setOnClickListener(this);
        ib_lgz_page3_add.setOnClickListener(this);
        btn_turnset_sure.setOnClickListener(this);
        btn_turnset_reset.setOnClickListener(this);

        view_turnset_area.setOnClickListener(this);
        view_turnset_sysb.setOnClickListener(this);
        view_turnset_syjj.setOnClickListener(this);

        btn_add_cancel.setOnClickListener(this);
        btn_add_confirm.setOnClickListener(this);

        // 轮灌组任务列表，滚动
        mPullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                adapter.close();
            }
        });

        // 轮灌组任务列表，刷新
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                dowhat=1;
                getCircleTasks();


            }
        });

        // 周期列表
        gv_turnZQ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTurnSel(position);
                currCircle=ZQ_list.get(position).getCircleCount();
            }
        });

        // 轮灌组列表
        lv_btn_lgz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isAdd = true;
                for (int i = 0;i<lgz_set_list.size();i++){
                     if (turnwatchers.get(position).getId() == lgz_set_list.get(i).getTurnid()){
                         isAdd = false;
                         break;
                     }
                }
                //(turnid,turnname,starttype,starttime,endtime,irrigationcount,state)
                if (isAdd){
                    // 添加数据
                    IrrigationTaskTurn irrigationTaskTurn = new IrrigationTaskTurn();
                    irrigationTaskTurn.setTurnid(turnwatchers.get(position).getId());
                    irrigationTaskTurn.setTurnname(turnwatchers.get(position).getTurnname());
                    irrigationTaskTurn.setStarttype(0);
                    irrigationTaskTurn.setIrrigationcount(maxCircle);
                    irrigationTaskTurn.setState(-1);
                    lgz_set_list.add(irrigationTaskTurn);

                    //生成添加任务布局
                    refreshNewTasks();
                }else{
                    ToastUtil.toast(context,"不能重复添加轮灌组轮灌周期");
                }

            }
        });


        // 当轮灌组布局界面，消失时，改变添加布局的背景图
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ib_lgz_page3_add.setImageResource(R.drawable.lgz_add);
            }
        });


    }

    private void views_state() {
        tv_cur_circle.setText("周期"+String.valueOf(currCircle));
        HomePageFragment homepage = (HomePageFragment) ((FragmentActivity) getActivity()).getSupportFragmentManager().findFragmentByTag("homepagefragment");
        if(currCircle==maxCircle) {
            ib_lgz_page3_add.setVisibility(View.VISIBLE);
            homepage.setTjVisible(true);
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        }else{
            homepage.setTjVisible(false);
            ib_lgz_page3_add.setVisibility(View.GONE);
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_page3_sx:
                if (!popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(btn_page3_sx, 0, 0);
                }
                break;
            case R.id.ib_lgz_page3_add:
                currPump = tv_turnset_syjj.getText().toString();
                if (currPump.equals("水源机井")){
                    ToastUtil.toast(context,"请选择水源机井!");
                }else{
                    ib_lgz_page3_add.setImageResource(R.drawable.lgz_back);
                    if (!popupWindow1.isShowing()) {
                        popupWindow1.showAsDropDown(ib_lgz_page3_add, 0, 0);
                    }
                }

                break;
            case R.id.btn_turnset_reset:  // 轮灌组周期选择重置
                setTurnSel(0);
                break;
            case R.id.btn_turnset_sure:   // 轮灌组周期选择确认

                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                LogUtil.e("currCircle != maxCircle",currCircle != maxCircle);
                if (currCircle != maxCircle) {
                    //获取周期下任务
                    getCircleTasks();
                } else {
                    lgz_set_list.clear();
                    refreshNewTasks();
                }

                views_state();
                break;
            case R.id.view_turnset_area:
                Intent chooseAreaIntent = new Intent(context,ChooseAreaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataList",(Serializable)myNodeBeanList);//序列化,要注意转化(Serializable)
                chooseAreaIntent.putExtras(bundle);
                startActivityForResult(chooseAreaIntent,100);
                break;
            case R.id.view_turnset_sysb:
                sysb_item = "水源首部";
                clickType = 0;
                handlerWellList();
                break;
            case R.id.view_turnset_syjj:
                sysb_item = "水源机井";
                clickType = 1;
                handlerWellList();
                break;
            case R.id.btn_add_confirm:
                popupWindow2.dismiss();
                switch (clickType){
                    case 0:
                        if (index != 0){
                            tv_turnset_sysb.setText(sysb_item);
                            tv_turnset_syjj.setText("水源机井");
                            pumphousenum = pumpHouseLists.get(index - 2).getPumphousenum();
                            pumpnum = "";
                            getMaxCircleR();
                            getPumpListByHouseNum(pumphousenum);
                        }

                        break;
                    case 1:
                        if (index != 0){
                            tv_turnset_syjj.setText(sysb_item);
                            if (!sysb_item.equals("水源机井")){
                                pumpnum = pumpListByHouseNumList.get(index - 2).getPumpnum();
                                String gprs = pumpListByHouseNumList.get(index - 2).getGprs();
                                pumpnum = pumpnum + "_" + gprs;
                                getTurnList(pumpnum);
                                getMaxCircleR();
                            }

                        }

                        break;
                    default:break;
                }

                break;
            case R.id.btn_add_cancel:
                popupWindow2.dismiss();
                break;
            default:break;
        }
    }

    /**
     * 获取周期下任务
     */
    private void getCircleTasks() {
        CircleTaskstBiz circleTaskstBiz = new CircleTaskstBiz();
        circleTaskstBiz.getinfo(MyApplication.mApplication.getApplicationContext(), currCircle, pumpnum, new NetCallBack<List<IrrigationTaskTurn>>() {

            @Override
            public void getNetSuccess(int statu, String url, List<IrrigationTaskTurn> irrigationTaskTurns) {
                List<IrrigationTaskTurn> tempList = irrigationTaskTurns;
                if (tempList != null && tempList.size() > 0) {
                    lgz_set_list.clear();
                    lgz_set_list.addAll(tempList);
                    refreshCicleTasks();
                }else{
                    if(dowhat==1){
                        mPullToRefreshListView.onRefreshComplete();
                    }
                    ToastUtil.toast(context,"无数据");
                }


            }

            @Override
            public void getNetFauiled(int statu, String url) {
                ToastUtil.toast(context,"获取数据失败");

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

    public void delIrrTurnComm(String data){
        DelIrrTurnCommandBiz delIrrTurnCommandBiz = new DelIrrTurnCommandBiz();
        delIrrTurnCommandBiz.getInfo(context, data, new NetCallBack<Integer>() {
            @Override
            public void getNetSuccess(int statu, String url, Integer integer) {
                mHandler.obtainMessage(DELIRRTURNCOMM_SUCCESS, integer).sendToTarget();
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mHandler.obtainMessage(DELIRRTURNCOMM_FAIL, "删除失败").sendToTarget();
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
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 100:
                String currArea = data.getStringExtra("currArea");
                if (TextUtils.isEmpty(currArea)){
                    tv_turnset_area.setText("所属区域");
                }else{
                    areaId = data.getStringExtra("areaId");
                    areaLevel = data.getStringExtra("areaLevel");
                    getPumpList(token,areaId,areaLevel);
                    tv_turnset_area.setText(currArea);
                }
                pumphousenum = "";
                pumpnum = "";
                getMaxCircleR();
                tv_turnset_sysb.setText("水源首部");
                tv_turnset_syjj.setText("水源机井");

                break;
        }
    }
}
