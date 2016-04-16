package com.marek.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity  implements SensorEventListener{
    SensorManager smm;
    List<SensorHolder> sensor;
    ListView lv;
    ArrayAdapter<SensorHolder> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lv = (ListView) findViewById(R.id.listView1);
        sensor = new ArrayList<>();
        for (Sensor s : smm.getSensorList(Sensor.TYPE_ALL)) {
            sensor.add(new SensorHolder(s));
        }

        array = new ArrayAdapter<SensorHolder>(this, android.R.layout.simple_list_item_1, sensor);

        lv.setAdapter(array);
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (SensorHolder s : sensor) {
            smm.registerListener(this, s.sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        smm.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor s = event.sensor;
        int i=0;
        for(SensorHolder s1 : sensor){
            if(s1.sensor.getType()==s.getType()){
                array.remove(s1);
                array.insert(new SensorHolder(event.values, s), i);
                array.notifyDataSetChanged();
                break;
            }
            i++;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}