package com.xsyj.irrigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.xsyj.irrigation.adapter.MyTreeListViewAdapter;
import com.xsyj.irrigation.adapter.TreeListViewAdapter;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.GetAreasBiz;
import com.xsyj.irrigation.commonentity.CommonData;
import com.xsyj.irrigation.customview.ListViewForScrollView;
import com.xsyj.irrigation.entity.MyNodeBean;
import com.xsyj.irrigation.entity.Node;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.DialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/5/10.
 */

public class ChooseAreaActivity extends Activity implements View.OnClickListener{

    private Context context;
    private MyTreeListViewAdapter<MyNodeBean> adapter;
    private List<MyNodeBean> mDatas;
    private ListViewForScrollView treeLv;
    private Button btn_choose_cancel;
    private Button btn_choose_confirm;
    private boolean isHide = true;
    private String currArea = "";
    private String currToken = "";
    private String currAreaId = "";
    private String currAreaLevel = "";
    private Node currNode;

    private final int GETAREAS_SUCCESS = 0;
    private final int GETAREAS_FAIL = 1;

    private Dialog loadDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosearea);
        context = this;
        initData();
        initView();

//        String id = MyApplication.mApplication.getAreaId();
//        String tocken = MyApplication.mApplication.getToken();
//        String lev = MyApplication.mApplication.getAreaLevel();

    }

    private void initView(){
        btn_choose_cancel = (Button) findViewById(R.id.btn_choose_cancel);
        btn_choose_cancel.setOnClickListener(this);
        btn_choose_confirm = (Button) findViewById(R.id.btn_choose_confirm);
        btn_choose_confirm.setOnClickListener(this);
        treeLv = (ListViewForScrollView) findViewById(R.id.tree_lv);
        try {
            adapter = new MyTreeListViewAdapter<MyNodeBean>(treeLv, this,
                    mDatas, 0, isHide);

            adapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        currArea = node.getAreaName();
                        currAreaId = node.getAreaId();
                        currAreaLevel = node.getAreaLevel();
                        LogUtil.e("currArea",currArea);
                        if (currNode != null){
                            currNode.setIcon(-3);
                            adapter.notifyDataSetChanged();
                        }
                        node.setIcon(-4);
                        currNode = node;
                        adapter.notifyDataSetChanged();
//                        Intent intent = new Intent();
//                        intent.putExtra("currArea",currArea);
//                        setResult(100,intent);
//                        finish();
                    }else{
                        if (node.isExpand()){
                            currArea = node.getAreaName();
                        }else{
                            if (node.isParentExpand()){
                                currArea = node.getParent().getAreaName();
                            }
                        }
                    }

                }

            });
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        treeLv.setAdapter(adapter);
    }

    private void initData(){
          Intent intent = getIntent();
          mDatas = (List<MyNodeBean>) intent.getSerializableExtra("dataList");
//        mDatas.add(new MyNodeBean("bbbb", "aaaa", "河南省"));
//        mDatas.add(new MyNodeBean("cccc", "aaaa", "北京市"));
//        mDatas.add(new MyNodeBean("dddd", "aaaa", "河北省"));
//        mDatas.add(new MyNodeBean("eeee", "bbbb", "濮阳市"));
//        mDatas.add(new MyNodeBean("ffff", "cccc", "丰台区"));
//
//        mDatas.add(new MyNodeBean("eert", "cccc", "海淀区"));
//        mDatas.add(new MyNodeBean("ikki", "cccc", "昌平区"));
//        mDatas.add(new MyNodeBean("aaaa", "0222", "中国"));
//        mDatas.add(new MyNodeBean("werwe", "0222", "美国"));
//        mDatas.add(new MyNodeBean("trer", "werwe", "纽约"));
//
//        mDatas.add(new MyNodeBean("hjhg", "dddd", "邯郸市"));
//        mDatas.add(new MyNodeBean("fdsfg", "dddd", "石家庄"));
//        mDatas.add(new MyNodeBean("ertet", "dddd", "文征明"));
//        mDatas.add(new MyNodeBean("fgdg", "eeee", "濮阳县"));
//        mDatas.add(new MyNodeBean("retw", "fgdg", "庆祖镇"));
//        mDatas.add(new MyNodeBean("rtre", "retw", "西辛庄"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_choose_cancel:
                finish();
                break;
            case R.id.btn_choose_confirm:
                Intent intent = new Intent();
                intent.putExtra("currArea",currArea);
                intent.putExtra("areaId", currAreaId);
                intent.putExtra("areaLevel", currAreaLevel);
                setResult(100,intent);
                finish();
                break;
            default:break;
        }
    }


}
