package com.xsyj.irrigation;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xsyj.irrigation.fragment.DecisionFlowFragment;
import com.xsyj.irrigation.fragment.IrrigationDecisionFragment;
import com.xsyj.irrigation.fragment.SoilMoistureForeFragment;
import com.xsyj.irrigation.fragment.WeatherForecastFragment;

/**
 * Created by Lenovo on 2017/5/25.
 */

public class IrrigateForecastActivity extends BaseActivity{
    private TextView titleTxt;
    private FrameLayout irrfore_frame;
    private RadioGroup irrfore_rgp;
    private RadioButton decFlowRBtn;
    private RadioButton weatForeRBtn;
    private RadioButton soilMoisForeRBtn;
    private RadioButton irrDecisionRbtn;

    private DecisionFlowFragment decisionFlowFragment;
    private WeatherForecastFragment weatherForecastFragment;
    private SoilMoistureForeFragment soilMoistureForeFragment;
    private IrrigationDecisionFragment irrigationDecisionFragment;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigate_forecast);
        initView();
    }

    private void initView(){
        titleTxt = (TextView) findViewById(R.id.titleTxt);
        titleTxt.setText("决策流程");
        irrfore_frame = (FrameLayout) findViewById(R.id.irrfore_frame);
        irrfore_rgp = (RadioGroup) findViewById(R.id.irrfore_rgp);
        decFlowRBtn = (RadioButton) findViewById(R.id.rbtn_decision_flow);
        weatForeRBtn = (RadioButton) findViewById(R.id.rbtn_weather_forecast);
        soilMoisForeRBtn = (RadioButton) findViewById(R.id.rbtn_soilmoisture_forecast);
        irrDecisionRbtn = (RadioButton) findViewById(R.id.rbtn_irrigation_decision);
        rgpCheckChange();
        setRGPSelection(0);
    }

    private void rgpCheckChange(){
        irrfore_rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbtn_decision_flow:
                        titleTxt.setText("决策流程");
                        setRGPSelection(0);
                        break;
                    case R.id.rbtn_weather_forecast:
                        titleTxt.setText("天气预报");
                        setRGPSelection(1);
                        break;
                    case R.id.rbtn_soilmoisture_forecast:
                        titleTxt.setText("墒情预报");
                        setRGPSelection(2);
                        break;
                    case R.id.rbtn_irrigation_decision:
                        titleTxt.setText("墒情决策");
                        setRGPSelection(3);
                        break;
                    default:break;
                }
            }
        });
    }

    public void setRGPSelection(int index) {
        mFragmentManager = getSupportFragmentManager();
        // 开启一个Fragment事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (decisionFlowFragment == null) {
                    decisionFlowFragment = new DecisionFlowFragment();
                    transaction.add(R.id.irrfore_frame, decisionFlowFragment);
                } else {
                    transaction.show(decisionFlowFragment);
                }
                break;
            case 1:
                if (weatherForecastFragment == null) {
                    weatherForecastFragment = new WeatherForecastFragment();
                    transaction.add(R.id.irrfore_frame, weatherForecastFragment);
                } else {
                    transaction.show(weatherForecastFragment);
                }
                break;
            case 2:
                if (soilMoistureForeFragment == null) {
                    soilMoistureForeFragment = new SoilMoistureForeFragment();
                    transaction.add(R.id.irrfore_frame, soilMoistureForeFragment);
                } else {
                    transaction.show(soilMoistureForeFragment);
                }
                break;
            case 3:
                if (irrigationDecisionFragment == null) {
                    irrigationDecisionFragment = new IrrigationDecisionFragment();
                    transaction.add(R.id.irrfore_frame, irrigationDecisionFragment);
                } else {
                    transaction.show(irrigationDecisionFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (decisionFlowFragment != null) {
            transaction.hide(decisionFlowFragment);
        }
        if (weatherForecastFragment != null) {
            transaction.hide(weatherForecastFragment);
        }
        if (soilMoistureForeFragment != null) {
            transaction.hide(soilMoistureForeFragment);
        }
        if (irrigationDecisionFragment != null) {
            transaction.hide(irrigationDecisionFragment);
        }
    }
}
