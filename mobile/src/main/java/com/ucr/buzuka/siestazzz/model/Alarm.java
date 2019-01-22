package com.ucr.buzuka.siestazzz.model;

import android.content.Context;
import android.util.Log;
import com.ucr.buzuka.siestazzz.AlarmService;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/15/2018.
 */

public class Alarm {

  private UUID mId;
  private String mAlarmTitle;
  private Date mAlarmTime;
  private boolean mActive;
  private boolean mSmart;

  public Alarm() {
    this(UUID.randomUUID());
  }

  public Alarm(UUID uuid) {
    mId = uuid;
    mAlarmTime = new Date();
    mAlarmTitle = "Alarm";
    mActive = true;
    mSmart = false;
  }

  public UUID getId() {
    return mId;
  }

  public String getAlarmTitle() {
    return mAlarmTitle;
  }

  public void setAlarmTitle(String alarmTitle) {
    mAlarmTitle = alarmTitle;
  }

  public Date getAlarmTime() {
    return mAlarmTime;
  }

  public void setAlarmTime(Date alarmTime) {
    mAlarmTime = alarmTime;
//        AlarmService.setAlarmService(context, mActive, mAlarmTime, mSmart, mAlarmTitle, mId);
  }

  public void setAlarmTime(Context context, Date alarmTime) {
    Log.w("myApp", "SetAlarmTime");
    Calendar newDate = Calendar.getInstance();
    newDate.setTime(alarmTime);

    Calendar mTempAlarmTime = Calendar.getInstance();

    mTempAlarmTime.setTime(mAlarmTime);             // Set to current SleepTimeAndDate
    mTempAlarmTime.set(Calendar.HOUR_OF_DAY, newDate.get(Calendar.HOUR_OF_DAY));
    mTempAlarmTime.set(Calendar.MINUTE, newDate.get(Calendar.MINUTE));

    mAlarmTime = mTempAlarmTime.getTime();

    AlarmService.setAlarmService(context, mActive, mAlarmTime, mSmart, mAlarmTitle, mId);
  }

  public boolean isActive() {
    return mActive;
  }

  public void setActive(boolean active) {
    mActive = active;
  }

  public boolean isSmart() {
    return mSmart;
  }

  public void setSmart(boolean smart) {
    mSmart = smart;
  }
}
