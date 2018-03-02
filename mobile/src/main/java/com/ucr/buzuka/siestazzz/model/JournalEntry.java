package com.ucr.buzuka.siestazzz.model;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/18/18.
 */


public class JournalEntry {
    private UUID mId;
    private String mTitle;
//    private Date mSleepDate;
//    private Date mWakeDate;
    private Date mSleepDateAndTime; // keeps track of sleep date and time
    private Date mWakeDateAndTime;  // keeps track of time and date
    private int mHoursSlept; // SleepDebt represents the difference between how many hours the user was actually asleep vs. how many hours the user should have slept.
    private int mSleepDebt;
    private int mDesiredHoursOfSleep;
    private String mRecordingPlaybackDirectory;



    public JournalEntry() {
        mId = UUID.randomUUID();
        mDesiredHoursOfSleep = 8;

        mWakeDateAndTime = new Date();

        // Note: Some code I found to do Time math.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mWakeDateAndTime);
        calendar.add(Calendar.HOUR, -1*(mDesiredHoursOfSleep));

        mSleepDateAndTime = calendar.getTime();
    }

    public int getHoursSlept() {
        long sleepDiff = Math.abs(mWakeDateAndTime.getTime() - mSleepDateAndTime.getTime())/(1000*60*60);
        mHoursSlept = (int)sleepDiff;
        return mHoursSlept;
    }

    // SleepDebt represents the difference between how many hours the user was actually asleep vs. how many hours the user should have slept.
    public int getSleepDebt() {
        mSleepDebt = mDesiredHoursOfSleep - getHoursSlept();
        return mSleepDebt;
    }

    public String getWakeDateAndTime() {

        DateFormat df = new SimpleDateFormat("h:mm a");
        String fWakeTime = df.format(mWakeDateAndTime);

        return fWakeTime;
    }

    public void setWakeTime(Time wakeTime) {
        this.mWakeDateAndTime = wakeTime;
    }



    public String getSleepHourAndMinute() {

        DateFormat df = new SimpleDateFormat("h:mm a");
        String fSleepTime = df.format(mSleepDateAndTime);

        return fSleepTime;
    }

    public String getWakeHourAndMinute() {

        DateFormat df = new SimpleDateFormat("h:mm a");
        String fSleepTime = df.format(mWakeDateAndTime);

        return fSleepTime;
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
        this.mSleepDateAndTime = date;
    }
    public void setWakeDate(Date date) {
        this.mWakeDateAndTime = date;
    }

    public int getDesiredHoursOfSleep() {
        return mDesiredHoursOfSleep;
    }

    public void setSleepTime(Date date) {

        Calendar newDate = Calendar.getInstance();
        newDate.setTime(date);

        Calendar mTempSleepDateandTime = Calendar.getInstance();

        mTempSleepDateandTime.setTime(mSleepDateAndTime);             // Set to current SleepTimeAndDate
        mTempSleepDateandTime.set(Calendar.HOUR, newDate.get(Calendar.HOUR));
        mTempSleepDateandTime.set(Calendar.MINUTE, newDate.get(Calendar.MINUTE));
        this.mSleepDateAndTime = mTempSleepDateandTime.getTime();
    }

    public void setRecordingPlaybackDirectory(String file_location) {
        this.mRecordingPlaybackDirectory = file_location;
    }
}
