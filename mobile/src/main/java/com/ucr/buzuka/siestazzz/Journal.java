package com.ucr.buzuka.siestazzz;

import android.content.Context;

import java.util.ArrayList;
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
        for (int i = 0; i < 14; i++) {
            JournalEntry journalEntry = new JournalEntry();
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
