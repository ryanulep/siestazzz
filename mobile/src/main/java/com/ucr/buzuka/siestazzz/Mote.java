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

    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent intent_snooze = new Intent(context, Snooze2Activity.class);
//        intent_snooze.setClassName("com.ucr.buzuka.siestazzz", "com.ucr.buzuka.siestazzz.SnoozeActivity");
        intent_snooze.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent_snooze);


    }
}

