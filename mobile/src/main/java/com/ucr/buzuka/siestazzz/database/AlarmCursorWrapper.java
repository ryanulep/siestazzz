package com.ucr.buzuka.siestazzz.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ucr.buzuka.siestazzz.database.AlarmDbSchema.AlarmTable;
import com.ucr.buzuka.siestazzz.model.Alarm;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Rick Boshae on 3/4/2018.
 * AlarmCursorWrapper takes care of having to repeat lines of code to read from the database.
 */

public class AlarmCursorWrapper extends CursorWrapper {
    public AlarmCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Alarm getAlarm() {
        String uuidString = getString(getColumnIndex(AlarmTable.Cols.UUID));
        String title = getString(getColumnIndex(AlarmTable.Cols.TITLE));
        long time = getLong(getColumnIndex(AlarmTable.Cols.TIME));
        int isActive = getInt(getColumnIndex(AlarmTable.Cols.ACTIVE));
        int isSmart = getInt(getColumnIndex(AlarmTable.Cols.SMART));

        Alarm alarm = new Alarm(UUID.fromString(uuidString));
        alarm.setAlarmTitle(title);
        alarm.setAlarmTime(new Date(time));
        alarm.setActive( isActive != 0);
        alarm.setSmart( isSmart != 0);

        return alarm;
    }
}
