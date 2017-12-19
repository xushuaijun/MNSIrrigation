package com.xsyj.irrigation;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.SelectApkVersionBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.AppVersion;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;
import com.xsyj.irrigation.utils.ApkDownLoadUtil;
import com.xsyj.irrigation.utils.DataCleanManager;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.DownLoadUtil;
import com.xsyj.irrigation.utils.SharePrefrenceUtil;
import com.xsyj.irrigation.utils.ToastUtil;
import com.xsyj.irrigation.utils.UpdateService;
import com.xsyj.irrigation.utils.VersionUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManageCenterActivity extends BaseActivity implements View.OnClickListener {

    private Context context;
    private TextView manager_username;
    private Button btn_exit;
    private TextView tv_file_size;
    private LinearLayout clear_view;
    private LinearLayout checkVersion;

    private List<AppVersion> appVersionList = new ArrayList<AppVersion>();
    private AppVersion appVersion;

    private boolean isConnect = false;

    private String apkUrl;
    private String filepath;

    private ApkDownLoadUtil.MyBinder myBinder;

    private final int SELECTAPKVER_SUCCESS = 0;
    private final int SELECTAPKVER_FAIL = 1;
    private final int GET_NET_ERROR = 2;
    private final int MSG_FILE_DOWN_SUCCEED = 7; // 客户端下载成功
    private final int MSG_FILE_DOWN_FAIL = 8; // 客户端下载失败

    private Dialog dialog;
//    private NotificationCompat.Builder builder ;
    //定义notification实用的ID
//    private static final int NO_3 =1;
//    NotificationManager manager ;
    private DownLoadUtil downloadUtils;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SELECTAPKVER_SUCCESS:
                    CommonData<List<AppVersion>> listCommonData = (CommonData<List<AppVersion>>)msg.obj;
                    appVersionList = listCommonData.getData();
                    if (appVersionList != null && appVersionList.size() > 0){
                        appVersion = appVersionList.get(0);
                    }
                    checkVision();
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    break;
                case SELECTAPKVER_FAIL:
                    ToastUtil.toast(context, msg.obj.toString());
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    break;
                case GET_NET_ERROR:
                    ToastUtil.toast(context, msg.obj.toString());
                    if (dialog != null){
                        dialog.dismiss();
                    }
                    break;
                case MSG_FILE_DOWN_SUCCEED: // 新版客户端下载成功，开始安装
//                    closeProgressDialog();
//                    PollingUtils.stopPollingService(Config.this,
//                            PollingService.class, PollingService.ACTION);
//                    manager.cancel(NO_3);//设置关闭通知栏
                    Intent updateIntent = new Intent("android.intent.action.VIEW");
                    updateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    updateIntent.setDataAndType(Uri.fromFile(new File(filepath)),
                            "application/vnd.android.package-archive");

                    startActivity(updateIntent);

                    break;
                case MSG_FILE_DOWN_FAIL: // 客户端下载失败
//                    closeProgressDialog();
//                    manager.cancel(NO_3);//设置关闭通知栏
                    ToastUtil.toast(context, "下载失败，稍后再试");

                    break;
                case 10:
                    int length = (int) msg.obj;
//                    initNoti(length);
                    break;
            }
        }
    };

//    private ServiceConnection connection = new ServiceConnection() {
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            myBinder = (ApkDownLoadUtil.MyBinder) service;
//            myBinder.startDownLoad(apkUrl, ApkDownLoadUtil.onFileIsLoaded);
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_center);
        context = this;
        intview();
        setlistener();
        getCacheSize();

    }

    private void setlistener() {
        btn_exit.setOnClickListener(this);
        clear_view.setOnClickListener(this);
        checkVersion.setOnClickListener(this);
    }

    private void intview() {
        manager_username = (TextView) findViewById(R.id.manager_username);
        manager_username.setText(MyApplication.mApplication.getUserName());
        btn_exit = (Button) findViewById(R.id.btn_exit);
        tv_file_size = (TextView) findViewById(R.id.tv_file_size);
        clear_view = (LinearLayout) findViewById(R.id.clear_view);
        checkVersion = (LinearLayout) findViewById(R.id.checkVersion);
//        builder = new NotificationCompat.Builder(this);
//        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        builder.setSmallIcon(R.drawable.head);
//        builder.setContentTitle("下载");
//        builder.setContentText("正在下载");

    }
//
//    private void initNoti(int length){
//        builder.setProgress(100,length,false);
//        manager.notify(NO_3, builder.build());
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                SharePrefrenceUtil.setParam(this, "token", "");
                setResult(1);
                finish();
                break;
            case R.id.clear_view:
                String[] paths = {Environment.getExternalStorageDirectory().getAbsolutePath(),Environment.getDownloadCacheDirectory().getAbsolutePath()};
                DataCleanManager.cleanApplicationData(this,null);
                getCacheSize();
                break;
            case R.id.checkVersion:
                dialog = DialogUtil.createLoadingDialog(context, "正在获取版本信息……");
//                if(ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)!= PackageManager.PERMISSION_GRANTED){
//                    //2、申请权限: 参数二：权限的数组；参数三：请求码
//                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},10);
//                }else{
                    seleceApkVersion();
//                }

                break;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==10&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
//            seleceApkVersion();
//        }
//
//    }

    private void getCacheSize(){
        String pageName = this.getPackageName();
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File cachePath =  this.getExternalCacheDir();
        String size = "0TB";
        try {
            size = DataCleanManager.getFileSize(cachePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_file_size.setText(size);
    }

    /**
     * 检查版本
     */
    private void checkVision() {
       int currVersion = VersionUtil.getVisionCode(context);
        apkUrl = appVersion.getUrl();

        if (currVersion < appVersion.getVersionCode()){
            AlertDialogUtil.showDialog((ManageCenterActivity)context,"",appVersion.getMsg(),new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case AlertDialogUtil.SETOK: // 确定
                            isConnect = true;
//                            apkUrl = "http://192.168.3.165:8089/LandIrrigation2/AAP/abc.apk";
//                            downloadUtils =  new DownLoadUtil(getApplicationContext());
//                            downloadUtils.downloadAPK(apkUrl, "abc.apk");
                            UpdateService.Builder.create(apkUrl).build(context);
                            break;
                        case AlertDialogUtil.SETCANCEL: // 取消
                            break;
                    }
                }
            });
        }else{
            ToastUtil.toast(context, appVersion.getVersionName() + appVersion.getMsg());
        }

    }



    private void seleceApkVersion(){
        SelectApkVersionBiz selectApkVersionBiz = new SelectApkVersionBiz();
        selectApkVersionBiz.getApkVersion(context, new NetCallBack<CommonData<List<AppVersion>>>() {
            @Override
            public void getNetSuccess(int statu, String url, CommonData<List<AppVersion>> listCommonData) {
                if (listCommonData != null){
                    mHandler.obtainMessage(SELECTAPKVER_SUCCESS, listCommonData).sendToTarget();
                }else{
                    mHandler.obtainMessage(SELECTAPKVER_FAIL, "暂时没有版本信息").sendToTarget();
                }

            }

            @Override
            public void getNetFauiled(int statu, String url) {
                mHandler.obtainMessage(SELECTAPKVER_FAIL, "获取版本信息失败").sendToTarget();
            }

            @Override
            public void getNetCanceled(int statu, String url) {

            }

            @Override
            public void getNetError(int statu, String url) {
                mHandler.obtainMessage(GET_NET_ERROR, "连接服务器失败！").sendToTarget();
            }

            @Override
            public void getNetFinished(int statu, String url) {

            }
        });
    }

    @Override
    protected void onDestroy() {
//        if (isConnect){
//            unbindService(connection);
//        }
        super.onDestroy();
    }
}
