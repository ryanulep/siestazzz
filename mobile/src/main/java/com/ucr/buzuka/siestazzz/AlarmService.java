package com.ucr.buzuka.siestazzz;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
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


    public static  Intent newIntent(Context context){
        return new Intent(context, Mote.class);
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
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
        AlarmManager alarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if(mActive){
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
            Log.w("myApp", "AlarmManager Set");

        }
        else{
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

}
