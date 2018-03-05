package com.ucr.buzuka.siestazzz.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by jakex on 2/26/2018.
 */
@Entity
public class Session {

    @PrimaryKey
    @NonNull
    private String sessionID;
    @ColumnInfo
    private String path;
    @ColumnInfo
    private long sleepDateAndTime; // keeps track of sleep date and time
    @ColumnInfo
    private long wakeDateAndTime;  // keeps track of time and date
    @ColumnInfo
    private String sleepNotes;
    
    public Session() {
    }

    //add starting session data
    @Ignore
    public Session(String sessionID, String path, long sleepDateAndTime, long wakeDateAndTime, String passInSleepNotes){
        this.sessionID = sessionID;
        this.path = path;
        this.sleepDateAndTime = sleepDateAndTime;
        this.wakeDateAndTime = wakeDateAndTime;
        this.sleepNotes = passInSleepNotes;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path=path;
    }
    public long getSleepDateAndTime() {
        return sleepDateAndTime;
    }

    public void setSleepDateAndTime(long sleepDateAndTime) {
        this.sleepDateAndTime = sleepDateAndTime;
    }

    public long getWakeDateAndTime() {
        return wakeDateAndTime;
    }

    public void setWakeDateAndTime(long wakeDateAndTime) {
        this.wakeDateAndTime = wakeDateAndTime;
    }

    public String getSleepNotes() {
        return sleepNotes;
    }

    public void setSleepNotes(String sleepNotes) {
        this.sleepNotes = sleepNotes;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionID='" + sessionID + '\'' +
                ", path='" + path + '\'' +
                ", sleepDateAndTime=" + sleepDateAndTime +
                ", wakeDateAndTime=" + wakeDateAndTime +
                ", sleepNotes='" + sleepNotes + '\'' +
                '}';
    }
}
