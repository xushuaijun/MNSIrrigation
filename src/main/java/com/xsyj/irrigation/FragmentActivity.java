package com.xsyj.irrigation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.xsyj.irrigation.fragment.HomePageFragment;
import com.xsyj.irrigation.fragment.Page1Fragment;
import com.xsyj.irrigation.utils.LogUtil;

import java.lang.ref.WeakReference;
import java.util.List;

public class FragmentActivity extends BaseActivity {

//    private MyHandler mhandler;

    private List<Fragment> pagefragment;
    private boolean isShow; // 是否显示page1的列表的勾选按钮

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initview();
    }

    private void initview() {
//        mhandler=new MyHandler(this);
        initDefaultFragment();
    }


    /**
     * 初始化fragment
     */
    private void initDefaultFragment() {
        HomePageFragment homefragment=(HomePageFragment)getSupportFragmentManager().findFragmentByTag("homepagefragment");
        if(homefragment==null){
            homefragment=new HomePageFragment();

            FragmentTransaction t = this
                    .getSupportFragmentManager().beginTransaction();//

            t.add(R.id.rl_main_content, homefragment,"homepagefragment");
            //t.addToBackStack("homepagefragment");
            t.commit();
        }else{

            homefragment.initFragement();

        }
    }


//    static class MyHandler extends Handler {
//        WeakReference<FragmentActivity> mActiReference;
//
//        MyHandler(FragmentActivity mActivity) {
//            this.mActiReference = new WeakReference<>(
//                    mActivity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//
//            FragmentActivity mactivity = mActiReference.get();
//            if (mactivity != null) {
//                mactivity.initDefaultFragment();
//            }
//        }
//    }


    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("FragmentActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("FragmentActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("FragmentActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("FragmentActivity", "onStop");
    }

    public void refreshPage1(){
        ((Page1Fragment)getSupportFragmentManager().findFragmentByTag("page1")).getTurnList(); // 在page2里删除轮灌组，重刷page1页的轮灌组筛选条件
    }
}
