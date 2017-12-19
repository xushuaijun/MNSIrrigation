package com.xsyj.irrigation;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;


import com.github.glomadrian.loadingballs.BallView;
import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.adapter.MetoAdapter;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.IrrigationCountBiz;
import com.xsyj.irrigation.customview.AdvancedGridView;
import com.xsyj.irrigation.customview.MyListView;
import com.xsyj.irrigation.customview.SlideShowView;
import com.xsyj.irrigation.entity.IrrigationCount;
import com.xsyj.irrigation.entity.MetoMenuEntry;
import com.xsyj.irrigation.fragment.AuthorInfoFragment;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.LogUtil;

import org.xutils.http.RequestParams;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



/***
 *
 * @author ymw
 */
public class MainActivity extends BaseActivity implements ViewTreeObserver.OnGlobalLayoutListener {


    private Toolbar mToolBar;
    private MyListView lv_main_lgz;
    private SlideShowView main_slideshowView;
    // listview 是否可以滑动
    private boolean canScroll;
    private BallView ball_main;
    private MyHandler mHandler;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setHandler(); // 初始化handler
        //setLayoutLisitener();
         setBallProgress(); //  设置小球等待
         setImages(); // 返回首页展示图
         setTollBar(); // 设置工具栏
         setIrrigationCount(); // 设置灌溉统计信息
         setMainMenu(); // 设置菜单
         setListView(); // 设置轮灌组信息列表




    }

    // 初始化Handler
    private void setHandler() {
        mHandler=new MyHandler(this);
    }


    static class MyHandler extends Handler {
        WeakReference<MainActivity> mActiReference;
        MyHandler(MainActivity mActivity) {
            this.mActiReference = new WeakReference<MainActivity>(
                    mActivity);
        }
        @Override
        public void handleMessage(Message msg) {

            MainActivity mactivity = mActiReference.get();
            if (mactivity != null) {
                switch (msg.what) {
                    case 0:  // 刷新界面


                        break;

                }
            }
        }

    }

    /**
     * 定时获取信息
     */
    private void setIntervalInfo() {

// 每隔30秒执行一次
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new freshContentTask(), 1, 30, TimeUnit.SECONDS);


    }

    private void setBallProgress() {
        ball_main=(BallView)findViewById(R.id.ball_main);
    }

    /**
     * 设置菜单
     */
    private void setMainMenu() {
        AdvancedGridView gridview_menu = (AdvancedGridView) findViewById(R.id.gridview_menu);
        MetoMenuEntry[] marr=new MetoMenuEntry[9];
        MetoAdapter adapter=new MetoAdapter(this, marr);
        gridview_menu.setAdapter(adapter);
    }

    /**
     * 设置灌溉统计信息
     */
    private void setIrrigationCount() {
        final TextView tv_main_total_water = (TextView) findViewById(R.id.tv_main_total_water);
        final TextView tv_main_total_time = (TextView) findViewById(R.id.tv_main_total_time);
        final TextView tv_main_total_times = (TextView) findViewById(R.id.tv_main_total_times);



        IrrigationCountBiz irrigationCountBiz=new IrrigationCountBiz();
        irrigationCountBiz.getinfo(getApplicationContext(), new NetCallBack<IrrigationCount>() {
            @Override
            public void getNetSuccess(int statu, String url, IrrigationCount irrigationCount) {
                tv_main_total_water.setText(irrigationCount.getTotalWater() + "m³");

                tv_main_total_time.setText(new BigDecimal(irrigationCount.getTotalTime()).divide(new BigDecimal(1440), 2, BigDecimal.ROUND_HALF_EVEN).doubleValue()+"天");
                tv_main_total_times.setText(irrigationCount.getTotalTimes()+"次");
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

    /**
     *  获取首页展示图
     */
    private void setImages() {
        main_slideshowView=(SlideShowView)findViewById(R.id.main_slideshowView);
        main_slideshowView.setProgressBar(ball_main); // 设置进度动画view
        RequestParams params=new RequestParams(Const.main_show);
        params.addBodyParameter("token", MyApplication.mApplication.getToken());
        main_slideshowView.initData(params);
    }

    private void setLayoutLisitener() {

        CoordinatorLayout rl_main = (CoordinatorLayout) findViewById(R.id.rl_main);
      /*  rl_main.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                ToastUtil.toast(getApplicationContext(),scrollY+"");
            }
        });*/
        ViewTreeObserver vto = (ViewTreeObserver) rl_main.getViewTreeObserver();         // 通过 getViewTreeObserver 获得 ViewTreeObserver 对象
        vto .addOnGlobalLayoutListener(this);
    }

    private void setListView() {
        mFragmentArrays[0] = AuthorInfoFragment.newInstance();
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new AuthorPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);

     /*   RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AuthorRecyclerAdapter(AuthorInfo.createTestData()));
*/


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lv_main_lgz.setNestedScrollingEnabled(true);
            main_slideshowView.setNestedScrollingEnabled(true);
        }
*/
     /*
          监听 listview 是否 滑动到底端或者是 顶端
      ListViewUtil listViewUtil=new ListViewUtil();
        listViewUtil.isTopOrBottom(lv_main_lgz, new ListViewUtil.TopOrBottom() {
            @Override
            public void onTop(boolean isTop) {
                ToastUtil.toast(getApplicationContext(),isTop+"");
            }

            @Override
            public void onBottom(boolean isBottom) {
                ToastUtil.toast(getApplicationContext(),isBottom+"");
            }
        });*/
    }

    // 设置工具栏
    private void setTollBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        // 设置是toolbar否有返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出framgnt
                onBackPressed();
            }
        });
    }


    // 用于监听布局之类的变化，比如某个空间消失了
    @Override
    public void onGlobalLayout() {


    }

    private Fragment[] mFragmentArrays = new Fragment[1];
    private class AuthorPagerAdapter extends FragmentPagerAdapter {

        public AuthorPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }

        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }


    }

    private class freshContentTask implements Runnable {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 刷新首页轮灌组列表
                            ((AuthorInfoFragment) mFragmentArrays[0]).setTurnWatcher();
                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setIntervalInfo(); //  定时获取信息
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(scheduledExecutorService!=null){
            scheduledExecutorService.shutdownNow();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.e("关了吗", "关了吗");
        if(resultCode==1) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
           this.finish();

        }
    }
}