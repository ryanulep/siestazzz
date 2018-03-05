package com.ucr.buzuka.siestazzz.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by jakex on 2/11/2018.
 */

/** new table for sensor readout */
@Entity
public class SensorReadout implements Parcelable {

/*  ROOM */
    @PrimaryKey
    @NonNull
    private String readOutID;
    @ColumnInfo
    @NonNull
    private String sessionID;
    @ColumnInfo
    private long current_Time;
    @ColumnInfo
    private float speed;
    @ColumnInfo
    private int volume;
    
/** Constructors */
    public SensorReadout() {
    }

    @Ignore
    public SensorReadout(String sessionID, long curTime, float speed, int volume) {
        this.readOutID = UUID.randomUUID().toString();

        this.sessionID  = sessionID;
        this.current_Time = curTime;
        this.speed = speed;
        this.volume=volume;
    }
    
/** Setters and getters */
    public String getReadOutID() { return readOutID; }

    public void setReadOutID( String readOutID) { this.readOutID = readOutID;}

    public String getSessionID() { return sessionID; }

    public void setSessionID( String sessionID) { this.sessionID = sessionID;}

    public long getCurrent_Time() {
        return current_Time;
    }

    public void setCurrent_Time(long current_Time) {
        this.current_Time = current_Time;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getVolume() { return volume; }

    public void setVolume(int volume) { this.volume=volume; }


    /**
     * Convert all object in data container to a single string
     */
    @Override
    public String toString() {
        return "SensorReadout{" +
                "readOutID='" + readOutID + '\'' +
                ", sessionID='" + sessionID + '\'' +
                ", current_Time=" + current_Time +
                ", speed=" + speed +
                ", volume=" + volume +
                '}';
    }

    /**Parcelables functionality for passing data(key value pairs) around activities
 * using Code -> generate -> parcelables
 * usage: start Intent  ->   intent.putExtra(key, value)
 *        in activity   ->   getIntent().getExtras().getParcelable(key)*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.readOutID);
        dest.writeString(this.sessionID);
        dest.writeLong(this.current_Time);
        dest.writeFloat(this.speed);
        dest.writeInt(this.volume);
    }

    protected SensorReadout(Parcel in) {
        this.readOutID = in.readString();
        this.sessionID = in.readString();
        this.current_Time = in.readLong();
        this.speed = in.readFloat();
        this.volume = in.readInt();
    }

    public static final Creator<SensorReadout> CREATOR = new Creator<SensorReadout>() {
        @Override
        public SensorReadout createFromParcel(Parcel source) {
            return new SensorReadout(source);
        }

        @Override
        public SensorReadout[] newArray(int size) {
            return new SensorReadout[size];
        }
    };
}
