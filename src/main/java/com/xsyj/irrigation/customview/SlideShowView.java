package com.xsyj.irrigation.customview;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz_abstract.HttpBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.entity.User;
import com.xsyj.irrigation.utils.JsonParserUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;


/**
 * ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果；
 * 既支持自动轮播页面也支持手势滑动切换页面
 *
 *
 */

public class SlideShowView extends FrameLayout implements HttpBiz.NetConnect {



    //轮播图图片数量
    private final static int IMAGE_COUNT = 5;
    //自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true;

    //自定义轮播图的资源
    private String[] imageUrls;
    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList=new ArrayList<>();
    //放圆点的View的list
    private List<View> dotViewsList=new ArrayList<>();

    private ViewPager viewPager;
    //当前轮播页
    private int currentItem  = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;

    private Context context;


    private View progressbar; //外部传进来的等待显示view

    //Handler
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }

    };


    public SlideShowView(Context context) {
        this(context,null);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;



        if(isAutoPlay){
            startPlay();
        }

    }
    /**
     * 开始轮播图切换
     */
    public void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);

    }
    /**
     * 停止轮播图切换
     */
    public void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    /**
     * 初始化相关Data
     */
    public void initData(RequestParams params){
        imageViewsList.clear();
        dotViewsList.clear();

        // 一步任务获取图片
        getImageUrls(params);
    }

    // 获取图片地址
    private void getImageUrls(RequestParams params) {
        HttpBiz httpBiz=new HttpBiz(getContext());
        httpBiz.connectNet(HttpBiz.POST, params);
        httpBiz.setNetConnect(this);
    }

    public void setProgressBar(View v){
       progressbar=v;
    }

    /**
     * 初始化Views等UI
     */
    private void initUI(Context context){
        if(imageUrls == null || imageUrls.length == 0)
            return;

        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);

        LinearLayout dotLayout = (LinearLayout)findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();
        dotViewsList.clear();

        // 热点个数与图片特殊相等
        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view =  new ImageView(context);
            view.setTag(imageUrls[i]);

            view.setScaleType(ScaleType.CENTER_CROP);
            imageViewsList.add(view);

            ImageView dotView =  new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.leftMargin = 15;
            params.rightMargin = 15;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        ShowDot(0);
    }

    @Override
    public void getSuccess(String url, String result) {
           if(!TextUtils.isEmpty(result)){

               Type type= new TypeToken<CommonData<List<String>>>(){}.getType();
               // 根据类型返回实体类
               CommonData<List<String>>  datamessage= JsonParserUtil.getObject(result, type);
               LogUtil.e("图片展示",datamessage.getData().toString()+".....");
               if(datamessage!=null && datamessage.getS()==200){
                   List<String> imgs=datamessage.getData();
                   imageUrls=new String[imgs.size()];
                   datamessage.getData().toArray(imageUrls);
                   if(progressbar!=null){
                       progressbar.setVisibility(View.GONE);
                   }
               }


               initUI(context);
           }
    }

    @Override
    public void getCanelled(String url, Callback.CancelledException e) {
        initUI(context);
    }

    @Override
    public void getError(String url, Throwable ex, boolean b) {
        initUI(context);
    }

    @Override
    public void getFinished(String url) {

    }

    /**
     * 填充ViewPager的页面适配器
     *
     */
    private class MyPagerAdapter  extends PagerAdapter{

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager)container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ImageView imageView = imageViewsList.get(position);

            MyApplication.imageLoader.displayImage(imageView.getTag() + "", imageView);

            ((ViewPager)container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

    }
    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     *
     */
    private class MyPageChangeListener implements OnPageChangeListener{

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
            ShowDot(pos);
        }

    }


    private void ShowDot(int pos){
        currentItem = pos;
        for(int i=0;i < dotViewsList.size();i++){
            if(i == pos){
                ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.dot_focus);
            }else {
                ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_blur);
            }
        }
    }


    /**
     *执行轮播图切换任务
     *
     */
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }

    /**
     * 销毁ImageView资源，回收内存
     *
     */
    private void destoryBitmaps() {

        for (int i = 0; i < IMAGE_COUNT; i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                //解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }





}