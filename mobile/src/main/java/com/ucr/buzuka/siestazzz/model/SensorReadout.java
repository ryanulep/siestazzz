package com.ucr.buzuka.siestazzz.model;

/**
 * Created by jakex on 2/11/2018.
 */

//new table for sensor readout
public class SensorReadout {
    private String itemId; //unique sensor readout id
    private String itemName; //sensor readout name
    private long curTime;
    private long lastUpdate;
    private float speed;
    private float x;
    private float y;
    private float z;

    public SensorReadout() {
    }

    public SensorReadout(String itemId, String itemName, long curTime, long lastUpdate, float speed, float x, float y, float z) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.curTime = curTime;
        this.lastUpdate = lastUpdate;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getCurTime() {
        return curTime;
    }

    public void setCurTime(long curTime) {
        this.curTime = curTime;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "SensorReadout{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", curTime=" + curTime +
                ", lastUpdate=" + lastUpdate +
                ", speed=" + speed +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
