package com.ucr.buzuka.siestazzz.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import android.content.Context;

import javax.xml.datatype.Duration;

/**
 * Created by Rick Boshae on 2/18/18.
 */


public class JournalEntry {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Date mSleepTime;
    private Date mWakeTime;
    private int mHoursSlept; // SleepDebt represents the difference between how many hours the user was actually asleep vs. how many hours the user should have slept.
    private int mSleepDebt;
    private int desiredHoursOfSleep;



    public JournalEntry() {
        mDate = new Date();
        mId = UUID.randomUUID();
        mSleepTime = mDate;
        desiredHoursOfSleep = 8;

        // Some code I found to do Time math.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mSleepTime);
        calendar.add(Calendar.HOUR, 8);

        mWakeTime = calendar.getTime();
    }

    public int getHoursSlept() {
        long sleepDiff = Math.abs(mWakeTime.getTime() - mSleepTime.getTime())/(1000*60*60);
        mHoursSlept = (int)sleepDiff;
        return mHoursSlept;
    }

    // SleepDebt represents the difference between how many hours the user was actually asleep vs. how many hours the user should have slept.
    public int getSleepDebt() {

        mSleepDebt = desiredHoursOfSleep - getHoursSlept();
        return mSleepDebt;
    }

    public String getWakeTime() {

        DateFormat df = new SimpleDateFormat("h:mm a");
        String fWakeTime = df.format(mWakeTime);

        return fWakeTime;
    }

    public void setWake(Time wakeTime) {
        this.mWakeTime = wakeTime;
    }



    public String getSleepTime() {

        DateFormat df = new SimpleDateFormat("h:mm a");
        String fSleepTime = df.format(mSleepTime);

        return fSleepTime;
    }

    public void setSleepTime(Time sleepTime) {
        this.mSleepTime = sleepTime;
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

    public Date getDate() {
        return mDate;
    }

    public String getDateMonthAndDay() {

        DateFormat df = new SimpleDateFormat("MMM dd"); // Set Parse Grammar.

        return df.format(mDate);
    }

    public void setDate(Date date) {
        this.mDate = date;
    }
}
