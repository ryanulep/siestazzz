package com.ucr.buzuka.siestazzz;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Queue;

public class SleepSessionActivity extends AppCompatActivity implements SensorEventListener {

    //sensor manager and accelerometer
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    //local variable for sensor data
    private long lastUpdate = 0;
    private float last_x, last_y, last_z; //last position
    //private Queue<Float> sensorLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_session);

        //create, get, register accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // get an instance of system sensor
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // get accelerometer
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //put sensor to sleep when app not in use, will need to comment out in production
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //get current accel data
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            //create a time internal
            long curTime = System.currentTimeMillis();

            if((curTime - lastUpdate) > 100){
                long interval = (curTime - lastUpdate);
                lastUpdate = curTime; // update time

                //get acceleration
                float accel = Math.abs(x - last_x + y - last_y + z - last_z)/(interval * 10000);

                //TODO finish sensor
                if(accel > 0){

                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
