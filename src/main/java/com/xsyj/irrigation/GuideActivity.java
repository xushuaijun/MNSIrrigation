package com.xsyj.irrigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xsyj.irrigation.Cons.Const;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.customview.SlideShowView;
import com.xsyj.irrigation.utils.SharePrefrenceUtil;

import org.xutils.http.RequestParams;

public class GuideActivity extends BaseActivity implements View.OnClickListener {


    private SlideShowView lg_ssv;
    private Button btn_register;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        if(!"".equals(SharePrefrenceUtil.getParam(getApplicationContext(), LoginActivity.TOKEN_STR, ""))){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
            return;
        }
        initview();
        setlisteber();
    }

    private void setlisteber() {
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void initview() {
        SlideShowView lg_ssv = (SlideShowView) findViewById(R.id.login_slideshowView);
        lg_ssv.stopPlay();
        RequestParams params=new RequestParams(Const.login_show);
        lg_ssv.initData(params);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                startActivityForResult(new Intent(this,LoginActivity.class),100);
                break;
            case R.id.btn_register:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100){
            finish();
        }
    }
}
