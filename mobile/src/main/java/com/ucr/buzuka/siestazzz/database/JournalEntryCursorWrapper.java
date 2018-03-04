package com.ucr.buzuka.siestazzz.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ucr.buzuka.siestazzz.model.JournalEntry;

/**
 * Created by Rick Boshae on 3/3/2018.
 * JournalEntryCursorWrapper takes care of having to repeat lines of code to read from the database.
 */

public class JournalEntryCursorWrapper extends CursorWrapper {
    public JournalEntryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public JournalEntry getJournalEntry() {
        String uuidString = getString(getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.UUID));
        String sleepDateAndTime = getString(getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.SLEEPDATEANDTIME));
        String wakeDateAndTime = getString(getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.WAKEDATEANDTIME));
        String motionDataPath = getString(getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.MOTIONDATAPATH));
        String soundDataPath = getString(getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.SOUNDDATAPATH));
        String notes = getString(getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.NOTES));

        return null;
    }
}
