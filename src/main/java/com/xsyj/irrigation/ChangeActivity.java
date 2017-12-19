package com.xsyj.irrigation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xsyj.irrigation.NSURLRequest.GetAreasRequest;
import com.xsyj.irrigation.adapter.ChangeUnBindedAdapter;
import com.xsyj.irrigation.adapter.DialogAdapter;
import com.xsyj.irrigation.adapter.TapBindedAdapter;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.BindedTapsBiz;
import com.xsyj.irrigation.biz.GetBindTapsBiz;
import com.xsyj.irrigation.biz.PumpHouseListBiz;
import com.xsyj.irrigation.biz.PumpListByHouseNumBiz;
import com.xsyj.irrigation.biz.TurnChangeBiz;
import com.xsyj.irrigation.biz.UnBindTapsBiz;
import com.xsyj.irrigation.biz.WellListBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.customview.WheelView;
import com.xsyj.irrigation.entity.GetBindTaps;
import com.xsyj.irrigation.entity.MyNodeBean;
import com.xsyj.irrigation.entity.PumpHouseList;
import com.xsyj.irrigation.entity.PumpListByHouseNum;
import com.xsyj.irrigation.entity.TapInfo;
import com.xsyj.irrigation.entity.WELL;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ShowListViewDiaLogUtils;
import com.xsyj.irrigation.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ChangeActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup rg_change_qdfs;
    private RadioButton rb_change_auto;
    private RadioButton rb_change_manual;
    private TextView tv_btn_change_sysb,tv_btn_wbd_change_fk,tv_btn_cdz_change_fk;
    private TextView tv_btn_change_ssqy;
    private TextView tv_btn_change_syjj;

    private ImageView iv_change_style1,iv_change_style0,iv_change_style2;
    private Button btn_change_save;
    private PopupWindow popupWindow;
    private Button btn_add_cancel;
    private Button btn_add_confirm;
    private WheelView wv_sysb;
    private int index;
    private String sysb_item="全部";
    private List<TapInfo> cdz_list;  // 未绑定的阀口
    private Dialog loadDialog;

    public List<TapInfo> getCdz_list() {
        return cdz_list;
    }
    private List<TapInfo> binded_list; // 已经被绑定的阀口

    public List<TapInfo> getBinded_list() {
        return binded_list;
    }



    private ChangeUnBindedAdapter cdzadapter;
    private String wellNum;
    private String turnId;
    private List<WELL> well_list;  // 机井列表
    private ScheduledExecutorService scheduledExecutorService;
    private String turnName;
    private TapBindedAdapter bindedAdapter;
    private TextView tv_change_name;
    private String startType="0";  // 启动方式


    /**
     * 存储选择的未绑定的阀门信息
     */
    private Map<String,String> unbindedSeled= new HashMap<>();
    public Map<String, String> getUnbindedSeled() {
        return unbindedSeled;
    }
    private StringBuilder tapid_openmouth=new StringBuilder();
    private StringBuilder cancel_tapid_openmouth=new StringBuilder();
    /**
     * 存储取消的绑定的阀门信息
     */
    private Map<String,String> cancelbindedSeled= new HashMap<>();
    public Map<String, String> getcancelbindedSeled() {
        return cancelbindedSeled;
    }

    private CommonData<List<MyNodeBean>> listCommonData;
    private List<MyNodeBean> myNodeBeanList;
    private Context context;
    private final int GETAREAS_SUCCESS = 110;
    private final int GETAREAS_FAIL = 112;
    private final int GETPUMPLISTBYHOUSENUM_SUCCESS = 113;
    private final int GETPUMPLISTBYHOUSENUM_FAIL = 114;
    private final int GETPUMPHOUSELIST_SUCCESS = 115;
    private final int GETPUMPHOUSELIST_FAIL = 116;
    private final int GETUNBINDTAPS_SUCCESS = 7;
    private final int GETUNBINDTAPS_FAIL = 8;

    private String token;
    private String areaId;
    private String areaLevel;
    private String pumphousenum;

    private String gprs;

    private List<PumpHouseList> pumpHouseLists;
    private List<PumpListByHouseNum> pumpListByHouseNumList;
    private List<GetBindTaps> bindTapsesList;
    private List<String> wells = new ArrayList<String>();
    private int clickType;


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case 1: // 确定   处理未绑定的阀口
                    handlerMap();
                    break;
                case 2:   // 确定 处理已经绑定的阀口
                    handlerUnBindMap();
                    break;
                case GETAREAS_SUCCESS:
                    listCommonData = (CommonData<List<MyNodeBean>>) msg.obj;
                    myNodeBeanList = listCommonData.getData();
                    if (myNodeBeanList != null && myNodeBeanList.size() > 0){

                    }else{
//                        tv_btn_location.setText(currArea);
                        ToastUtil.toast(context,"没有区域信息");
                    }
                    getPumpList(token, areaId, areaLevel);
                    break;
                case GETAREAS_FAIL:
                    if (loadDialog != null){
                        loadDialog.dismiss();
                    }
                    ToastUtil.toast(context,"获取区域信息失败");
                    getPumpList(token, areaId, areaLevel);
                    break;
                case GETPUMPLISTBYHOUSENUM_SUCCESS:
                    CommonData<List<PumpListByHouseNum>> pumpListByNumData = (CommonData<List<PumpListByHouseNum>>)msg.obj;
                    if (pumpListByNumData != null){
                        if (pumpListByHouseNumList != null){
                            pumpListByHouseNumList.clear();
                        }
                        pumpListByHouseNumList.addAll(pumpListByNumData.getData());
                        PumpListByHouseNum pumpListByHouseNum = new PumpListByHouseNum();
                        pumpListByHouseNum.setPumpname("全部");
                        pumpListByHouseNumList.add(0,pumpListByHouseNum);
                    }
                    getUnBindTaps();
                    break;
                case GETPUMPLISTBYHOUSENUM_FAIL:
                    getUnBindTaps();
                    break;
                case GETPUMPHOUSELIST_SUCCESS:
                    CommonData<List<PumpHouseList>> pumpHoseData = (CommonData<List<PumpHouseList>>)msg.obj;
                    if (pumpHoseData != null){
                        if (pumpHouseLists != null){
                            pumpHouseLists.clear();
                        }
                        pumpHouseLists.addAll(pumpHoseData.getData());
                        PumpHouseList pumpHouseList = new PumpHouseList();
                        pumpHouseList.setPumphousename("全部");
                        pumpHouseLists.add(0,pumpHouseList);
                    }
                    getPumpListByHouseNum(pumphousenum);
                    break;
                case GETPUMPHOUSELIST_FAIL:
                    getPumpListByHouseNum(pumphousenum);
                    break;
                case GETUNBINDTAPS_SUCCESS:
                    if (loadDialog != null){
                        loadDialog.dismiss();
                    }
                    break;
                case GETUNBINDTAPS_FAIL:
                    if (loadDialog != null){
                        loadDialog.dismiss();
                    }
                    break;
                default:break;
            }
        }
    };

    /**
     * 将取消的已绑定的阀口进行处理，显示到文本上
     */
    private void handlerUnBindMap() {
        // 清空字符串
        cancel_tapid_openmouth.delete(0, tapid_openmouth.length());
        StringBuilder pile_openmouth=new StringBuilder();
        Iterator iter = cancelbindedSeled.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if("".equals(pile_openmouth.toString())){
                if(String.valueOf(val).endsWith("A")){
                    pile_openmouth.append(String.valueOf(key).concat("-A"));
                }else{
                    pile_openmouth.append(String.valueOf(key).concat("-B"));
                }

            }else{
                if(String.valueOf(val).endsWith("A")){
                    pile_openmouth.append(";").append(String.valueOf(key).concat("-A"));
                }else{
                    pile_openmouth.append(";").append(String.valueOf(key).concat("-B"));
                }

            }

            if("".equals(cancel_tapid_openmouth.toString())){
                cancel_tapid_openmouth.append(String.valueOf(val));
            }else{
                cancel_tapid_openmouth.append(";").append(String.valueOf(val));
            }

        }
        tv_btn_cdz_change_fk.setText(pile_openmouth.toString());
    }


    /**
     * 将选中的阀口进行处理，显示到文本框上
     */
    private void handlerMap() {
        // 清空字符串
        tapid_openmouth.delete(0, tapid_openmouth.length());
        StringBuilder pile_openmouth=new StringBuilder();
        Iterator iter = unbindedSeled.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if("".equals(pile_openmouth.toString())){
                if(String.valueOf(val).endsWith("A")){
                    pile_openmouth.append(String.valueOf(key).concat("-A"));
                }else{
                    pile_openmouth.append(String.valueOf(key).concat("-B"));
                }

            }else{
                if(String.valueOf(val).endsWith("A")){
                    pile_openmouth.append(";").append(String.valueOf(key).concat("-A"));
                }else{
                    pile_openmouth.append(";").append(String.valueOf(key).concat("-B"));
                }

            }

            if("".equals(tapid_openmouth.toString())){
                tapid_openmouth.append(String.valueOf(val));
            }else{
                tapid_openmouth.append(";").append(String.valueOf(val));
            }

        }
        tv_btn_wbd_change_fk.setText(pile_openmouth.toString());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        context = this;
        initview();
        setlistener();
        initdata();
    }

    private void initdata() {

        pumpHouseLists = new ArrayList<PumpHouseList>();
        pumpListByHouseNumList = new ArrayList<PumpListByHouseNum>();
        bindTapsesList = new ArrayList<GetBindTaps>();

        loadDialog= DialogUtil.createLoadingDialog(context,"正在查询数据...");
        token = MyApplication.mApplication.getToken();
        areaId = MyApplication.mApplication.getAreaId();
        areaLevel = MyApplication.mApplication.getAreaLevel();
        getAreas();
        wellNum=""; // 机井编号
        turnName=getIntent().getStringExtra("turnName");
        tv_change_name.setText(turnName);
        startType = getIntent().getStringExtra("startType");
        turnId=getIntent().getStringExtra("turnId")==null?"0":getIntent().getStringExtra("turnId");
        if (startType.equals("0"))
            rb_change_auto.setChecked(true);
        else{
            rb_change_manual.setChecked(true);
        }

        if(well_list==null){
            well_list=new ArrayList<>();
        }else {
            well_list.clear();
        }

//        getWellList();  // 获取水源井

        if(cdz_list==null){
            cdz_list=new ArrayList<>();
        }else{
            cdz_list.clear();
        }
//        getUnBindTaps();  // 获取未绑定的阀口


         // 已经被绑定的阀口
        if(binded_list==null){
            binded_list=new ArrayList<>();
        }else{
            binded_list.clear();
        }

       getBindTaps(); // 获取轮灌组下已经被绑定的阀口

    }

    /**
     * 获取轮灌组下绑定的阀口
     */
    private void getBindTaps() {
        BindedTapsBiz bindedTapsBiz=new BindedTapsBiz();
        bindedTapsBiz.getinfo(getApplicationContext(), turnId, new NetCallBack<List<TapInfo>>() {
            @Override
            public void getNetSuccess(int statu, String url, List<TapInfo> tapInfos) {
                if (tapInfos != null) {
                    binded_list.clear();
                    binded_list.addAll(tapInfos);
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

            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }

    // 获取未绑定的阀口
    private void getUnBindTaps() {
        UnBindTapsBiz unBindTapsBiz=new UnBindTapsBiz();
        unBindTapsBiz.getinfo(getApplicationContext(), wellNum, turnId, gprs, new NetCallBack<List<TapInfo>>() {
            @Override
            public void getNetSuccess(int statu, String url, List<TapInfo> tapInfos) {
                if (tapInfos != null) {
                    cdz_list.clear();
                    cdz_list.addAll(tapInfos);
                }
//                mHandler.obtainMessage(GETUNBINDTAPS_SUCCESS, tapInfos).sendToTarget();
                if (loadDialog != null){
                    loadDialog.dismiss();
                }

            }

            @Override
            public void getNetFauiled(int statu, String url) {

//                mHandler.obtainMessage(GETUNBINDTAPS_FAIL, "获取未绑定阀口失败").sendToTarget();
                if (loadDialog != null){
                    loadDialog.dismiss();
                }
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

//    // 获取水源井
//    private void getWellList() {
//        WellListBiz wellListBiz=new WellListBiz();
//        wellListBiz.getinfo(getApplicationContext(), new NetCallBack<List<WELL>>() {
//            @Override
//            public void getNetSuccess(int statu, String url, List<WELL> wells) {
//                List<WELL> templist = wells;
//                if (templist != null && templist.size() > 0) {
//                    WELL well = new WELL();
//                    well.setWellName("全部");
//                    well_list.add(well);
//                    well_list.addAll(templist);
//
//                } else {
//                    WELL well = new WELL();
//                    well.setWellName("全部");
//                    well_list.add(well);
//                }
//
//                handlerWellList(); // 转换成字符串集合
//
//            }
//
//            @Override
//            public void getNetFauiled(int statu, String url) {
//
//            }
//
//            @Override
//            public void getNetCanceled(int statu, String url) {
//
//            }
//
//            @Override
//            public void getNetError(int statu, String url) {
//
//            }
//
//            @Override
//            public void getNetFinished(int statu, String url) {
//
//            }
//        });
//    }

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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wv_sysb.delViews();
                        wv_sysb.setItems(wells);

                        switch (clickType){
                            case 0:
                                if (!popupWindow.isShowing()) {
                                    popupWindow.showAsDropDown(tv_btn_change_sysb, 0, 0);
                                }
                                break;
                            case 1:
                                if (!popupWindow.isShowing()) {
                                    popupWindow.showAsDropDown(tv_btn_change_sysb, 0, 0);
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


    @Override
    protected void onStop() {
        super.onStop();
        if(scheduledExecutorService!=null){
            scheduledExecutorService.shutdownNow();
        }
    }

    private void initview() {

        tv_change_name = (TextView) findViewById(R.id.tv_change_name);//轮灌组名称
        rg_change_qdfs = (RadioGroup) findViewById(R.id.rg_change_qdfs);//启动方式组
        rb_change_auto = (RadioButton) findViewById(R.id.rb_change_auto);//自动启动
        rb_change_manual = (RadioButton) findViewById(R.id.rb_change_manual);//手动启动

        tv_btn_change_syjj = (TextView) findViewById(R.id.tv_btn_change_syjj);
        tv_btn_change_ssqy = (TextView) findViewById(R.id.tv_btn_change_ssqy);
        tv_btn_change_sysb = (TextView) findViewById(R.id.tv_btn_change_sysb);//水源首部
        iv_change_style1 = (ImageView) findViewById(R.id.iv_change_style1);//水源首部尾部标签
        tv_btn_wbd_change_fk = (TextView) findViewById(R.id.tv_btn_wbd_change_fk);//未绑定阀口
        iv_change_style0 = (ImageView) findViewById(R.id.iv_change_style0);//未绑定阀口尾部标签
        tv_btn_cdz_change_fk = (TextView) findViewById(R.id.tv_btn_cdz_change_fk);//当前组下阀口
        iv_change_style2 = (ImageView) findViewById(R.id.iv_change_style2);//当前足下阀口尾部标签

        btn_change_save = (Button) findViewById(R.id.btn_change_save);//保存按钮


        //PopuWindow内部构造
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.pop_wheel, null);

        //自适配长、框设置
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.bg_gray));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        btn_add_cancel = (Button) view.findViewById(R.id.btn_add_cancel);
        btn_add_confirm = (Button) view.findViewById(R.id.btn_add_confirm);

        //WheelView的使用
        wv_sysb = (WheelView) view.findViewById(R.id.wv_sysb);
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

    private void setlistener() {

        tv_btn_change_syjj.setOnClickListener(this);
        tv_btn_change_ssqy.setOnClickListener(this);
        tv_btn_change_sysb.setOnClickListener(this);
        tv_btn_wbd_change_fk.setOnClickListener(this);
        tv_btn_cdz_change_fk.setOnClickListener(this);
        btn_change_save.setOnClickListener(this);
        btn_add_cancel.setOnClickListener(this);
        btn_add_confirm.setOnClickListener(this);

        rg_change_qdfs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_change_auto: // 自动
                        startType = "0";
                        break;
                    case R.id.rb_change_manual: // 手动
                        startType = "1";
                        break;
                    default:break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_btn_change_sysb:
                clickType = 0;
                handlerWellList();
//                if (!popupWindow.isShowing()) {
//                    popupWindow.showAsDropDown(tv_btn_change_sysb, 0, 0);
//                }
                break;
            case R.id.tv_btn_wbd_change_fk:  // 未绑定的阀口

                if(cdz_list.size()==0){
                    ToastUtil.toast(getApplicationContext(),"暂时没有多余的阀口可以被绑定");
                    return;
                }

                if(cdzadapter==null){
                    cdzadapter = new ChangeUnBindedAdapter(cdz_list,this);
                }else{
                    cdzadapter.notifyDataSetChanged();
                }

                LayoutInflater inflater = LayoutInflater.from(this);
                View viewDialog = inflater.inflate(R.layout.dialog, null);

                Dialog dia=ShowListViewDiaLogUtils.showDialog(this, viewDialog, "出地桩-阀口");
                ShowListViewDiaLogUtils.setDialogView(viewDialog, dia, (TextView) v, cdzadapter,mHandler,1);

                break;
            case R.id.tv_btn_cdz_change_fk:  // 已经绑定的阀口

                if(bindedAdapter==null){
                    bindedAdapter = new TapBindedAdapter(binded_list,this);
                }else{
                    bindedAdapter.notifyDataSetChanged();
                }

                LayoutInflater inflater1 = LayoutInflater.from(this);
                View viewDialog1 = inflater1.inflate(R.layout.dialog, null);

                Dialog dia1=ShowListViewDiaLogUtils.showDialog(this, viewDialog1, "出地桩-阀口");
                ShowListViewDiaLogUtils.setDialogView(viewDialog1, dia1, (TextView) v, bindedAdapter,mHandler,2);
                break;
            case R.id.btn_add_cancel:
                popupWindow.dismiss();
                break;
            case R.id.btn_add_confirm:
                popupWindow.dismiss();
                switch (clickType) {
                    case 0:
                        if (index != 0) {
                            tv_btn_change_sysb.setText(sysb_item);
                            sysb_item = "全部";
                            pumphousenum = pumpHouseLists.get(index - 2).getPumphousenum();
                            getPumpListByHouseNum(pumphousenum);
                        }

                        break;
                    case 1:
                        if (index != 0) {
                            tv_btn_change_syjj.setText(sysb_item);
                            sysb_item = "全部";
                            wellNum = pumpListByHouseNumList.get(index - 2).getPumpnum();
                            gprs = pumpListByHouseNumList.get(index - 2).getGprs();
                            getUnBindTaps();
                        }
                }
                break;
            case R.id.btn_change_save:  // 保存修改
                    changeTurn();
                break;
            case R.id.tv_btn_change_ssqy:
                Intent chooseAreaIntent = new Intent(context,ChooseAreaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataList",(Serializable)myNodeBeanList);//序列化,要注意转化(Serializable)
                chooseAreaIntent.putExtras(bundle);
                startActivityForResult(chooseAreaIntent,100);
                break;
            case R.id.tv_btn_change_syjj:
                clickType = 1;
                handlerWellList();
                break;
            default:break;
        }
    }

    /**
     * 保存修改
     */
    private void changeTurn() {

        loadDialog= DialogUtil.createLoadingDialog(this, "正在修改...");
        TurnChangeBiz turnChangeBiz=new TurnChangeBiz();
        turnChangeBiz.control(getApplicationContext(), turnId, startType, tapid_openmouth.toString(), cancel_tapid_openmouth.toString(), new NetCallBack<Integer>() {
            @Override
            public void getNetSuccess(int statu, String url, Integer integer) {
                if(integer==1){

                    ToastUtil.toast(getApplicationContext(),"修改成功");
                }
                if(loadDialog!=null){
                    loadDialog.dismiss();
                }
                setResult(2);
                finish();

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
            }

            @Override
            public void getNetFinished(int statu, String url) {
                if(loadDialog!=null){
                    loadDialog.dismiss();
                }
            }
        });
    }

    public void refreshUnBindTapinfo(){
        cdzadapter.notifyDataSetChanged();
    }
    private void getAreas() {
        GetAreasRequest.getAreas(context, mHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 100:
                String currArea = data.getStringExtra("currArea");
                if (TextUtils.isEmpty(currArea)){
                    tv_btn_change_ssqy.setText("请选择");
                }else{
                    areaId = data.getStringExtra("areaId");
                    areaLevel = data.getStringExtra("areaLevel");
//                    getPumpList(token,areaId,areaLevel);
                    tv_btn_change_ssqy.setText(currArea);
                }

                break;
        }
    }

    // 获取水源列表
    private void getPumpList(String token, String areaId, String areaLevel) {
        GetAreasRequest.getPumpList(getApplicationContext(), token, areaId, areaLevel, mHandler);
    }


    // 获取水源机井
    private void getPumpListByHouseNum(String pumphousenum) {
        GetAreasRequest.getPumpListByHouseNum(getApplicationContext(), pumphousenum, mHandler);
    }
}
