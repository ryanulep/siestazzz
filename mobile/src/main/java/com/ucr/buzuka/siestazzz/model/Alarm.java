package com.ucr.buzuka.siestazzz.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/15/2018.
 */

public class Alarm {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mActive;

    public Alarm() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mTitle = "Default Alarm";
    }

    public Alarm(String title, Date date, boolean active) {
        mId = UUID.randomUUID();
        mDate = date;
        mActive = active;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }
}
