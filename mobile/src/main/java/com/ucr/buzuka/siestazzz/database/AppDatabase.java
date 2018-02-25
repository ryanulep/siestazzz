package com.ucr.buzuka.siestazzz.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ucr.buzuka.siestazzz.model.SensorReadout;

/**
 * Created by jakex on 2/25/2018.
 */
@Database(entities = {SensorReadout.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
/** Create a private instance of the database*/
    private static AppDatabase instance;

/** Using context to create a database instance and pass the reference*/
    public static AppDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
/** Destroy database instance to save memory */
    public static void destroyInstance(){
        instance = null;
    }
}
