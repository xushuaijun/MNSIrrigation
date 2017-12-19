package com.xsyj.irrigation.utils;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;


public class ApkDownLoadUtil extends Service {

    private boolean downing; // 是否正在下载
    private Cursor c;
    private String apkUrl;


    public class MyBinder extends Binder{

        private DownloadManager downloadManager;
        private long mTaskId;
        private String downloadPath; //下载到本地路径
        private String mimeType; // 文件类型
        private int isCompleted;
        private String apkUrl; // apk路径

        private BroadcastReceiver receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
                    checkDownloadStatus();// 检查下载状态
                }

            }


        };



        /**
         * 取消下载监听
         */
        public void UNRegisterDownLoadReceiver(){
            if(receiver!=null){
                unregisterReceiver(receiver);
            }

        }

        /**
         * 取消下载任务
         */
        public void removeDownLoad(){
            if(downing){ // 如果正在下载的过程中退出服务了，则暂停下载
                downloadManager.remove(mTaskId);
            }
            UNRegisterDownLoadReceiver();
        }

        /**
         * 开启下载任务
         */
        public  void startDownLoad(String apkUrl,onFileIsLoaded onFileIsLoaded){
           // downloadAPK("http://123.196.114.99:8081/workmanage/APP/swdb.apk", "swdb.apk");
            // 注册广播接收者，监听下载状态
            this.apkUrl=apkUrl;
            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            ApkDownLoadUtil.this.onFileIsLoaded=onFileIsLoaded;

            downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
            // 监听下载
        /*    DownloadObserver mDownloadObserver = new DownloadObserver(null);
            getContentResolver().registerContentObserver(Uri.parse("content://downloads/"),
                    true,
                    mDownloadObserver);
*/

                    goToDownLoad(apkUrl);





            }




        private void goToDownLoad(String fileUrl) {

            if(!TextUtils.isEmpty(fileUrl)){
                String fileName=fileUrl.substring(fileUrl.lastIndexOf("/")+1);
                downloadAPK(fileUrl,fileName);
            }else{
                ToastUtil.toast(getApplicationContext(),"下载路径有误");
            }
        }


        /**
         * 检查下载状态
         */
        private void checkDownloadStatus() {
            DownloadManager.Query query=new DownloadManager.Query();
            query.setFilterById(mTaskId); // 筛选下载任务，传入任务ID，可变参数
             c=downloadManager.query(query);
            if(c.moveToFirst()){
                int status=c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (status){
                    case DownloadManager.STATUS_PAUSED:
                      //  Toast.makeText(getApplicationContext(), "下载暂停", Toast.LENGTH_SHORT).show();
                        break;
                    case DownloadManager.STATUS_PENDING:
                      //  Toast.makeText(getApplicationContext(),"下载延迟",Toast.LENGTH_SHORT).show();
                        break;
                    case DownloadManager.STATUS_RUNNING:
                      //  Toast.makeText(getApplicationContext(),"正在下载",Toast.LENGTH_SHORT).show();
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        String[] command = {"chmod", "777", downloadPath };
                        ProcessBuilder builder = new ProcessBuilder(command);
                        try {
                            builder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                         installAPK(new File(downloadPath));

                        break;
                    case DownloadManager.STATUS_FAILED:
                        ToastUtil.toast(getApplicationContext(),"下载失败");
                        break;
                }
            }

        }



        // 下载本地后，安装apk
        private void installAPK(File file) {
            if(!file.exists()){
                return;
            }
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri=Uri.parse("file:/"+file.toString());
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            // 在服务中开启activity必须设置flag,后面解释
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }



        // 使用系统下载器下载apk
        private void downloadAPK(String versionUrl,String versionName){
          LogUtil.e("名称",versionName);
            // 下载的存储路径
            downloadPath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+ File.separator+"irrigation"+File.separator+versionName;
            LogUtil.e("到哪下载", versionUrl);
           LogUtil.e("下载到", downloadPath);
            File file=new File(downloadPath);
            if (file.exists()) {
                  file.delete();
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(versionUrl));
            //设置在什么网络状态下面能够下载软件
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                    | DownloadManager.Request.NETWORK_MOBILE);

            request.setAllowedOverRoaming(false);// 不允许在漫游的网络下进行下载
            // 设置文件类型，可以在下载结束后自动打开文件
            MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
             mimeType=mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(versionUrl));
            request.setMimeType(mimeType);
//在通知栏中显示，默认就是显示的
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setTitle("新版本Apk");
            request.setDescription("Apk Downloading");
            request.setVisibleInDownloadsUi(true);
//            // 在通知栏中显示，默认就是显示的
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            //request.setDestinationUri(Uri.fromFile(new File(filePath)));//设置文件存放目录，filePath是保存文件的路径
            // request.setDestinationInExternalFilesDir(context,dirType,subPath);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "irrigation".concat(File.separator).concat(versionName));
            // 将下载任务加入下载队列，否则不会进行下载,并且返回任务id
            mTaskId= downloadManager.enqueue(request);
            downing=true;

        }

        class DownloadObserver extends ContentObserver {

            private  DownloadManager mdownLoadManager;

            /**
             * Creates a content observer.
             *
             * @param handler The handler to run {@link #onChange} on, or null if none.
             */
            public DownloadObserver(Handler handler) {
                super(handler);
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);


                    DownloadManager.Query query = new DownloadManager.Query().setFilterById(mTaskId);
                    Cursor cursor = downloadManager.query(query);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    int progress = (int) ((bytes_downloaded * 100) / bytes_total);
                    LogUtil.e("下载进度",progress);
                if( ApkDownLoadUtil.this.onFileIsLoaded!=null){
                    ApkDownLoadUtil.this.onFileIsLoaded.onPartFileIsLoaded(progress,0,null);
                }
                    cursor.close();







            }
        }


    }

    private  onFileIsLoaded onFileIsLoaded;
    public interface onFileIsLoaded{

        /**
         *
         * @param progress     当前进度
         * @param completed    已经完成个数
         * @param o  用户需要的对象（这个根据实际需求）
         */
        void onFileIsLoaded(int progress, int completed, Object o);

        void onPartFileIsLoaded(int progress, int completed, Object o);
    }



    private MyBinder myBinder=new MyBinder();


    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DownLoadService","oncreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("DownLoadService","onStartCommand");
        if(!TextUtils.isEmpty(apkUrl)){
            apkUrl = intent.getStringExtra("apkUrl");
            myBinder.startDownLoad(apkUrl, onFileIsLoaded);
        }

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("DownLoadService","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("DownLoadService", "onDestroy");
        myBinder.removeDownLoad(); // 取消一个准备进行的下载，中止一个正在进行的下载，或者删除一个已经完成的下载。
        if(c!=null){
            c.close();
        }

        super.onDestroy();
    }
}
