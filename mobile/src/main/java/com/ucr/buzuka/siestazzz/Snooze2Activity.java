package com.ucr.buzuka.siestazzz;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Snooze2Activity extends AppCompatActivity {
    private Button mDismissAlarm;
    private static final String TAG =  "SnoozeActivity";
    float vol = (float) 0.025;
    boolean smartAlarm = true;
    boolean isActive = true;
    int maxVolume = 100;
    public static final String SMART_ALARM = "smart_alarm_toggle";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze2);

        Log.w(TAG, "Before pref " +smartAlarm);
        SharedPreferences prefs = getSharedPreferences(MainActivity.GLOBAL_PREFS, MODE_PRIVATE);
        smartAlarm = Boolean.parseBoolean(prefs.getString(SMART_ALARM, ""));
        Log.w(TAG, "After pref " + smartAlarm);



        final Vibrator vibe = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
        vibe.vibrate(10000);

        Log.w(TAG, "Initializing sounds...");

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sample);
        mediaPlayer.setLooping(true);

        Log.w(TAG, "Playing sound...");

        if(smartAlarm) {
            mediaPlayer.setVolume(vol, vol);
            mediaPlayer.start();
            Handler handler1 = new Handler();
            for (int a = 1; a < 99; a++) {
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vol += 1;
                        Log.w(TAG, "Increasing vol");
                        float log1 = (float) (Math.log(maxVolume - vol) / Math.log(maxVolume));
                        mediaPlayer.setVolume(1 - log1, 1 - log1);
                    }
                }, 1000 * a);
            }

        }
        else{
            mediaPlayer.setVolume(1, 1);
            mediaPlayer.start();
        }

        mDismissAlarm = findViewById(R.id.snooze2_dismiss);
        mDismissAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                vibe.cancel();
                finish();
            }
        });

    }
}
