package com.ucr.buzuka.siestazzz;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.widget.Toast.makeText;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import java.util.UUID;

public class MoteNotify extends BroadcastReceiver {

  private String CHANNEL_ID = UUID.randomUUID().toString();
  private int notificationId = 123;

  @Override
  public void onReceive(Context context, Intent intent) {
    makeText(context, "NOTIFICATION ON", Toast.LENGTH_SHORT).show();
    Log.i("MOTEN", "onReceive: notify received");
    //hard coding titles and content for now
    String notificationTitle = "Time to sleep";
    String notification = "You should start sleeping";
    NotificationManager notificationManager;

    //for android o and up, require to register notification channel
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = "Siestazzz";
      String description = "Siestazzz_notification";
      int importance = NotificationManager.IMPORTANCE_DEFAULT;
      NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
      channel.setDescription(description);
      // Register the channel with the system
      notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
      notificationManager.createNotificationChannel(channel);
    } else {
      // just register notification manager
      notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    //create an intent to got that activity when notification displayed
    Intent intent_notify = new Intent(context, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 12, intent, 0);

    //create notification object,
    //pass in view, title for the notification, and the content to notify
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.icon)
        .setContentTitle(notificationTitle)
        .setContentText(notification)
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    notificationManager.notify(notificationId, mBuilder.build());
  }
}
