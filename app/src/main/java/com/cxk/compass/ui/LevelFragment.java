package com.cxk.compass.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxk.compass.R;
import com.cxk.compass.widget.LevelView;

/**
 * Created by chengxiakuan on 2018/2/2.
 */

public class LevelFragment extends SensorFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        LevelView levelView = view.findViewById(R.id.level_view);
        return view;
    }

    @Override
    void onSensorChanger(float x, float y, float z) {

    }
}
