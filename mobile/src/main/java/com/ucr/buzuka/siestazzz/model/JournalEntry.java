package com.ucr.buzuka.siestazzz.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/18/18.
 */


public class JournalEntry {

  private UUID mId;
  private String mTitle;
  private Date mSleepDateAndTime; // keeps track of sleep date and time
  private Date mWakeDateAndTime;  // keeps track of time and date
  // SleepDebt represents the difference between how many hours the user was actually asleep vs.
  // how many hours the user should have slept.
  private int mHoursSlept;
  private int mSleepDebt;
  private int mDesiredHoursOfSleep = 8;
  private String mMotionDataPath;
  private String mSoundDataPath;
  private String mSleepNotes;

  private List<SensorData> mSensorDataList;


  public JournalEntry() {

    this(UUID.randomUUID());
  }

  public JournalEntry(UUID id) {
    mId = id;
    mWakeDateAndTime = new Date();

    // Note: Some code I found to do Time math.
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(mWakeDateAndTime);
    calendar.add(Calendar.HOUR, -1 * (mDesiredHoursOfSleep));

    mSleepDateAndTime = calendar.getTime();
  }

  public int getHoursSlept() {
    long sleepDiff =
        Math.abs(mWakeDateAndTime.getTime() - mSleepDateAndTime.getTime()) / (1000 * 60 * 60);
    mHoursSlept = (int) sleepDiff;
    return mHoursSlept;
  }

  // SleepDebt represents the difference between how many hours the user was actually asleep vs.
  // how many hours the user should have slept.
  public int getSleepDebt() {
    mSleepDebt = mDesiredHoursOfSleep - getHoursSlept();
    return mSleepDebt;
  }

  public String getWakeHourAndMinute() {

    DateFormat df = new SimpleDateFormat("h:mm a");
    String fWakeTime = df.format(mWakeDateAndTime);

    return fWakeTime;
  }

  public void setWakeTime(Date wakeTime) {

    Calendar newDate = Calendar.getInstance();
    newDate.setTime(wakeTime);

    Calendar mTempWakeDateAndTime = Calendar.getInstance();

    mTempWakeDateAndTime.setTime(mWakeDateAndTime);             // Set to current SleepTimeAndDate
    mTempWakeDateAndTime.set(Calendar.HOUR_OF_DAY, newDate.get(Calendar.HOUR_OF_DAY));
    mTempWakeDateAndTime.set(Calendar.MINUTE, newDate.get(Calendar.MINUTE));
    this.mWakeDateAndTime = mTempWakeDateAndTime.getTime();
  }


  public String getSleepHourAndMinute() {

    DateFormat df = new SimpleDateFormat("h:mm a");
    String fSleepTime = df.format(mSleepDateAndTime);

    return fSleepTime;
  }

  public Date getWakeDateAndTime() {

    return this.mWakeDateAndTime;
  }

  public void setSleepDateAndTime(Date sleepDateAndTime) {
    this.mSleepDateAndTime = sleepDateAndTime;
  }

  public void setWakeDateAndTime(Date wakeDateAndTime) {
    this.mWakeDateAndTime = wakeDateAndTime;
  }


  public UUID getId() {
    return mId;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    this.mTitle = title;
  }

  public Date getSleepDateAndTime() {
    return mSleepDateAndTime;
  }

  // Returns Values in the Form of: Dec 25
  public String getSleepMonthAndDay() {

    DateFormat df = new SimpleDateFormat("MMM dd"); // Set Parse Grammar.

    return df.format(mSleepDateAndTime);
  }

  // Returns Values in the Form of: Dec 25
  public String getWakeMonthAndDay() {

    DateFormat df = new SimpleDateFormat("MMM dd"); // Set Parse Grammar.

    return df.format(mWakeDateAndTime);
  }

  // Returns Values in the Form of: MM/DD/YYYY
  public String getNumericSleepDate() {

    DateFormat df = new SimpleDateFormat("MM/dd/YY"); // Set Parse Grammar.

    return df.format(mSleepDateAndTime);
  }

  // Returns Values in the Form of: MM/DD/YYYY
  public String getNumericWakeDate() {

    DateFormat df = new SimpleDateFormat("MM/dd/YY"); // Set Parse Grammar.

    return df.format(mWakeDateAndTime);
  }

  public void setSleepDate(Date date) {
    Calendar newDate = Calendar.getInstance();
    newDate.setTime(date);

    Calendar mTempSleepDateAndTime = Calendar.getInstance();

    mTempSleepDateAndTime.setTime(mSleepDateAndTime);             // Set to current SleepTimeAndDate
    mTempSleepDateAndTime.set(Calendar.YEAR, newDate.get(Calendar.YEAR));
    mTempSleepDateAndTime.set(Calendar.MONTH, newDate.get(Calendar.MONTH));
    mTempSleepDateAndTime.set(Calendar.DAY_OF_MONTH, newDate.get(Calendar.DAY_OF_MONTH));
    this.mSleepDateAndTime = mTempSleepDateAndTime.getTime();
  }

  public void setWakeDate(Date date) {

    Calendar newDate = Calendar.getInstance();
    newDate.setTime(date);

    Calendar mTempWakeDateAndTime = Calendar.getInstance();

    mTempWakeDateAndTime.setTime(mWakeDateAndTime);             // Set to current SleepTimeAndDate
    mTempWakeDateAndTime.set(Calendar.YEAR, newDate.get(Calendar.YEAR));
    mTempWakeDateAndTime.set(Calendar.MONTH, newDate.get(Calendar.MONTH));
    mTempWakeDateAndTime.set(Calendar.DAY_OF_MONTH, newDate.get(Calendar.DAY_OF_MONTH));
    this.mWakeDateAndTime = mTempWakeDateAndTime.getTime();
  }

  public int getDesiredHoursOfSleep() {
    return mDesiredHoursOfSleep;
  }

  public void setSleepTime(Date date) {

    Calendar newDate = Calendar.getInstance();
    newDate.setTime(date);

    Calendar mTempSleepDateandTime = Calendar.getInstance();

    mTempSleepDateandTime.setTime(mSleepDateAndTime); // Set to current SleepTimeAndDate
    mTempSleepDateandTime.set(Calendar.HOUR_OF_DAY, newDate.get(Calendar.HOUR_OF_DAY));
    mTempSleepDateandTime.set(Calendar.MINUTE, newDate.get(Calendar.MINUTE));
    this.mSleepDateAndTime = mTempSleepDateandTime.getTime();
  }

  public String getMotionDataPath() {
    return mMotionDataPath;
  }

  public void setMotionDataPath(String motionDataPath) {
    mMotionDataPath = motionDataPath;
  }

  public String getSoundDataPath() {
    return mSoundDataPath;
  }

  public void setSoundDataPath(String soundDataPath) {
    mSoundDataPath = soundDataPath;
  }

  public String getSleepNotes() {
    return mSleepNotes;
  }

  public void setSleepNotes(String sleepNotes) {
    mSleepNotes = sleepNotes;
  }

  public List<SensorData> getSensorDataList() {
    return mSensorDataList;
  }

  public void setSensorDataList(List<SensorData> sensorDataList) {
    mSensorDataList = sensorDataList;
  }
}
