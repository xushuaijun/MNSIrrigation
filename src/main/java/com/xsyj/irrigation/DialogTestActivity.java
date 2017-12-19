package com.xsyj.irrigation;

import android.os.Bundle;

import com.xsyj.irrigation.utils.DialogUtil;

/***
 *
 * @author ymw
 */
public class DialogTestActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DialogUtil.createLoadingDialog(this,"Are you ready!").show();

    }


}