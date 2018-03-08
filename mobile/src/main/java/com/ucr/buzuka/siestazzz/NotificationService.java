package com.ucr.buzuka.siestazzz;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.UUID;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by jakex on 3/7/2018.
 */

public class NotificationService extends BroadcastReceiver {
    //notification channel id
    private String CHANNEL_ID = UUID.randomUUID().toString();
    private int notificationId = 123;
    @Override
    public void onReceive(Context context, Intent intent) {
        //hard coding titles and content for now
        String notificationTitle = "Time to sleep";
        String notification = "You should start sleeping";
        NotificationManager notificationManager;

        //for android o and up, require to register notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }else{
            // just register notification manager
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }


        //create an intent to got that activity when notification displayed
        intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //create notification object,
        //pass in view, title for the notification, and the content to notify
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID )
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(notificationTitle)
                .setContentText(notification)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(notificationId, mBuilder.build());
        Log.v("notification", "Display notification");

    }
}