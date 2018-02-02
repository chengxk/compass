package com.cxk.compass.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxk.compass.R;
import com.cxk.compass.widget.CompassView;

/**
 * Created by chengxiakuan on 2018/2/1.
 */

public class CompassFragment extends SensorFragment {
    private String stringArray[];
    private TextView text;
    private CompassView compassView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stringArray = getResources().getStringArray(R.array.direction);
    }

    @Override
    void onSensorChanger(float x, float y, float z) {
        int index = (int) ((x + 22.5) % 360 / 45);

        String description = String.format(getString(R.string.description), stringArray[index], (int) (x % 360));
        text.setText(description);
        compassView.updateDegree(x, y, z);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compass, container, false);
        text = view.findViewById(R.id.text);
        compassView = view.findViewById(R.id.compass_view);
        return view;
    }


}
