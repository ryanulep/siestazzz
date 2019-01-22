package com.ucr.buzuka.siestazzz.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.google.gson.Gson;
import com.ucr.buzuka.siestazzz.model.SensorReadout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jakex on 2/11/2018.
 */

public class JSONHelper {

  //private static final String FILE_NAME = "readout.json";
  private static final String TAG = "JSONHelper";

  public static boolean exportToJSON(Context context, List<SensorReadout> sensorReadoutList) {
    DataItems readoutData = new DataItems();
    readoutData.setSensorReadouts(sensorReadoutList);
    Gson gson = new Gson();
    String jsonString = gson.toJson(readoutData);
    Log.i(TAG, "exportToJSON: " + jsonString);
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
        .format(Calendar.getInstance().getTime());
    timeStamp += ".json";

    FileOutputStream fileOutputStream = null;

    //create directory
    File folder = new File(Environment.getExternalStorageDirectory(), "/Siestazzz/");
    if (!folder.exists()) {
      if (!folder.mkdirs()) {
        Log.e(TAG, "Problem creating Image folder");
      }
    }

    File file = new File(folder.toString(), timeStamp);
    try {
      fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(jsonString.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fileOutputStream != null) {
        try {
          fileOutputStream.close();
          return true;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return false;
  }

  public static List<SensorReadout> importFromJSON(Context context) {
    return null;
  }

  static class DataItems {

    List<SensorReadout> sensorReadouts;

    public List<SensorReadout> getSensorReadouts() {
      return sensorReadouts;
    }

    public void setSensorReadouts(List<SensorReadout> sensorReadouts) {
      this.sensorReadouts = sensorReadouts;
    }
  }
}
