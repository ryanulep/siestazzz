package com.ucr.buzuka.siestazzz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ucr.buzuka.siestazzz.database.AlarmCursorWrapper;
import com.ucr.buzuka.siestazzz.database.AlarmDbSchema;
import com.ucr.buzuka.siestazzz.database.UnifiedBaseHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/14/2018.
 * <p>
 * BellTower maintains a list of alarms created by the user.
 * <p>
 * BellTower follows the Singleton data pattern. This ensures that only a single list of alarms can
 * be accessed across the entire application. Accomplished by private constructor.
 */

public class BellTower {

  private static final String TAG = "BellTower";
  private static BellTower sBellTower; // the s prefix on sBelltower is a naming convention
  // indicating that sAlarmKeeper is static.

  // Variables needed to add SQLiteOpenHelper to get rid of the grunt work of opening a
  // SQLiteDatabase.
  private Context mContext;
  private SQLiteDatabase mDatabase;

  public static BellTower get(Context context) {
    // if an instance of BellTower does not exist, create one.
    if (sBellTower == null) {
      sBellTower = new BellTower(context);
    }
    return sBellTower;
  }

  private BellTower(Context context) {
    mContext = context.getApplicationContext();

    /*
     * When getWritableDatabase() is called UnifiedBaseHelper will do the following:
     *  - Open /data/data/<path>/databases/alarmBase.db
     *  - If this is the first time the database has been created, call onCreate(SQLiteDatabase),
     *  then save out the
     *    latest version number
     *  - If hits is not the first time, check the version umber in the database. If the version
     *  number in
     *    UnifiedBaseHelper is higher call onUpgrade(SQLiteDatabase, int, int).
     */
    mDatabase = new UnifiedBaseHelper(mContext).getWritableDatabase();
  }

  public void addAlarm(Alarm a) {
    // Inserting and updating rows to the database.
    ContentValues values = getContentValues(a);

    /* The insert(...) method has two important arguments and one that is rarely used. The first
     argument is the table you want to insert into - here, AlarmTable.NAME. The last
     argument is the data you want to put in.*/
    mDatabase.insert(AlarmDbSchema.AlarmTable.NAME, null, values);
  }

  public List<Alarm> getAlarms() {

    List<Alarm> alarms = new ArrayList<>();

    AlarmCursorWrapper cursor = queryAlarms(null, null);

    try {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        alarms.add(cursor.getAlarm());
        cursor.moveToNext();
      }
    } finally {
      cursor.close();
    }
    return alarms;
  }

  public Alarm getAlarm(UUID id) {
    AlarmCursorWrapper cursor = queryAlarms(
        AlarmDbSchema.AlarmTable.Cols.UUID + " = ?",
        new String[]{id.toString()}
    );
    try {
      if (cursor.getCount() == 0) {
        return null;
      }

      cursor.moveToFirst();
      return cursor.getAlarm();
    } finally {
      cursor.close();
    }
  }

  public void updateAlarm(Alarm alarm) {
    String uuidString = alarm.getId().toString();
    ContentValues values = getContentValues(alarm);

    /* The update(...) method starts off similar to insert(...) - you pass a table name you want to
     update and the ContentValues you want to assign to each row you update. The last part is
     different because now you have to specify which rows get updated. You do that by building
     a where clause (third argument) and then specify values for the arguments in the where clause
     (the final String[] array). Note: " = ?" prevents a SQL injection attack.*/
    mDatabase.update(AlarmDbSchema.AlarmTable.NAME, values,
        AlarmDbSchema.AlarmTable.Cols.UUID + " = ?",
        new String[]{uuidString});
  }

  public void deleteAlarm(Alarm alarm) {
    String uuidString = alarm.getId().toString();

    mDatabase.delete(AlarmDbSchema.AlarmTable.NAME,
        AlarmDbSchema.AlarmTable.Cols.UUID + " LIKE ?",
        new String[]{uuidString});
    Log.d(TAG, "deleteAlarm: deleted");
  }

  /**
   * Reading in data from SQLite is done using the query(...) method.
   */
  private AlarmCursorWrapper queryAlarms(String whereClause, String[] whereArgs) {
    Cursor cursor = mDatabase.query(
        AlarmDbSchema.AlarmTable.NAME,
        null, // columns - null selects all columns
        whereClause,
        whereArgs,
        null, // groupBy
        null,  // having
        null  // orderBy
    );

    return new AlarmCursorWrapper(cursor);
  }

  private static ContentValues getContentValues(Alarm alarm) {
    /* Using Content Values. Writes and updates to databases are done with the assistance of a
    class called ContentView.
     ContentValues is a key-value store class, like Java's HashMap or Bundles. ContentValues is
     specifically designed
     to store the kinds of data SQLite can hold.*/
    ContentValues values = new ContentValues();
    values.put(AlarmDbSchema.AlarmTable.Cols.UUID, alarm.getId().toString());
    values.put(AlarmDbSchema.AlarmTable.Cols.TITLE, alarm.getAlarmTitle());
    values.put(AlarmDbSchema.AlarmTable.Cols.TIME, alarm.getAlarmTime().getTime());
    values.put(AlarmDbSchema.AlarmTable.Cols.ACTIVE, alarm.isActive() ? 1 : 0);
    Log.i("BellTower", "alarm.isActive() ? 1 : 0 — check logic " + (alarm.isActive() ? 1 : 0));
    values.put(AlarmDbSchema.AlarmTable.Cols.SMART, alarm.isSmart() ? 1 : 0);

    return values;
  }


}
