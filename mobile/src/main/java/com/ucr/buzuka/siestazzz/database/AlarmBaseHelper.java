package com.ucr.buzuka.siestazzz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ucr.buzuka.siestazzz.database.AlarmDbSchema.AlarmTable;

/**
 * Created by Rick Boshae on 3/4/2018.
 */

/**
 * AlarmBaseHelper handles four core steps.
 *  - Check to see whether the database exists
 *  - If the database does not exist, create it and create the a table and initial data it needs.
 *  - If it does, open it up and see what version of AlarmDbSchema has.
 *  - If it is an old version, upgrade to a new version.
 */

public class AlarmBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "alarmBase.db";

    public AlarmBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    public void onCreate(SQLiteDatabase db) {

        // This is part of a very important two-step process. First, write the initial part of your SQL creation code.
        db.execSQL("create table " + AlarmDbSchema.AlarmTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AlarmTable.Cols.UUID + ", " +
                AlarmTable.Cols.TITLE + ", " +
                AlarmTable.Cols.TIME + ", " +
                AlarmTable.Cols.ACTIVE + ", " +
                AlarmTable.Cols.SMART +
                ")"
        );

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
