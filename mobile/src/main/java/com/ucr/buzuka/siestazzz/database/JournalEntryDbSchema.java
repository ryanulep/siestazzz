package com.ucr.buzuka.siestazzz.database;

/**
 * Created by Rick Boshae on 3/3/2018.
 */

public class JournalEntryDbSchema {

    /**
     * An inner class to describe to describe the table.
     * The JournalEntryTable class only exists to define the String constants needed
     * to describe the moving pieces of the table definition.
     *
     */
    public static final class JournalEntryTable {
        public static final String NAME ="journalEntries";

        // With the below defined you are able to refer to the column named "title" in a Java-safe way:
        // JournalEntryTable.Cols.TITLE. This makes it much safer to update the program if you ever need
        // to change the name of that column or add additional data to the table.
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String SLEEPDATEANDTIME = "sleepDateAndTime";
            public static final String WAKEDATEANDTIME = "wakeDateAndTime";
            // public static final String MOTIONDATAPATH = "motionDataPath"; // not needed
            public static final String SOUNDDATAPATH = "soundDataPath";   // this id okay
            public static final String NOTES = "notes";
        }
    }
}
