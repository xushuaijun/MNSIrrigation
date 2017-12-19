package com.xsyj.irrigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    /**
     * 结束当前页
     * @param v
     */
    public void back(View v){
        finish();
    }



}
