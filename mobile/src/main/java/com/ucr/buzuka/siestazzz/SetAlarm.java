package com.ucr.buzuka.siestazzz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class SetAlarm extends AppCompatActivity {
    private int hour;
    private int minute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button setButton = (Button) findViewById(R.id.setAlarm);

        //get toggle preference





       // final TextView alarmDisplay =  findViewById(R.id.textView_AlarmTime);
        final TimePicker timerClock = (TimePicker) findViewById(R.id.timePicker);

        final Calendar cal=Calendar.getInstance();

        Intent intent = new Intent(this, Mote.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1253, intent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast to indicate that the alarm has been set by the user.
                Toast.makeText(getApplicationContext(), "Alarm Set!", Toast.LENGTH_SHORT).show();

                // TODO: Add comments explaining what code is doing here.
                // TODO: Determine why we are not adding the set alarm to BellTower
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = timerClock.getHour();
                    minute = timerClock.getMinute();
                }

                cal.set(Calendar.MONTH,1);
                cal.set(Calendar.YEAR,2018);
                cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.HOUR_OF_DAY,hour);
                cal.set(Calendar.MINUTE,minute);
                cal.set(Calendar.SECOND,0);
                Log.w("myApp", "Alarm");

                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alarmManager.cancel(pendingIntent);
                finish();
            }
        });
    }

}
