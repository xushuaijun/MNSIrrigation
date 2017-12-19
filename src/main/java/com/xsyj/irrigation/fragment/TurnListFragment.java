package com.xsyj.irrigation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xsyj.irrigation.R;

/**
 * Created by Administrator on 2016/8/31.
 */
public class TurnListFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_turn_list, container, false);
        return v;
    }
}
