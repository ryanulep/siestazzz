package com.ucr.buzuka.siestazzz.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ucr.buzuka.siestazzz.model.SensorReadout;

import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by jakex on 2/11/2018.
 */

public class JSONHelper {
    private static final String FILE_NAME = "readout.jsosn";
    private static final String TAG = "JSONHelper";

    public static boolean exportToJSON(Context context, List<SensorReadout> sensorReadoutList){
        DataItems readoutData = new DataItems();
        readoutData.setSensorReadouts(sensorReadoutList);
        Gson gson = new Gson();
        String jsonString = gson.toJson(readoutData);
        Log.i(TAG, "exportToJSON: " + jsonString);

        FileOutputStream fileOutputStream = null;
        //File file = new File()

        return false;
    }

    public static List<SensorReadout> importFromJSON(Context context) {
        return null;
    }

    static class DataItems{
        List<SensorReadout> sensorReadouts;

        public List<SensorReadout> getSensorReadouts() {
            return sensorReadouts;
        }

        public void setSensorReadouts(List<SensorReadout> sensorReadouts) {
            this.sensorReadouts = sensorReadouts;
        }
    }
}
