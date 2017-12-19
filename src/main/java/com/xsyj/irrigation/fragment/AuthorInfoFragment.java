package com.xsyj.irrigation.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AuthorRecyclerAdapter;
import com.xsyj.irrigation.application.MyApplication;
import com.xsyj.irrigation.biz.TurnWatcherBiz;
import com.xsyj.irrigation.entity.AuthorInfo;
import com.xsyj.irrigation.entity.IrrigationTurnInfo;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Clock on 2016/7/26.
 */
public class AuthorInfoFragment extends Fragment {


    private AuthorRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private TurnWatcherBiz turnWatcherBiz;
    private List<IrrigationTurnInfo> turnWatcher_list;
    private int page=1;
    private int page_size=Integer.MAX_VALUE;

    public static Fragment newInstance() {
        AuthorInfoFragment fragment = new AuthorInfoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //需要利用实现了嵌套滚动机制的控件，才能出现AppBarLayout往上推的效果
        View rootView = inflater.inflate(R.layout.fragment_author_info, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

         initdata();
         setTurnWatcher();
        return rootView;
    }

    /**
     * 初始化数据
     */
    private void initdata() {
         if(turnWatcher_list==null){
             turnWatcher_list=new ArrayList<>();
         }

    }


    public void setTurnWatcher() {
        turnWatcherBiz=new TurnWatcherBiz();

        turnWatcherBiz.getinfo(MyApplication.mApplication.getApplicationContext(), 0,String.valueOf(page),String.valueOf(page_size), new NetCallBack<List<IrrigationTurnInfo>>() {
            @Override
            public void getNetSuccess(int statu, String url, List<IrrigationTurnInfo> irrigationTurnInfos) {
                LogUtil.e("轮灌组监控",irrigationTurnInfos.size());
                if(irrigationTurnInfos!=null){
                    turnWatcher_list.clear();
                    turnWatcher_list.addAll(irrigationTurnInfos);
                    adapter = new AuthorRecyclerAdapter(turnWatcher_list);
                    recyclerView.setAdapter(adapter);
                }
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
}
