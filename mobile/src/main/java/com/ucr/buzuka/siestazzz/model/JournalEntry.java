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
    private int desiredHoursOfSleep;
    private String mRecordingPlaybackDirectory;



    public JournalEntry() {
        mSleepDateAndTime = new Date();
        mId = UUID.randomUUID();
        desiredHoursOfSleep = 8;

        // Note: Some code I found to do Time math.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mSleepDateAndTime);
        calendar.add(Calendar.HOUR, 8);

        mWakeDateAndTime = calendar.getTime();
    }

    public int getHoursSlept() {
        long sleepDiff = Math.abs(mWakeDateAndTime.getTime() - mSleepDateAndTime.getTime())/(1000*60*60);
        mHoursSlept = (int)sleepDiff;
        return mHoursSlept;
    }

    // SleepDebt represents the difference between how many hours the user was actually asleep vs. how many hours the user should have slept.
    public int getSleepDebt() {
        mSleepDebt = desiredHoursOfSleep - getHoursSlept();
        return mSleepDebt;
    }

    public String getWakeDateAndTime() {

        DateFormat df = new SimpleDateFormat("h:mm a");
        String fWakeTime = df.format(mWakeDateAndTime);

        return fWakeTime;
    }

    public void setWake(Time wakeTime) {
        this.mWakeDateAndTime = wakeTime;
    }



    public String getSleepHourAndMinute() {

        DateFormat df = new SimpleDateFormat("h:mm a");
        String fSleepTime = df.format(mSleepDateAndTime);

        return fSleepTime;
    }

    public void setSleepDateAndTime(Time sleepDateAndTime) {
        this.mSleepDateAndTime = sleepDateAndTime;
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
    public String getDateMonthAndDay() {

        DateFormat df = new SimpleDateFormat("MMM dd"); // Set Parse Grammar.

        return df.format(mSleepDateAndTime);
    }

    // Returns Values in the Form of: MM/DD/YYYY
    public String getNumbericDate() {

        DateFormat df = new SimpleDateFormat("MM/dd/YY"); // Set Parse Grammar.

        return df.format(mSleepDateAndTime);
    }

    public void setSleepDate(Date date) {
        this.mSleepDateAndTime = date;
    }
    public void setWakeDate(Date date) {
        this.mWakeDateAndTime = date;
    }

    public void setRecordingPlaybackDirectory(String file_location) {
        this.mRecordingPlaybackDirectory = file_location;
    }
}
