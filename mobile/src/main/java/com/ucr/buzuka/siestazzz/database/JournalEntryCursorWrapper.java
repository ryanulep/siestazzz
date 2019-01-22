package com.ucr.buzuka.siestazzz.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.ucr.buzuka.siestazzz.model.JournalEntry;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Rick Boshae on 3/3/2018. JournalEntryCursorWrapper takes care of having to repeat
 * lines of code to read from the database.
 */

public class JournalEntryCursorWrapper extends CursorWrapper {

  public JournalEntryCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  public JournalEntry getJournalEntry() {
    String uuidString = getString(getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.UUID));
    long sleepDateAndTime = getLong(
        getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.SLEEPDATEANDTIME));
    long wakeDateAndTime = getLong(
        getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.WAKEDATEANDTIME));
    String motionDataPath = getString(
        getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.MOTIONDATAPATH));
    String soundDataPath = getString(
        getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.SOUNDDATAPATH));
    String sleepNotes = getString(
        getColumnIndex(JournalEntryDbSchema.JournalEntryTable.Cols.NOTES));

    JournalEntry journalEntry = new JournalEntry(UUID.fromString(uuidString));
    journalEntry.setSleepDateAndTime(new Date(sleepDateAndTime));
    journalEntry.setWakeDateAndTime(new Date(wakeDateAndTime));
    journalEntry.setMotionDataPath(motionDataPath);
    journalEntry.setSoundDataPath(soundDataPath);
    journalEntry.setSleepNotes(sleepNotes);

    return journalEntry;
  }
}
