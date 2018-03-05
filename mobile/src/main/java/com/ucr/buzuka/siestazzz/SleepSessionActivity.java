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

import com.ucr.buzuka.siestazzz.database.AppDatabase;
import com.ucr.buzuka.siestazzz.model.JournalEntry;
import com.ucr.buzuka.siestazzz.model.SensorReadout;
import com.ucr.buzuka.siestazzz.model.Session;
import com.ucr.buzuka.siestazzz.util.JSONHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SleepSessionActivity extends AppCompatActivity implements SensorEventListener {

    private static final int M_SENSOR_DELAY = 200;      //set the time interval to pull from sensor
    private static int STORAGE_LIMITER = 150;           //set the time interval to store
    private static final String TAG = "SleepSessionActivity";
    //private Queue<Float> sensorLog;
//    public  ArrayList<SensorReadout> sensorReadoutList = new ArrayList<>();
    //sensor manager and accelerometer
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    //local variable for sensor data
    private long lastUpdate = 0;
    private float last_x, last_y, last_z; //last position
    private float SENSOR_THRESHOLD = 0.05f;
    private float MAX_SPEED = Float.NEGATIVE_INFINITY;
    long curTime = 0;
    long diffTime = 0;
    float speed = 0;
    String sessionID;
    private Context mContext; //global context field to threading
    private AppDatabase db;

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
        DateFormat df = new SimpleDateFormat("M_d_h_m");
        fDate = df.format(mDate);

        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMddyyyHHmm");
        Date resultdate = new Date(yourmilliseconds);

        File myDir = new File(getExternalCacheDir().getAbsolutePath(), fDate);
        if(!myDir.exists()){
            myDir.mkdir();
        }
        Log.d("RECORD", myDir.getPath());


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // get an instance of system sensor
        assert sensorManager != null;
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // get accelerometer
        sensorManager.registerListener(this, sensorAccelerometer, M_SENSOR_DELAY);

//        create a new session id
        sessionID = UUID.randomUUID().toString();
        //sensorReadoutList.add(sensorReadout);
        SensorReadout sensorReadout = new SensorReadout(sessionID, System.currentTimeMillis(), 0, 0);
        Session         session     = new Session(sessionID, myDir.getPath());
        Log.d("SESSION", "Session:" + session);
//        get an instance of database
        db = AppDatabase.getInstance(this);
//        drop table
//        db.sensorReadoutDao().clearTable();

//        log session start
//        insert start
        db.sensorReadoutDao().insertAll(sensorReadout);
//        new session
        db.sessionDao().insertAll(session);
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
        startRecording();//create, get, register accelerometer
    }

    protected void onStop(){
        super.onStop();
        stopRecording();
        Log.d("RECORD", "onStop() called");
        sensorManager.unregisterListener(this);

        // TODO Create JournalEntryObject
        JournalEntry journalEntry = new JournalEntry();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;


        /*If sensor is accelerometer
        * and if storage limiter hits zero
        * */

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //get current accelerometer data
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            if (STORAGE_LIMITER == 0) {
                //reset
                STORAGE_LIMITER = 150;

                //create a time internal
                curTime = System.currentTimeMillis();

                diffTime = (curTime - lastUpdate) / 100;
                lastUpdate = curTime;
                // speed = delta V / time
                speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime;

                /*Write to file if speed is greater than threshold* */
                int volume = mRecorder.getMaxAmplitude();

                if (speed > SENSOR_THRESHOLD || volume > 2000) {
                    if (!(speed > SENSOR_THRESHOLD)) {
                        speed = 0;
                    }
                    if (!(volume > 2000)) {
                        volume = 0;
                    }
                    Log.d("RECORD", String.valueOf(volume));
                    SensorReadout sensorReadout = new SensorReadout(sessionID, curTime, speed * 100, volume);
                    db.sensorReadoutDao().insertAll(sensorReadout);
                    //                    sensorReadoutList.add(sensorReadout);
                    Log.d(TAG, "Current read out " + sensorReadout);

                    if (speed != Float.POSITIVE_INFINITY) {
                        MAX_SPEED = speed;
                    }
                }

                TextView textView = findViewById(R.id.textView2);
                textView.setText(String.format("x = %s\ny = %s\nz = %s\n", x, y, z));
                textView.append("Current time " + curTime);
                textView.append("\nSpeed " + speed);
                textView.append("\nMax speed " + MAX_SPEED);

//                Log.i(TAG, "Array: " + sensorReadoutList);

            }
            last_x = x;
            last_y = y;
            last_z = z;
            STORAGE_LIMITER--;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

//  onClick listener for going back to MainActivity to end session
    public void GoHome(View view){

//        log session end
        SensorReadout sensorReadout = new SensorReadout(sessionID, System.currentTimeMillis(), 0,0);
        //sensorReadoutList.add(sensorReadout);
        db.sensorReadoutDao().insertAll(sensorReadout);

        // export to json file
        boolean result = JSONHelper.exportToJSON(this, db.sensorReadoutDao().findById(sessionID));

        if(result){
            Toast.makeText(this, "Data exported", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Export failed", Toast.LENGTH_SHORT).show();
        }

        finish(); //may needed for closing activity
        //intent to go back to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregister sensor
        sensorManager.unregisterListener(this);
//        close app database
        AppDatabase.destroyInstance();
    }
}
