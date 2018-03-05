package com.ucr.buzuka.siestazzz.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/14/2018.
 *
 * BellTower maintains a list of alarms created by the user.
 *
 * BellTower follows the Singleton data pattern. This ensures that only a single list of alarms
 * can be accessed across the entire application. Accomplished by private constructor.
 */

public class BellTower {
    private static BellTower sBellTower;
            // the s prefix on sBelltower is a naming convention indicating that sAlarmKeeper is static.
    private List<Alarm> mAlarms;

    public static BellTower get(Context context) {
        // if an instance of AlarmKeeper does not exist, create one.
        if (sBellTower == null) {
            sBellTower = new BellTower(context);
        }
        return sBellTower;
    }

    private BellTower(Context context) {
        mAlarms = new ArrayList<>();

        // For now populate a list of alarms
        for (int i = 0; i < 5; i++) {
            Alarm alarm = new Alarm();
            alarm.setAlarmTitle("Alarm #" + i);
            alarm.setActive(i % 2 == 0); // Set every other alarm active
            mAlarms.add(alarm);
        }
    }

    public List<Alarm> getAlarms() {
        return mAlarms;
    }

    public Alarm getAlarm(UUID id) {
        for (Alarm alarm : mAlarms) {
            if (alarm.getId().equals(id)) {
                return alarm;
            }
        }
        return null;
    }

    public void addAlarm(Alarm alarm) {
        mAlarms.add(alarm);
    }
}
