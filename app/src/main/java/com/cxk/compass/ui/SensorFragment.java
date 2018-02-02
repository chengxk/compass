package com.cxk.compass.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cxk.compass.R;

/**
 * Created by chengxiakuan on 2018/2/2.
 */

public  abstract class SensorFragment extends BaseFragment {

    protected SensorManager sensorManager;
    protected Sensor defaultSensor;

    protected long lastMarkTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

    abstract void onSensorChanger(float x, float y ,float z);


    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (System.currentTimeMillis() - lastMarkTime < 50) {
                return;
            }
            onSensorChanger(event.values[0], event.values[1], event.values[2]);

            lastMarkTime = System.currentTimeMillis();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
