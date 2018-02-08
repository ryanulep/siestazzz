package com.ucr.buzuka.siestazzz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.net.Uri;
import android.os.Vibrator;
import android.media.RingtoneManager;
import android.media.Ringtone;


/**
 * Created by Ammar on 2/7/18.
 */


public class Mote extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub




        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        //Vibrator vibe = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        //vibe.vibrate(10000);
        Log.w("myApp", "Alarm at");
    }



}