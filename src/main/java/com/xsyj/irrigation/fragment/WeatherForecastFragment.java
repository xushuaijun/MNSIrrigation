package com.xsyj.irrigation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xsyj.irrigation.R;

/**
 * Created by Lenovo on 2017/5/25.
 */

public class WeatherForecastFragment extends Fragment {
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_decflow, container, false);
        textView = (TextView) v.findViewById(R.id.f_textView);
        textView.setText("天气预报");
        return v;
    }
}
