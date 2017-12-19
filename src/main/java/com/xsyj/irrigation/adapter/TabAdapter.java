package com.xsyj.irrigation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Lenovo on 2016/6/25.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list_fragment=null;                         //fragment列表
    private List<String> list_Title;                              //tab名的列表

    public TabAdapter(FragmentManager fm,List<Fragment> list_fragment,List<String> list_Title) {
        super(fm);

        this.list_fragment=list_fragment;
        this.list_Title = list_Title;
        notifyDataSetChanged();
    }



    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }




    @Override
    public int getCount() {
        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());
    }


    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return TabAdapter.POSITION_NONE;
    }
}
