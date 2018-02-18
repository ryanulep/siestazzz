package com.ucr.buzuka.siestazzz;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/18/18.
 */

public class JournalEntry {
    private UUID id;
    private String mTitle;
    private Date mDate;

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = mDate;
    }
}
