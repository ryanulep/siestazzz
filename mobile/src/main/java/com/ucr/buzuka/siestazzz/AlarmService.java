package com.ucr.buzuka.siestazzz;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ucr.buzuka.siestazzz.model.Alarm;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Ammar on 3/6/18.
 */

public class AlarmService extends IntentService {
    private static final String TAG = "AlarmService";
    //notification channel id
    private static String CHANNEL_ID = UUID.randomUUID().toString();
    private static int notificationId = 123;


    public static  Intent newIntent(Context context){
        return new Intent(context, Mote.class);
    }
    public static Intent newNotification(Context context){
        return new Intent(context, NotificationService.class);
    }

    public AlarmService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        Log.i(TAG, "Recieved Intent:" + intent);
    }

    public static void setAlarmService(Context context, boolean mActive, Date date, boolean mSmart, String mAlarmTitle, UUID mId){
        Log.w("myApp", "In setAlarmService function");
        Intent i = AlarmService.newIntent(context);


        // TODO Give unique ID at place of 0
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
        AlarmManager alarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);


        if(mActive){
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
            Log.w("myApp", "AlarmManager Se t" + cal);
        }
        else{
            alarmManager.cancel(pi);
            pi.cancel();
        }


    }


    public static void setNotificationService(Context context, Date date) {
        Intent i = AlarmService.newNotification(context);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
        Calendar set_time = Calendar.getInstance();
        set_time.setTime(date);
        //set_time.add(Calendar.SECOND, 15);
        AlarmManager alarmManager  = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, set_time.getTimeInMillis(), pendingIntent);

//        //hard coding titles and content for now
//        String notificationTitle = "Time to sleep";
//        String notification = "You should start sleeping";
//        NotificationManager notificationManager;
//
//        Calendar right_now = Calendar.getInstance();
//        right_now.setTime(date);
//        Calendar set_time = Calendar.getInstance();
//        set_time.setTime(date);
//        set_time.add(Calendar.SECOND, -10);
//
//        Log.i("notification", "hour_of_day: " + set_time.getTime());
//        Log.i("notification", "right_now: " + right_now.getTime());
//        //Log.i("myApp", "hour: " + right_now - set_time.getTime());
//
//
//
//        //for android o and up, require to register notification channel
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = context.getString(R.string.channel_name);
//            String description = context.getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system
//            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(channel);
//        }else{
//            // just register notification manager
//            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        }
//
//        //create an intent to got that activity when notification displayed
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//        //create notification object,
//        //pass in view, title for the notification, and the content to notify
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID )
//                .setSmallIcon(R.drawable.icon)
//                .setContentTitle(notificationTitle)
//                .setContentText(notification)
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationManager.IMPORTANCE_HIGH)
//                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
//
//        if (right_now == set_time){
////        if (true){
//            notificationManager.notify(notificationId, mBuilder.build());
//            Log.w("notification", "show notification");
//        }
    }
}
