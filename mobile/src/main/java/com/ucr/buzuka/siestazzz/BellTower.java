package com.ucr.buzuka.siestazzz;

import android.content.Context;

/**
 * Created by Rick Boshae on 2/14/2018.
 *
 * BellTower maintains a list of alarms created by the user.
 *
 * BellTower follows the Singleton data pattern. This ensures that only a single list of alarms
 * can be accessed across.
 */

public class BellTower {
    private static BellTower sBellTower;          // the s prefix on sBelltower is a naming convention indicating that sAlarmKeeper is static.

    public static BellTower get(Context context) {
        // if an instance of AlarmKeeper does not exist, create one.
        if (sBellTower == null) {
            sBellTower = new BellTower(context);
        }
        return sBellTower;
    }

    private BellTower(Context context) {
        // empty
    }



}
