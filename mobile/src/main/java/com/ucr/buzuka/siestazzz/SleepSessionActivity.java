package com.ucr.buzuka.siestazzz;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ucr.buzuka.siestazzz.model.SensorReadout;

public class SleepSessionActivity extends AppCompatActivity implements SensorEventListener {

    //sensor manager and accelerometer
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    //set the time interval to pull from sensor, current 500 ms
    private static final int M_SENSOR_DELAY = 500;

    //local variable for sensor data
    private long lastUpdate = 0;
    private static final long M_POLL_INTERVAL = 1000; // not used, but it is for displaying sensor data on app
    private float last_x, last_y, last_z; //last position
    //private Queue<Float> sensorLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_session);

        //new sensor read out
        SensorReadout read =
                new SensorReadout(null,
                                "first",
                                System.currentTimeMillis(),
                                System.currentTimeMillis(),
                                0,
                                0,
                                0,
                                0);
        TextView textView = findViewById(R.id.textView);
        textView.setText(read.toString());

        //create, get, register accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // get an instance of system sensor
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // get accelerometer
        sensorManager.registerListener(this, sensorAccelerometer, M_SENSOR_DELAY);

    }

    /** put sensor to sleep when app not in use, will need to comment out in production.
    *   will need to make sensor polling into a service
    */
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    
    /**
    * re-register sensor for app, will not need this in production
    */
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

//            TextView textView = findViewById(R.id.textView);
//            textView.setText("x = " + x + "\n" + "y = " + y + "\n"+ "z = " + z + "\n");
//            textView.append("Speed " + Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 1000);

            last_x = x;
            last_y = y;
            last_z = z;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
