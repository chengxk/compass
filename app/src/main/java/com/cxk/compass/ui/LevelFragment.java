package com.cxk.compass.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxk.compass.R;
import com.cxk.compass.widget.LevelView;

/**
 * Created by chengxiakuan on 2018/2/2.
 */

public class LevelFragment extends SensorFragment {
    LevelView levelView;
    private TextView vertical_txt;
    private TextView horizontal_txt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        levelView = view.findViewById(R.id.level_view);
        vertical_txt = view.findViewById(R.id.vertical_txt);
        horizontal_txt = view.findViewById(R.id.horizontal_txt);
        return view;
    }

    @Override
    void onSensorChanger(float x, float y, float z) {

        Log.d("aaa" , y+","+z);
        levelView.updateDegree(x, y, z);
        String verStr = String.format(getString(R.string.vertical_description), y);
        String horStr = String.format(getString(R.string.horizontal_description), z);
        vertical_txt.setText(verStr);
        horizontal_txt.setText(horStr);
    }
}
