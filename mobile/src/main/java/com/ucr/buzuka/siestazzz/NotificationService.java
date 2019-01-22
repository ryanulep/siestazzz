package com.ucr.buzuka.siestazzz;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rick Boshae and Xiao Zhou on 3/7/18.
 */

public class NotificationService extends IntentService {

  private static final String TAG = "NotificationService";


  public static Intent newIntent(Context context) {
    return new Intent(context, MoteNotify.class);
  }

  public NotificationService() {
    super(TAG);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Log.i(TAG, "Recieved Intent:" + intent);
  }

  public static void setNotifyService(Context context, Date date) {
    Log.d("myApp", "In setAlarmService function");
    Intent i = NotificationService.newIntent(context);

    // TODO Give unique ID at place of 0
    PendingIntent pi = PendingIntent
        .getBroadcast(context, 11, i, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA);
    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    Log.i("myApp", "AlarmManager Set for notification " + cal.getTime());
  }
}
