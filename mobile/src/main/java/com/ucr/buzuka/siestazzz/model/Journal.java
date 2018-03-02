package com.ucr.buzuka.siestazzz.model;

import android.content.Context;

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
    private List<JournalEntry> mJournalEntries;

    public static Journal get(Context context) {
        if (sJournal == null) {
            sJournal = new Journal(context);
        }
        return sJournal;
    }

    private Journal(Context context){
        // Create an ArrayList<>() which will store all the journal entries.
        mJournalEntries = new ArrayList<>();

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
        return mJournalEntries;
    }

    public JournalEntry getJournalEntry(UUID id) {
        for (JournalEntry journalEntry : mJournalEntries) {
            if(journalEntry.getId().equals(id)) {
                return journalEntry;
            }
        }
        return null;
    }
}
