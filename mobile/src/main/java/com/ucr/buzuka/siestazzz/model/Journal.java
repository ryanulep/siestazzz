package com.ucr.buzuka.siestazzz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ucr.buzuka.siestazzz.database.UnifiedBaseHelper;
import com.ucr.buzuka.siestazzz.database.JournalEntryCursorWrapper;
import com.ucr.buzuka.siestazzz.database.JournalEntryDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/18/18.
 */

public class Journal {

    static final String TAG = "Journal";
    private static Journal sJournal;

    // Variables needed to add SQLiteOpenHelper to get rid of the grunt work of opening a SQLiteDatabase.
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Journal get(Context context) {
        // if an instance of Journal does not exist, create one.
        if (sJournal == null) {
            sJournal = new Journal(context);
        }
        return sJournal;
    }

    private Journal(Context context){

        mContext = context.getApplicationContext();

        /**
         * When getWritableDatabase() is called UnifiedBaseHelper will do the following:
         *  - Open /data/data/<path>/databases/journalEntryBase.db
         *  - If this is the first time the database has been created, call onCreate(SQLiteDatabase), then save out the
         *    latest version number
         *  - If hits is not the first time, check the version umber in the database. If the version number in
         *    UnifiedBaseHelper is higher call onUpgrade(SQLiteDatabase, int, int).
         */
        mDatabase = new UnifiedBaseHelper(mContext)
                .getWritableDatabase();
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
        List<JournalEntry> journalEntries = new ArrayList<>();

        JournalEntryCursorWrapper cursor = queryJournalEntries(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                journalEntries.add(cursor.getJournalEntry());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return journalEntries;
    }

    public JournalEntry getJournalEntry(UUID id) {
        JournalEntryCursorWrapper cursor = queryJournalEntries(
                JournalEntryDbSchema.JournalEntryTable.Cols.UUID + " = ?",
                new String[]{ id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getJournalEntry();
        }   finally {
            cursor.close();
        }
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

    public void deleteJournalEntry(JournalEntry journalEntry) {
        String uuidString = journalEntry.getId().toString();
        ContentValues values = getContentValues(journalEntry);

        // TODO: Delete associated sound and data files.
        mDatabase.delete(JournalEntryDbSchema.JournalEntryTable.NAME,
                JournalEntryDbSchema.JournalEntryTable.Cols.UUID + " LIKE ?",
                new String[] { uuidString });
        Log.d(TAG, "deleteJournalEntry: deleted");
    }

    /**
     * Reading in data from SQLite is done using the query(...) method.
     */
    private JournalEntryCursorWrapper queryJournalEntries(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                JournalEntryDbSchema.JournalEntryTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null,  // having
                null  // orderBy
        );

        return new JournalEntryCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(JournalEntry journalEntry) {
        // Using Content Values. Writes and updates to databases are done with the assistance of a class called ContentView.
        // ContentValues is a key-value store class, like Java's HashMap or Bundles. ContentValues is specifically designed
        // to store the kinds of data SQLite can hold.
        ContentValues values = new ContentValues();
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.UUID, journalEntry.getId().toString());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.SLEEPDATEANDTIME, journalEntry.getSleepDateAndTime().getTime());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.WAKEDATEANDTIME, journalEntry.getWakeDateAndTime().getTime());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.MOTIONDATAPATH, journalEntry.getMotionDataPath());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.SOUNDDATAPATH, journalEntry.getSoundDataPath());
        values.put(JournalEntryDbSchema.JournalEntryTable.Cols.NOTES, journalEntry.getSleepNotes());

        return values;
    }
}
