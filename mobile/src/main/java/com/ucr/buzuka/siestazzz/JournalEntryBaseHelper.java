package com.ucr.buzuka.siestazzz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ucr.buzuka.siestazzz.database.JournalEntryDbSchema;
import com.ucr.buzuka.siestazzz.database.JournalEntryDbSchema.JournalEntryTable;
import com.ucr.buzuka.siestazzz.model.JournalEntry;

/**
 * Created by Rick Boshae on 3/3/2018.
 */

/**
 * JournalEntryBaseHelper handles four core steps.
 *  - Check to see whether the database exists
 *  - If the database does not exist, create it and create the a table and initial data it needs.
 *  - If it does, open it up and see what version of JournalEntryDbSchema has.
 *  - If it is an old version, upgrade to a new version.
 */

public class JournalEntryBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "journalEntryBase.db";

    public JournalEntryBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    public void onCreate(SQLiteDatabase db) {

        // This is part of a very important two-step process. First, write the initial part of your SQL creation code.
        db.execSQL("create table " + JournalEntryTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                JournalEntryTable.Cols.UUID + ", " +
                JournalEntryTable.Cols.SLEEPDATEANDTIME + ", " +
                JournalEntryTable.Cols.WAKEDATEANDTIME + ", " +
                JournalEntryTable.Cols.MOTIONDATA + ", " +
                JournalEntryTable.Cols.SOUNDDATA + ", " +
                JournalEntryTable.Cols.NOTES +
                ")"
        );

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
