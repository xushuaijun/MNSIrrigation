package com.xsyj.irrigation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Lenovo on 2017/5/19.
 */

public class AddPileActivity extends BaseActivity implements View.OnClickListener{
    private Context context;

    private EditText et_pile_num;
    private EditText et_pile_name;
    private TextView tv_pland_name;
    private Button btn_pile_submit;

    private int type; //0 添加 1修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pile);
        context = this;
        initView();
        initData();
    }
    private void initView(){
        et_pile_num = (EditText) findViewById(R.id.et_pile_num);
        et_pile_name = (EditText) findViewById(R.id.et_pile_name);
        tv_pland_name = (TextView) findViewById(R.id.tv_pland_name);
        btn_pile_submit = (Button) findViewById(R.id.btn_pile_submit);

        btn_pile_submit.setOnClickListener(this);
    }

    private void initData(){
        type = getIntent().getIntExtra("type",-1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pile_submit:
                break;
            default:break;
        }
    }
}
