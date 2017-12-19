package com.xsyj.irrigation;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.xsyj.irrigation.BlueTooth.Bluetooth;
import com.xsyj.irrigation.BlueTooth.MyBTDevice;
import com.xsyj.irrigation.adapter.CommUseAdapter;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.socket.BytesAnd16Code;
import com.xsyj.irrigation.socket.CRC16;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.SocktOrderTools;
import com.xsyj.irrigation.utils.ToastUtil;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class NetBlueSetActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private BluetoothReceiver receiver;
    private List<MyBTDevice> foundDevices=new ArrayList<>();
    private List<String> foundDevicesString=new ArrayList<>();
    private BluetoothAdapter adapter; // 蓝牙适配器
    private Dialog dialog;
    private ImageButton ib_blue_show;
    private PullToRefreshListView prl_blue_list;
    private CommUseAdapter<MyBTDevice> listAdapter;
    private boolean isScaning=true;
    private Bluetooth bluetooth;

    private boolean isRefresh = false;
    private int currPosition = 0;
    private int type = 0;
    private String sendData = "";

    static class MyHandler extends Handler{


        private final WeakReference mActivity;

        public MyHandler(NetBlueSetActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            NetBlueSetActivity activity =(NetBlueSetActivity)mActivity.get();
            if (activity != null) {
               switch (msg.what){
                   case Bluetooth.CONNECT_SUCCESS:
                       SystemClock.sleep(2000);
                       activity.changestate("已连接", msg.arg1,msg.obj);
                       break;
                   case Bluetooth.CONNECT_FAILED:
                       if (activity.type == 1){
                           activity.type = 0;
                           activity.changestate("未连接，请重新修改提交", msg.arg1, msg.obj);
                       }else{
                           activity.changestate("未连接", msg.arg1, msg.obj);
                       }


                       break;
                   case Bluetooth.READ_FAILED:
                       if (activity.type == 1){
                           activity.type = 0;
                           activity.changestate("同步信息失败，请重新修改提交", msg.arg1, msg.obj);
                       }else{
                           activity.changestate("同步信息失败", msg.arg1, msg.obj);
                       }
//                       activity.changestate("同步信息失败",msg.arg1, msg.obj);
                       break;
                   case Bluetooth.WRITE_FAILED:
                       activity.changestate("发送命令失败",msg.arg1, msg.obj);
                       activity.type = 0;
                       break;
                   case Bluetooth.GETSOCKET_FAILED:
                       activity.changestate("蓝牙设备异常",msg.arg1,msg.obj);
                       activity.type = 0;
                       break;
                   case Bluetooth.DATA: // 得到数据信息
                       if(activity.dialog!=null){
                           activity.dialog.dismiss();
                           activity.startActivityForResult(new Intent(MyApplication.mApplication.getApplicationContext(),NetSetActivity.class)
                                   .putExtra("NetPrams", String.valueOf(msg.obj)),100);
                       }
                       break;
                   case Bluetooth.SET_SUCCESS:
//                       activity.changestate("已连接", msg.arg1,msg.obj);
//                       if(activity.dialog!=null){
//                           activity.dialog.dismiss();
//                           activity.startActivityForResult(new Intent(MyApplication.mApplication.getApplicationContext(),NetSetActivity.class)
//                                   .putExtra("NetPrams", String.valueOf(msg.obj)),100);
//                       }
                       activity.dialog.dismiss();
                       activity.type = 0;
                       ToastUtil.toast(activity,"修改成功");
//                       activity.changestate("已连接", msg.arg1,msg.obj);
//                       activity.sendMessage(msg.arg1, (Bluetooth)msg.obj, , 1);
                       break;
               }
            }
        }
    }


    /**
     * 改变蓝牙设备显示状态
     * @param state
     */
    private void changestate(String state,final int position,final Object object) {
        synchronized (this){
            try {
                foundDevices.get(position).setBtd_state(state);
                showDevices();
                dialog.dismiss();
                if("已连接".equals(state)){

                    sendMessage(position, (Bluetooth) object, sendData, type);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 发送命令
     * @param position
     * @param object
     */
    public void sendMessage(final int position, final Bluetooth object , final String data, final int type) {
        dialog= DialogUtil.createLoadingDialog(this, "正在同步数据...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                // 拼接读取网关配置参数命令
                StringBuffer sb = new StringBuffer();
                String head = "88FBFA";
                sb.append(head); // 帧头，发起帧
                String netcode = SocktOrderTools.getNetCode16(foundDevices.get(position).getBtd().getName(), true);//"00490000";
                String checkData = "09336101".concat(netcode); // 帧长度,帧代号，控制字，设备地址
                if (type == 1){
                    int dataNum = data.length()/2 + 9; //帧长度 = 帧代号 + 控制字 + 设备地址 + 数据 + 校验； 9 + 数据字节长度
                    checkData = Integer.toHexString(dataNum) + "316101".concat(netcode);
                }
                sb.append(checkData);
                if (type == 1 && !TextUtils.isEmpty(data))
                  sb.append(data);
                if (type == 1)
                  sb.append(SocktOrderTools.getCRC_16(CRC16.calcCrc16(BytesAnd16Code.hexStringToBytes(checkData + data))));// 校验
                else
                    sb.append(SocktOrderTools.getCRC_16(CRC16.calcCrc16(BytesAnd16Code.hexStringToBytes(checkData))));

                sb.append("FCFC"); // 帧尾

                // 设置网关参数
               /*  String crc=SocktOrderTools.getCRC_16(CRC16.calcCrc16(BytesAnd16Code.hexStringToBytes("17316101".concat(netcode).concat("DC711052591B434D4E45543C"))));
                ((Bluetooth) object).sendmessage("88FBFA17316101".concat(netcode).concat("DC711052591B").concat("434D4E4554").concat("3C").concat(crc).concat("FCFC"), MyApplication.mApplication.getApplicationContext(), position);*/

                String result = sb.toString().toUpperCase();
                //读取网关配置信息
                object.sendmessage(result, MyApplication.mApplication.getApplicationContext(), position , type);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_blue_set);

        // 注册蓝牙广播接收器
        regesiterBlueDiscovering();

        //初始化控件
        initView();


        //开始扫描周围网关设备
        startingScan();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        ib_blue_show=(ImageButton)findViewById(R.id.ib_blue_show);
        prl_blue_list=(PullToRefreshListView)findViewById(R.id.prl_blue_list);
        prl_blue_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listAdapter=new  CommUseAdapter<>(this,foundDevices,CommUseAdapter.VIEW_TYPE_SIMPLE_STRING);
        prl_blue_list.setAdapter(listAdapter);
        prl_blue_list.setMode(PullToRefreshBase.Mode.BOTH);
        prl_blue_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新触发的事件

                isRefresh = true;
                foundDevices.clear();
                showDevices();
                startingScan();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉加载

            }

        });

        prl_blue_list.setOnItemClickListener(this);

    }

    // 动画实际执行
    private void startPropertyAnim(final View v) {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
       final ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", 0f, 360f);

        // 动画的持续时间，执行多久？
        anim.setDuration(1000);

        // 回调监听
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // LogUtil.e("zhangphil", value + "");
            }
        });

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (isScaning) {
                    startPropertyAnim(v);
                    if (isRefresh){
                        prl_blue_list.onRefreshComplete();
                        isRefresh = false;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        // 正式开始启动执行动画
        anim.start();
    }



    /**
     * 开始扫描周围网关设备
     */
    private void startingScan() {
        ToastUtil.toast(MyApplication.mApplication.getApplicationContext(), "正在搜索设备...");

        startPropertyAnim(ib_blue_show);

        //1. 得到BluetoothAdapter 对象
        if(adapter==null){
            adapter=BluetoothAdapter.getDefaultAdapter();
        }

        if(adapter!=null){
            // 判断当前蓝牙设备设备是否可用
            if(!adapter.isEnabled()){
//                adapter.enable(); // 开启蓝牙
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 10);
			/*	Intent in=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(in);*/

                /*else if(adapter.isDiscovering()){
                System.out.println( "正在搜索周边蓝牙设备");
            }*/
            }else{
                startDiscovery();
            }


        }else{
           ToastUtil.toast(MyApplication.mApplication.getApplicationContext(),"本设备没有蓝牙设备");
        }

    }

    /**
     *  注册监听广播接收器，监听发现蓝牙设备
     */
    private void regesiterBlueDiscovering() {

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BluetoothReceiver();
        registerReceiver(receiver, filter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         position=position-1;
         currPosition = position;
         bluetoothConnect(position);
//        startActivityForResult(new Intent(MyApplication.mApplication.getApplicationContext(),NetSetActivity.class)
//                .putExtra("NetPrams", String.valueOf(msg.obj)),100);

    }

    private void bluetoothConnect(int position){

        MyBTDevice myBTDevice=foundDevices.get(position);
        if (bluetooth == null){
            dialog= DialogUtil.createLoadingDialog(this,"正在连接...");
            bluetooth=new Bluetooth(myBTDevice.getBtd(),new MyHandler(this));
            bluetooth.connect(position);
        }else{
            if("已连接".equals(myBTDevice.getBtd_state()) && bluetooth.isConnect()){
//                bluetooth.setConnect(true);
                sendMessage(position,bluetooth, sendData, type);
            }else{
                dialog= DialogUtil.createLoadingDialog(this,"正在连接...");
                bluetooth.connect(position);
            }
        }



    }

    /**
     * 蓝牙搜索广播接收器
     */
    private class BluetoothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println("BluetoothReceiver:"+action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                MyBTDevice mybtdevice=new MyBTDevice(device, "未连接");
                if (!isExist(mybtdevice)) {
                    LogUtil.e("BLUETOOTH设备", device.getName() + ";" + device.getAddress());
                    foundDevices.add(mybtdevice);
                    foundDevicesString.add(mybtdevice.getBtd().getName());
                   // LogUtil.e("BLUETOOTH设备", devic
                   // e.getName() + ";" + device.getAddress());
                }

            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){  // 扫描结束
                 isScaning=false;

                ToastUtil.toast(context, "扫描可用设备完毕");
            }

            // 展示搜索到的蓝牙设备
            showDevices();
        }
    }

    /**
     * 展示蓝牙设备
     */
    private void showDevices() {
           listAdapter.notifyDataSetChanged();
    }

    /**
     *
     * @param mybtdevice
     * @return
     */
    private boolean isExist(MyBTDevice mybtdevice) {

        boolean isExistDevice = foundDevicesString.indexOf(mybtdevice.getBtd().getName()) != -1;
        return isExistDevice;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                // 获取已经配对过的设备
                startDiscovery();
                break;
            default:break;
        }
        if (requestCode == 100){//dc711052591b434d4e45540a
            if (data != null){
                String sendMsg = data.getStringExtra("sendMsg");
                sendData = sendMsg;
                type = 1;
                bluetoothConnect(currPosition);
            }

//            if (bluetooth == null){

//            }
//            else{
//
//                sendMessage(currPosition,bluetooth, sendMsg, 1);
//            }

        }
    }

    private void startDiscovery(){
        List<MyBTDevice> bongList = Bluetooth.getBlueTooths(MyApplication.mApplication.getApplicationContext(), adapter);
        if(bongList!=null && bongList.size()>0){
            foundDevices.addAll(bongList);
        }
        // 开启搜索蓝牙设备
        adapter.startDiscovery(); // 扫描可用设备


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isScaning=false;
        unregisterReceiver(receiver);
        if(bluetooth!=null){
            bluetooth.disconnect();
        }
        if (dialog != null){
            dialog.dismiss();
        }
        adapter.disable();
    }
}
