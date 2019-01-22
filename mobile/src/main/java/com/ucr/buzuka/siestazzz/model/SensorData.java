package com.ucr.buzuka.siestazzz.model;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rick Boshae and jakex on 3/5/2018.
 */

/**
 * new table for sensor readout
 */
@Entity
public class SensorData implements Parcelable {

  private long current_Time;
  private float speed;
  private int volume;

  /**
   * Constructors
   */
  public SensorData() {
  }

  public SensorData(long curTime, float speed, int volume) {
    this.current_Time = curTime;
    this.speed = speed;
    this.volume = volume;
  }

  /**
   * Setters and getters
   */
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

  public int getVolume() {
    return volume;
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }

  /**
   * Convert all object in data container to a single string
   */
  @Override
  public String toString() {
    return "SensorReadout{" +
        ", current_Time=" + current_Time +
        ", speed=" + speed +
        ", volume=" + volume +
        '}';
  }

  /**
   * Parcelables functionality for passing data(key value pairs) around activities using Code ->
   * generate -> parcelables usage: start Intent  ->   intent.putExtra(key, value) in activity ->
   * getIntent().getExtras().getParcelable(key)
   */

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(this.current_Time);
    dest.writeFloat(this.speed);
    dest.writeInt(this.volume);
  }

  protected SensorData(Parcel in) {
    this.current_Time = in.readLong();
    this.speed = in.readFloat();
    this.volume = in.readInt();
  }

  public static final Creator<SensorData> CREATOR = new Creator<SensorData>() {
    @Override
    public SensorData createFromParcel(Parcel source) {
      return new SensorData(source);
    }

    @Override
    public SensorData[] newArray(int size) {
      return new SensorData[size];
    }
  };
}
