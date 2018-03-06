package com.ucr.buzuka.siestazzz;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Ammar on 2/7/18.
 */




public class Mote extends BroadcastReceiver {
    NotificationCompat.Builder notification;
    private static final String TAG =  "Mote.java";
    float vol = (float) 0.025;
    boolean smartAlarm = true;
    int maxVolume = 100;

    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        Vibrator vibe = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        vibe.vibrate(10000);

        Log.v(TAG, "Initializing sounds...");
        final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.sample);
        mediaPlayer.setLooping(true);

        Log.v(TAG, "Playing sound...");

        if(smartAlarm) {
            mediaPlayer.start();
            mediaPlayer.setVolume(vol, vol);
            Handler handler1 = new Handler();
            for (int a = 1; a < 99; a++) {
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vol += 1;
                        Log.v(TAG, "Increasing vol");
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
    }
}

