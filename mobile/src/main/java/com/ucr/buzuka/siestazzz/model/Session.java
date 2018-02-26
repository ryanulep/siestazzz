package com.ucr.buzuka.siestazzz.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by jakex on 2/26/2018.
 */
@Entity
public class Session {
    @PrimaryKey
    @NonNull
    private String sessionID;

    public Session() {
    }

    @Ignore
    public Session(String sessionID){
        this.sessionID = sessionID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionID='" + sessionID + '\'' +
                '}';
    }
}
