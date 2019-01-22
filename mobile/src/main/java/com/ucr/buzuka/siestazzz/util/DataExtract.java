package com.ucr.buzuka.siestazzz.util;

import android.util.Log;
import com.jjoe64.graphview.series.DataPoint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataExtract {

  public static DataPoint[] prepareVolumeData(String path) {
    String valuePath = path + "/volumeValues.txt";
    File valueFile = new File(valuePath);
    String timePath = path + "/volumeTimes.txt";
    File timeFile = new File(timePath);
    String valueRaw = "";
    String timeRaw = "";

    try {
      FileReader fr = new FileReader(valueFile);
      BufferedReader br = new BufferedReader(fr);
      valueRaw = br.readLine();
      fr = new FileReader(timeFile);
      br = new BufferedReader(fr);
      timeRaw = br.readLine();
    } catch (IOException e) {
      Log.d("CONVERT", "SLIGHTLY LARGER OOPS");
    }

    String[] values = new String[0];
    String[] times = new String[0];

    if (valueRaw != "") {
      Log.d("CONVERT", "timeRaw=" + timeRaw);
      values = valueRaw.split(" ");
      times = timeRaw.split(" ");
      DataPoint[] result = new DataPoint[values.length];
      double start = Double.parseDouble(times[0]);
      for (int i = 0; i < values.length; i++) {
//                Log.d("CONVERT", "Value of times is:"+times[i]);
        double x = Double.parseDouble(times[i]) - start;
        double y = Double.parseDouble(values[i]);
        DataPoint temp = new DataPoint(x / 1000, y);
        result[i] = temp;
      }
      return result;
    } else {
      return null;
    }
  }

  public static DataPoint[] prepareSpeedData(String path) {
    String valuePath = path + "/speedValues.txt";
    File valueFile = new File(valuePath);
    String timePath = path + "/speedTimes.txt";
    File timeFile = new File(timePath);
    String valueRaw = "";
    String timeRaw = "";

    try {
      FileReader fr = new FileReader(valueFile);
      BufferedReader br = new BufferedReader(fr);
      valueRaw = br.readLine();
      fr = new FileReader(timeFile);
      br = new BufferedReader(fr);
      timeRaw = br.readLine();
    } catch (IOException e) {
      Log.d("CONVERT", "SLIGHTLY LARGER OOPS");
    }

    String[] values = new String[0];
    String[] times = new String[0];

    if (valueRaw != null) {
      values = valueRaw.split(" ");
      times = timeRaw.split(" ");
      double start = Double.parseDouble(times[0]);
      DataPoint[] result = new DataPoint[values.length];
      for (int i = 0; i < values.length; i++) {
//                Log.d("CONVERT", "Value of times is:"+times[i]);

        double x = Double.parseDouble(times[i]) - start;
        double y = Double.parseDouble(values[i]);
        DataPoint temp = new DataPoint(x / 1000, y * 10000);
        result[i] = temp;
      }
      Log.d("CONVERT", "Speed Success");
      return result;
    } else {
      Log.d("CONVERT", "Speed Failure");
      return null;
    }
  }
}
