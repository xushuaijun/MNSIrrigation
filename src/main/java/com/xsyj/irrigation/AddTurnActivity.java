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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xsyj.irrigation.NSURLRequest.GetAreasRequest;
import com.xsyj.irrigation.adapter.DialogAdapter;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.GetAreasBiz;
import com.xsyj.irrigation.biz.GetBindTapsBiz;
import com.xsyj.irrigation.biz.PumpHouseListBiz;
import com.xsyj.irrigation.biz.PumpListByHouseNumBiz;
import com.xsyj.irrigation.biz.TurnAddBiz;
import com.xsyj.irrigation.biz.UnBindTapsBiz;
import com.xsyj.irrigation.biz.WellListBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.customview.WheelView;
import com.xsyj.irrigation.entity.GetBindTaps;
import com.xsyj.irrigation.entity.LandPile;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AddTurnActivity extends BaseActivity implements View.OnClickListener {

    private Context context;
    private Dialog builder = null;
    private ListView lv_dialog;

    private EditText et_lgz_name;
    private RadioGroup rg_qdfs;
    private RadioButton rb_auto,rb_manual;
    private TextView tv_btn_sysb,tv_btn_cdz_fk;
    private TextView tv_btn_location;
    private TextView tv_btn_syjj;
    private ImageView iv_style1,iv_style2;
    private ImageView img_location;
    private Button btn_add_cancel,btn_add_confirm;

    private WheelView wv_sysb;
    private WheelView wv_syjj;
    private PopupWindow popupWindow;
    //WheelView里的两个量
    private int index;  //选择的机井列标
    private String sysb_item="全部";
    private List<TapInfo> cdz_list;
    private Dialog loadDialog;


    private DialogAdapter adapter;
    private TextView tv_dialog;
    private DialogAdapter cdzadapter;
    private ScheduledExecutorService scheduledExecutorService;
    private List<WELL> well_list;
    private String wellNum;
    private String turnId;
    private String startType="0"; // 启动方式

    private Map<String,String> unbindedSeled= new HashMap<>();

    public Map<String, String> getUnbindedSeled() {
        return unbindedSeled;
    }

    private StringBuilder tapid_openmouth=new StringBuilder();

    private final int GETAREAS_SUCCESS = 110;
    private final int GETAREAS_FAIL = 112;
    private final int GETPUMPLISTBYHOUSENUM_SUCCESS = 113;
    private final int GETPUMPLISTBYHOUSENUM_FAIL = 114;
    private final int GETPUMPHOUSELIST_SUCCESS = 115;
    private final int GETPUMPHOUSELIST_FAIL = 116;
    private final int GETBINDTAPS_SUCCESS = 7;
    private final int GETBINDTAPS_FAIL = 8;

    private String pumphousenum;
    private String pumpnum;

    private String gprs;

    private CommonData<List<MyNodeBean>> listCommonData;
    private List<MyNodeBean> myNodeBeanList;
    private List<PumpHouseList> pumpHouseLists;
    private List<PumpListByHouseNum> pumpListByHouseNumList;

    public List<GetBindTaps> getBindTapsesList() {
        return bindTapsesList;
    }

    private List<GetBindTaps> bindTapsesList;
    private List<String> pumpHouseStrLists = new ArrayList<String>();
    private List<String> pumpByNumStrLists = new ArrayList<String>();

    private List<String> wells = new ArrayList<String>();

    private int clickType = 0;

    private String token;
    private String areaId;
    private String areaLevel;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case 1: // 确定
                      handlerMap();
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
                    getBindTaps(pumpnum, gprs);
                    break;
                case GETPUMPLISTBYHOUSENUM_FAIL:
                    getBindTaps(pumpnum, gprs);
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
                case GETBINDTAPS_SUCCESS:
                    if (loadDialog != null){
                        loadDialog.dismiss();
                    }
                    CommonData<List<GetBindTaps>> cgetBindTaps =  (CommonData<List<GetBindTaps>>) msg.obj;
                    if (cgetBindTaps != null){
                            if (bindTapsesList != null){
                                bindTapsesList.clear();
                            }
                            bindTapsesList.addAll(cgetBindTaps.getData());
                            LogUtil.e("bindTapsesList",bindTapsesList.toString());
                        }


                    break;
                case GETBINDTAPS_FAIL:
                    if (loadDialog != null){
                        loadDialog.dismiss();
                    }
                    break;
                default:break;
            }
        }
    };

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
        tv_btn_cdz_fk.setText(pile_openmouth.toString());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_turn);
        context = this;
        initview();
        setlistener();
        initdata();
    }

    private void initview() {
        et_lgz_name = (EditText) findViewById(R.id.et_lgz_name);//轮管组名称
        rg_qdfs = (RadioGroup) findViewById(R.id.rg_qdfs);//启动方式
        rb_auto = (RadioButton) findViewById(R.id.rb_auto);//自动启动
        rb_manual = (RadioButton) findViewById(R.id.rb_manual);//手动启动
        tv_btn_sysb = (TextView) findViewById(R.id.tv_btn_sysb);//水源首部选择
        tv_btn_sysb.setText(sysb_item);
        iv_style1 = (ImageView) findViewById(R.id.iv_style1);//尾部标签
        tv_btn_cdz_fk = (TextView) findViewById(R.id.tv_btn_cdz_fk);//出地桩-阀口选择
        iv_style2 = (ImageView) findViewById(R.id.iv_style2);//尾部标签2
        tv_btn_location = (TextView) findViewById(R.id.tv_btn_location);
        img_location = (ImageView) findViewById(R.id.img_location);
        tv_btn_syjj = (TextView) findViewById(R.id.tv_btn_syjj);
        tv_btn_syjj.setText(sysb_item);
        Button btn_turn_save = (Button) findViewById(R.id.btn_turn_save); // 保存轮灌组
        btn_turn_save.setOnClickListener(this);

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
        tv_btn_sysb.setOnClickListener(this);
        tv_btn_cdz_fk.setOnClickListener(this);
        tv_btn_syjj.setOnClickListener(this);

        btn_add_cancel.setOnClickListener(this);
        btn_add_confirm.setOnClickListener(this);
        tv_btn_location.setOnClickListener(this);
        img_location.setOnClickListener(this);
        rg_qdfs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_auto: // 自动
                        startType="0";
                        break;
                    case R.id.rb_manual: // 手动
                        startType="1";
                        break;
                }
            }
        });
    }

    // 初始化数据
    private void initdata() {


        pumpHouseLists = new ArrayList<PumpHouseList>();
        pumpListByHouseNumList = new ArrayList<PumpListByHouseNum>();
        bindTapsesList = new ArrayList<GetBindTaps>();

        loadDialog= DialogUtil.createLoadingDialog(context,"正在查询数据...");
        token = MyApplication.mApplication.getToken();
        areaId = MyApplication.mApplication.getAreaId();
        areaLevel = MyApplication.mApplication.getAreaLevel();
        getAreas();
    }

    // 获取水源列表
    private void getPumpList(String token, String areaId, String areaLevel) {
        GetAreasRequest.getPumpList(getApplicationContext(), token, areaId, areaLevel, mHandler);
    }


    // 获取水源机井
    private void getPumpListByHouseNum(String pumphousenum) {
        GetAreasRequest.getPumpListByHouseNum(getApplicationContext(), pumphousenum, mHandler);
    }

    // 获取出地桩-阀口
    private void getBindTaps(String pumpnum, String gprs) {
        GetBindTapsBiz getBindTapsBiz=new GetBindTapsBiz();
        getBindTapsBiz.getBindTaps(getApplicationContext(), pumpnum, gprs, new NetCallBack<CommonData<List<GetBindTaps>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<GetBindTaps>> listCommonData) {
                mHandler.obtainMessage(GETBINDTAPS_SUCCESS, listCommonData).sendToTarget();
            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mHandler.obtainMessage(GETBINDTAPS_FAIL, "获取出地桩-阀口失败").sendToTarget();
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
                                    popupWindow.showAsDropDown(tv_btn_sysb, 0, 0);
                                }
                                break;
                            case 1:
                                if (!popupWindow.isShowing()) {
                                    popupWindow.showAsDropDown(tv_btn_sysb, 0, 0);
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

    private void getAreas(){
        GetAreasRequest.getAreas(context,mHandler);
//        GetAreasBiz getAreasBiz = new GetAreasBiz();
//        getAreasBiz.getAreas(context, new NetCallBack<CommonData<List<MyNodeBean>>>() {
//            @Override
//            public void getNetSuccess(int statu, String url, CommonData<List<MyNodeBean>> myNodeBeanCommonData) {
//                if (myNodeBeanCommonData != null) {
//
//                    mHandler.obtainMessage(GETAREAS_SUCCESS, myNodeBeanCommonData).sendToTarget();
//                }
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
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(scheduledExecutorService!=null){
            scheduledExecutorService.shutdownNow();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_btn_sysb:
                clickType = 0;
                handlerWellList();

                break;
            case R.id.tv_btn_syjj:
                clickType = 1;
                handlerWellList();
                break;
            case R.id.tv_btn_cdz_fk:  // 点击未绑定阀口
//                if(cdz_list.size()==0){
//                    ToastUtil.toast(getApplicationContext(),"暂时没有多余的阀口可以被绑定");
//                    return;
//                }
//                //声明适配器
//                if(cdzadapter==null){
//                    cdzadapter = new DialogAdapter(cdz_list,this);
//                }else{
//                    cdzadapter.notifyDataSetChanged();
//                }
//
//                //获取dialog自定义布局
//                LayoutInflater inflater = LayoutInflater.from(this);
//                View viewDialog = inflater.inflate(R.layout.dialog, null);
//                //调用显示dialog的方法
//                Dialog dia=ShowListViewDiaLogUtils.showDialog(this, viewDialog,"出地桩-阀口");
//                ShowListViewDiaLogUtils.setDialogView(viewDialog,dia,(TextView)v,cdzadapter,mHandler,1);

                if(bindTapsesList.size()==0){
                    ToastUtil.toast(getApplicationContext(),"暂时没有多余的阀口可以被绑定");
                    return;
                }
                //声明适配器
                if(cdzadapter==null){
                    cdzadapter = new DialogAdapter(bindTapsesList,this);
                }else{
                    cdzadapter.notifyDataSetChanged();
                }

                //获取dialog自定义布局
                LayoutInflater inflater = LayoutInflater.from(this);
                View viewDialog = inflater.inflate(R.layout.dialog, null);
                //调用显示dialog的方法
                Dialog dia=ShowListViewDiaLogUtils.showDialog(this, viewDialog,"出地桩-阀口");
                ShowListViewDiaLogUtils.setDialogView(viewDialog,dia,(TextView)v,cdzadapter,mHandler,1);
                break;
            case R.id.btn_add_confirm:
                popupWindow.dismiss();
                switch (clickType){
                    case 0:
                        if (index != 0){
                            tv_btn_sysb.setText(sysb_item);
                            sysb_item = "全部";
                            pumphousenum = pumpHouseLists.get(index - 2).getPumphousenum();
                            getPumpListByHouseNum(pumphousenum);
                        }

                        break;
                    case 1:
                        if (index != 0){
                            tv_btn_syjj.setText(sysb_item);
                            sysb_item = "全部";
                            pumpnum = pumpListByHouseNumList.get(index - 2).getPumpnum();
                            gprs = pumpListByHouseNumList.get(index - 2).getGprs();
                            getBindTaps(pumpnum, gprs);
                        }

                        break;
                    default:break;
                }

                break;
            case R.id.btn_add_cancel:
                popupWindow.dismiss();
                break;
            case R.id.btn_turn_save:  // 添加轮灌组
                  saveTurn();
                break;
            case R.id.tv_btn_location:
                Intent chooseAreaIntent = new Intent(context,ChooseAreaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataList",(Serializable)myNodeBeanList);//序列化,要注意转化(Serializable)
                chooseAreaIntent.putExtras(bundle);
                startActivityForResult(chooseAreaIntent,100);
                break;
            default:break;
        }
    }

    /**
     * 添加轮灌组
     */
    private void saveTurn() {
        String turnName=et_lgz_name.getText().toString().trim();

        if(TextUtils.isEmpty(turnName)){
            ToastUtil.toast(getApplicationContext(),"轮灌组名称不能为空");
           return;
        }

        if(TextUtils.isEmpty(tapid_openmouth.toString())){
            ToastUtil.toast(getApplicationContext(),"请为轮灌组绑定阀门");
            return;
        }


            loadDialog=DialogUtil.createLoadingDialog(this,"正在保存...");


        TurnAddBiz turnAddBiz=new TurnAddBiz();
        turnAddBiz.control(getApplicationContext(), turnName, startType, tapid_openmouth.toString(), new NetCallBack<Integer>() {
            @Override
            public void getNetSuccess(int statu, String url, Integer integer) {
                 if(integer==1){
                     ToastUtil.toast(getApplicationContext(),"添加成功");
                     setResult(1); //
                     finish();
                 }else if(integer==0){
                     ToastUtil.toast(getApplicationContext(),"添加失败");
                 }else if(integer==2){
                     ToastUtil.toast(getApplicationContext(),"网关未绑定");
                 }else if(integer==-1){
                     ToastUtil.toast(getApplicationContext(),"网关未登录");
                 }else{
                     ToastUtil.toast(context,"出现异常");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 100:
                String currArea = data.getStringExtra("currArea");
                if (TextUtils.isEmpty(currArea)){
                    tv_btn_location.setText("请选择");
                }else{
                    areaId = data.getStringExtra("areaId");
                    areaLevel = data.getStringExtra("areaLevel");
                    getPumpList(token,areaId,areaLevel);
                    tv_btn_location.setText(currArea);
                }

                break;
        }
    }
}
