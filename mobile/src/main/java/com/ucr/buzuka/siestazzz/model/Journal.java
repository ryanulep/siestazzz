package com.ucr.buzuka.siestazzz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ucr.buzuka.siestazzz.JournalEntryBaseHelper;
import com.ucr.buzuka.siestazzz.database.JournalEntryDbSchema;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/18/18.
 */

public class Journal {
    private static Journal sJournal;

//    private List<JournalEntry> mJournalEntries;
    // Variables needed to add SQLiteOpenHelper to get rid of the grunt work of opening a SQLiteDatabase.
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Journal get(Context context) {
        if (sJournal == null) {
            sJournal = new Journal(context);
        }
        return sJournal;
    }

    private Journal(Context context){

        mContext = context.getApplicationContext();

        /**
         * When getWritableDatabase() is called JournalEntryBaseHelper will do the following:
         *  - Open /data/data/<path>/databases/journalEntryBase.db
         *  - If this is the first time the database has been created, call onCreate(SQLiteDatabase), then save out the
         *    latest version number
         *  - If hits is not the first time, check the version umber in the database. If the version number in
         *    JournalEntryBaseHelper is higher call onUpgrade(SQLiteDatabase, int, int).
         */
        mDatabase = new JournalEntryBaseHelper(mContext)
                .getWritableDatabase();

//        for (int i = 0; i < 14; i++) {
//            JournalEntry journalEntry = new JournalEntry();
//            journalEntry.setTitle("Journal Entry #" + i);
//
//            Date date = new Date();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.add((Calendar.DAY_OF_MONTH), -i);
//            journalEntry.setWakeDateAndTime(calendar.getTime());
//            calendar.add((Calendar.HOUR), -8);
//
//            journalEntry.setSleepDateAndTime(calendar.getTime());
//
//            mJournalEntries.add(journalEntry);
//        }
    }

    public void addJournalEntry(JournalEntry j) {
        // Inserting and updating rows to the database.
        ContentValues values = getContentValues(j);

        // The insert(...) method has two important arguments and one that is rarely used. The first
        // argument is the table you want to insert into - here, JournalEntryTable.NAME. The last
        // argument is the data you want to put in.
        mDatabase.insert(JournalEntryDbSchema.JournalEntryTable.NAME, null, values);
    }

    public List<JournalEntry> getJournalEntries() {
        return new ArrayList<>();
    }

    public JournalEntry getJournalEntry(UUID id) {
        return null;
    }

    public void updateJournalEntry(JournalEntry journalEntry) {
        String uuidString = journalEntry.getId().toString();
        ContentValues values = getContentValues(journalEntry);

        // The update(...) method starts off similar to insert(...) - you pass a table name you want to
        // update and the ContentValues you want to assign to each row you update. The last part is
        // different because now you have to specify which rows get updated. You do that by building
        // a where clause (third argument) and the nspecify values for the arguments in the where clause
        // (the final String[] array). Note: " = ?" prevents a SQL injection attack.
        mDatabase.update(JournalEntryDbSchema.JournalEntryTable.NAME, values,
                JournalEntryDbSchema.JournalEntryTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    /**
     * Reading in data from SQLite is done using the query(...) method.
     */
    private Cursor queryJournalEntries(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                JournalEntryDbSchema.JournalEntryTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return cursor;
    }

    private static ContentValues getContentValues(JournalEntry journalEntry) {
        // Using Content Values. Writes and updates to databases are done with the assistance of a class called ContentView.
        // ContentValues is a key-value store class, like Java's HashMap or Bundles. ContentValues is specifically designed
        // to store the kinds of data SQLite can hold.
        ContentValues values = new ContentValues();
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.UUID, journalEntry.getId().toString());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.SLEEPDATEANDTIME, journalEntry.getSleepDateAndTime().toString());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.WAKEDATEANDTIME, journalEntry.getWakeDateAndTime().toString());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.MOTIONDATAPATH, journalEntry.getMotionDataPath());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.SOUNDDATAPATH, journalEntry.getSoundDataPath());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.NOTES, journalEntry.getSleepNotes());

        return values;
    }
}
