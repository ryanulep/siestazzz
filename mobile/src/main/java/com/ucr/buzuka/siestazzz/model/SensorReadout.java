package com.ucr.buzuka.siestazzz.model;

/**
 * Created by jakex on 2/11/2018.
 */

//new table for sensor readout
public class SensorReadout {
    private long current_Time;
    private float speed;
    
//  Constructors
    public SensorReadout() {
    }

    public SensorReadout(long curTime, float speed) {
        this.current_Time = curTime;
        this.speed = speed;
    }
    
//  Setters and getters
    public long getCurTime() {
        return current_Time;
    }

    public void setCurTime(long curTime) {
        this.current_Time = curTime;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

//  convert all object in data container to a single string
    @Override
    public String toString() {
        return "SensorReadout{" +
                " currentTime= " + current_Time +
                ", speed= " + speed +
                '}';
    }
}
