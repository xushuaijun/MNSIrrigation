package com.xsyj.irrigation.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.IrrForeViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/5/25.
 */

public class SoilMoistureForeFragment extends Fragment {
    private ViewPager soilMois_viewpager;
    private RadioGroup soilmois_rg;
    private RadioButton btn_soilMois_monitor;
    private RadioButton btn_soilMois_forecast;

    private SoilMoisMonitorFragment soilMoisMonitorFragment;
    private SoilMoisForecastFragment soilMoisForecastFragment;

    private IrrForeViewPagerAdapter vpAdapter;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_soilmoistyrefore, container, false);
        initFragment();
        initView(v);
        initViewPager();
        return v;
    }

    private void initView(View view){
        soilMois_viewpager = (ViewPager) view.findViewById(R.id.soilMois_viewpager);
        soilmois_rg = (RadioGroup) view.findViewById(R.id.soilmois_rg);
        btn_soilMois_monitor = (RadioButton) view.findViewById(R.id.btn_soilMois_monitor);
        btn_soilMois_forecast = (RadioButton) view.findViewById(R.id.btn_soilMois_forecast);
        rgpCheckChange();
    }

    private void initFragment(){
        soilMoisMonitorFragment = new SoilMoisMonitorFragment();
        mFragmentList.add(soilMoisMonitorFragment);
        soilMoisForecastFragment = new SoilMoisForecastFragment();
        mFragmentList.add(soilMoisForecastFragment);
    }

    private void rgpCheckChange(){
        soilmois_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_soilMois_monitor:
                        soilMois_viewpager.setCurrentItem(0);
                        break;
                    case R.id.btn_soilMois_forecast:
                        soilMois_viewpager.setCurrentItem(1);
                        break;
                    default:break;
                }
            }
        });
    }

    private void initViewPager() {
        mFragmentManager = getChildFragmentManager();
        vpAdapter = new IrrForeViewPagerAdapter(mFragmentManager,mFragmentList);
        soilMois_viewpager.addOnPageChangeListener(new ViewPageOnPagerChangeListener());
        soilMois_viewpager.setAdapter(vpAdapter);
        soilMois_viewpager.setCurrentItem(0);
        btn_soilMois_monitor.setChecked(true);
        btn_soilMois_forecast.setChecked(false);
    }

    class ViewPageOnPagerChangeListener implements  ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            soilMois_viewpager.setCurrentItem(position);
            switch (position){
                case 0:
                    btn_soilMois_monitor.setChecked(true);
                    btn_soilMois_forecast.setChecked(false);
                    break;
                case 1:
                    btn_soilMois_monitor.setChecked(false);
                    btn_soilMois_forecast.setChecked(true);
                    break;

                default:break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
