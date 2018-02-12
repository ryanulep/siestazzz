package com.ucr.buzuka.siestazzz;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ucr.buzuka.siestazzz.model.SensorReadout;
import com.ucr.buzuka.siestazzz.util.JSONHelper;

import java.util.ArrayList;

public class SleepSessionActivity extends AppCompatActivity implements SensorEventListener {

    //set the time interval to pull from sensor, current 1000 ms
    private static final int M_SENSOR_DELAY = 300;
    private static final long M_POLL_INTERVAL = 1000; // not used, but it is for displaying sensor data on app
    private static final String TAG = "SleepSessionActivity";
    //private Queue<Float> sensorLog;
    public  ArrayList<SensorReadout> sensorReadoutList = new ArrayList<SensorReadout>();
    //sensor manager and accelerometer
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    //local variable for sensor data
    private long lastUpdate = 0;
    private float last_x, last_y, last_z; //last position
    private float SENSOR_THRESHOLD = 0.00005f;
    private float MAX_SPEED = Float.NEGATIVE_INFINITY;

//    public static final String FILE_NAME = "readout.txt";
//    private static FileOutputStream fileOutputStream = null;
//    private static File file = new File(FILE_NAME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_session);

        //new sensor read out
//        SensorReadout read =
//                new SensorReadout(null,
//                                "first",
//                                System.currentTimeMillis(),
//                                System.currentTimeMillis(),
//                                0,
//                                0,
//                                0,
//                                0);
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(read.toString());

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

    protected void onStop(){
        super.onStop();
        sensorManager.unregisterListener(this);
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
            long diffTime = (curTime - lastUpdate)*1000;
            lastUpdate = curTime;
            float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime;

            /*Write to file if speed is greater than threshold
            * */
            if (speed > SENSOR_THRESHOLD ) {

                SensorReadout sensorReadout = new SensorReadout(curTime, speed);
                sensorReadoutList.add(sensorReadout);
                //Log.i(TAG, "Current read out " + sensorReadoutList);

                if (speed != Float.POSITIVE_INFINITY){
                    MAX_SPEED = speed;
                }


            }

            TextView textView = findViewById(R.id.textView2);
            textView.setText("x = " + x + "\n" + "y = " + y + "\n"+ "z = " + z + "\n");
            textView.append("Current time " + curTime);
            textView.append("\nSpeed " + speed);
            textView.append("\nMax speed " + MAX_SPEED);

            Log.i(TAG, "Array: " + sensorReadoutList);

            last_x = x;
            last_y = y;
            last_z = z;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void GoHome(View view){

        boolean result = JSONHelper.exportToJSON(this, sensorReadoutList);
        if(result){
            Toast.makeText(this, "Data exported", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Export failed", Toast.LENGTH_SHORT).show();
        }

        //finish();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
