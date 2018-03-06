package com.ucr.buzuka.siestazzz.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
