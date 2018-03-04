package com.ucr.buzuka.siestazzz.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ucr.buzuka.siestazzz.JournalEntryBaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

        // Create an ArrayList<>() which will store all the journal entries.
//        mJournalEntries = new ArrayList<>();

        // Temp Variables for Journal
        for (int i = 0; i < 14; i++) {
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setTitle("Journal Entry #" + i);

            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
            calendar.add((Calendar.DAY_OF_MONTH), -i);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);



//            Date gDate = new GregorianCalendar(year, month, day).getTime();

//            journalEntry.setSleepDate(gDate);
            journalEntry.setWakeDateAndTime(calendar.getTime());

            calendar.add((Calendar.HOUR), -8);

            journalEntry.setSleepDateAndTime(calendar.getTime());

            mJournalEntries.add(journalEntry);
        }
    }

    public List<JournalEntry> getJournalEntries() {

//        return mJournalEntries;
        return new ArrayList<>();
    }

    public JournalEntry getJournalEntry(UUID id) {
//        for (JournalEntry journalEntry : mJournalEntries) {
//            if(journalEntry.getId().equals(id)) {
//                return journalEntry;
//            }
//        }
        return null;
    }
}
