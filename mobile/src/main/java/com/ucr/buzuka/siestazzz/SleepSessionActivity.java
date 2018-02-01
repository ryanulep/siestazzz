package com.ucr.buzuka.siestazzz;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SleepSessionActivity extends AppCompatActivity implements SensorEventListener {

    //sensor manager and accelerometer
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    private static final int M_SENSOR_DELAY = 500;

    //local variable for sensor data
    private long lastUpdate = 0;
    private static final long M_POLL_INTERVAL = 1000;
    private float last_x, last_y, last_z; //last position
    //private Queue<Float> sensorLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_session);


        //create, get, register accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // get an instance of system sensor
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // get accelerometer
        sensorManager.registerListener(this, sensorAccelerometer, M_SENSOR_DELAY);

    }

    //put sensor to sleep when app not in use, will need to comment out in production
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, M_SENSOR_DELAY);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //get current accelerometer data
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            //create a time internal
            long curTime = System.currentTimeMillis();
            long diffTime = curTime - lastUpdate;
            lastUpdate = curTime;

            TextView textView = findViewById(R.id.textView);
            textView.setText("x = " + x + "\n" + "y = " + y + "\n"+ "z = " + z + "\n");
            textView.append("Speed " + Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 1000);

            last_x = x;
            last_y = y;
            last_z = z;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}