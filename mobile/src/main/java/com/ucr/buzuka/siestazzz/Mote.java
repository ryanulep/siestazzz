package com.ucr.buzuka.siestazzz;


import android.app.AlertDialog;
import android.app.NotificationManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import android.widget.RemoteViews;
import android.widget.Toast;

import android.support.v4.app.NotificationCompat;
import android.os.Vibrator;



/**
 * Created by Ammar on 2/7/18.
 */



public class Mote extends BroadcastReceiver {
    NotificationCompat.Builder notification;
    private static final String TAG =  "Mote.java";



    public void onReceive(Context context, Intent intent) {




        //        To Launch snooze activity, Change to dialog
//        Intent i = new Intent();
//        i.setClassName("com.ucr.buzuka.siestazzz", "com.ucr.buzuka.siestazzz.Snooze");
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
//        AlertDialog.Builder builder;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
//        } else {
//            builder = new AlertDialog.Builder(this);
//        }
//        builder.setTitle("Alarm")
//
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // continue with delete
//
//                    }
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();




        // TODO Auto-generated method stub



        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();

        Vibrator vibe = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        //Snooze(view);
        vibe.vibrate(10000);


        Log.v(TAG, "Initializing sounds...");

        //MediaPlayer mp = MediaPlayer.create(context., R.raw.Alien_AlarmDrum_KevanGC_893953959);

        Log.v(TAG, "Playing sound...");
        //mp.start();


        Log.w("myApp", "Alarm at");



    }

//    public void Snooze(View view) {
//        Intent intent = new Intent(Mote.this, Snooze.class);
//    }





}

