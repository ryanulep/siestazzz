package com.ucr.buzuka.siestazzz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ucr.buzuka.siestazzz.database.JournalEntryDbSchema.JournalEntryTable;

/**
 * Created by Rick Boshae on 3/3/2018.
 */

/**
 * UnifiedBaseHelper handles four core steps.
 *  - Check to see whether the database exists
 *  - If the database does not exist, create it and create the a table and initial data it needs.
 *  - If it does, open it up and see what version of UnifiedDbSchema has.
 *  - If it is an old version, upgrade to a new version.
 */

public class UnifiedBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "unifiedBase.db";

    public UnifiedBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    public void onCreate(SQLiteDatabase db) {

        // This is part of a very important two-step process. First, write the initial part of your SQL creation code.
        db.execSQL("create table " + JournalEntryTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                JournalEntryTable.Cols.UUID + ", " +
                JournalEntryTable.Cols.SLEEPDATEANDTIME + ", " +
                JournalEntryTable.Cols.WAKEDATEANDTIME + ", " +
                JournalEntryTable.Cols.MOTIONDATAPATH + ", " +
                JournalEntryTable.Cols.SOUNDDATAPATH + ", " +
                JournalEntryTable.Cols.NOTES +
                ")"
        );

        // Brought over alarm table
        // This is part of a very important two-step process. First, write the initial part of your SQL creation code.
        db.execSQL("create table " + AlarmDbSchema.AlarmTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AlarmDbSchema.AlarmTable.Cols.UUID + ", " +
                AlarmDbSchema.AlarmTable.Cols.TITLE + ", " +
                AlarmDbSchema.AlarmTable.Cols.TIME + ", " +
                AlarmDbSchema.AlarmTable.Cols.ACTIVE + ", " +
                AlarmDbSchema.AlarmTable.Cols.SMART +
                ")"
        );

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
