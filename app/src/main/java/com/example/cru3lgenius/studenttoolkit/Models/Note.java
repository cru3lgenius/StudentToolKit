package com.example.cru3lgenius.studenttoolkit.Models;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by denis on 1/22/17.
 */

public class Note implements Serializable {

    private String mTitle;
    private long mDateTime;
    private String mContent;

    public Note(String mTitle, long mDateTime, String mContent) {
        this.mTitle = mTitle;
        this.mDateTime = mDateTime;
        this.mContent = mContent;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDateTime(long mDateTime) {
        this.mDateTime = mDateTime;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmTitle() {
        return mTitle;
    }

    public long getmDateTime() {
        return mDateTime;
    }

    public String getmContent() {
        return mContent;
    }
    public String getDataFormatted(Context context){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(new Date(mDateTime));

    }
}
