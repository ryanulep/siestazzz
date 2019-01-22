package com.ucr.buzuka.siestazzz.database;

public class AlarmDbSchema {

  /**
   * An inner class to describe to describe the table. The AlarmTable class only exists to define
   * the String constants needed to describe the moving pieces of the table definition.
   */
  public static final class AlarmTable {

    public static final String NAME = "alarms";

    // With the below defined you are able to refer to the column named "title" in a Java-safe way:
    // AlarmTable.Cols.TITLE. This makes it much safer to update the program if you ever need
    // to change the name of that column or add additional data to the table.
    public static final class Cols {

      public static final String UUID = "uuid";
      public static final String TITLE = "title";
      public static final String TIME = "time";
      public static final String ACTIVE = "active";
      public static final String SMART = "smart";
    }
  }
}
