package com.ucr.buzuka.siestazzz;

import android.content.Context;

/**
 * Created by rick on 2/18/18.
 */

public class Journal {
    private static Journal sJournal;

    public static Journal get(Context context) {
        if (sJournal == null) {
            sJournal = new Journal(context);
        }
        return sJournal;
    }

    private Journal(Context context){

    }
}
