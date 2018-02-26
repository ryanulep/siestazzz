package com.ucr.buzuka.siestazzz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ucr.buzuka.siestazzz.model.SensorReadout;
import com.ucr.buzuka.siestazzz.util.JSONHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SleepSessionActivity extends AppCompatActivity implements SensorEventListener {

    //set the time interval to pull from sensor, current 300 ms
    private static final int M_SENSOR_DELAY = 300;
    private static int STORAGE_LIMITER = 100;
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

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private Date mDate=new Date();
    private String fDate;


    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }



    private void startRecording() {
        mFileName = getExternalCacheDir().getAbsolutePath();
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMddyyyHHmm");
        Date resultdate = new Date(yourmilliseconds);
        mFileName +="/";
        mFileName +=fDate;
        mFileName +="/";
        mFileName += "1.3gp";
        Log.d("AUDIO", mFileName);
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        Log.d("RECORD", "Start Recording is being executed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_session);
        DateFormat df = new SimpleDateFormat("M_d");
        fDate = df.format(mDate);

        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMddyyyHHmm");
        Date resultdate = new Date(yourmilliseconds);

        File myDir = new File(getExternalCacheDir().getAbsolutePath(), fDate);
        if(!myDir.exists()){
            myDir.mkdir();
        }
        Log.d("RECORD", myDir.getPath());

        startRecording();
        //create, get, register accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // get an instance of system sensor
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // get accelerometer
        sensorManager.registerListener(this, sensorAccelerometer, M_SENSOR_DELAY);
        Log.d("RECORD", "OnCreate Completed");
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
        stopRecording();
        Log.d("RECORD", "onStop() called");
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        int volume=mRecorder.getMaxAmplitude();
        if (volume>2000)
        {

            Log.d("RECORD", String.valueOf(volume));
        }

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //get current accelerometer data
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            //create a time internal
            long curTime = System.currentTimeMillis();
            long diffTime = (curTime - lastUpdate)*1000;
            lastUpdate = curTime;
            // speed = delta V / time
            float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime;

            /*Write to file if speed is greater than threshold
            * */
            if (STORAGE_LIMITER == 0) {
                if (speed > SENSOR_THRESHOLD) {

                    SensorReadout sensorReadout = new SensorReadout(curTime, speed);
                    sensorReadoutList.add(sensorReadout);
                    //Log.i(TAG, "Current read out " + sensorReadoutList);

                    if (speed != Float.POSITIVE_INFINITY) {
                        MAX_SPEED = speed;
                    }
                }
                STORAGE_LIMITER = 100;
            }
            STORAGE_LIMITER--;

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

//  onClick listener for going back to MainActivity to end session
    public void GoHome(View view){

        // export to json file
        boolean result = JSONHelper.exportToJSON(this, sensorReadoutList);
        if (result) {
            Toast.makeText(this, "Data exported", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Export failed", Toast.LENGTH_SHORT).show();
        }
        //finish(); //may needed for closing activity

        //intent to go back to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
