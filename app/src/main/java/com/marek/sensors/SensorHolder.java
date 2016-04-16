package com.marek.sensors;

import android.hardware.Sensor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Marek on 21.03.2016.
 */
public class SensorHolder {
    public Sensor sensor;
    public float[] atributes;

    public SensorHolder(Sensor sensor) {
        this.sensor = sensor;
    }

    public SensorHolder(float[] atributes, Sensor sensor) {
        this.atributes = atributes;
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "sensor=" + sensor.getName() +"\n"+
                "attributes=" + Arrays.toString(atributes);
    }
}
