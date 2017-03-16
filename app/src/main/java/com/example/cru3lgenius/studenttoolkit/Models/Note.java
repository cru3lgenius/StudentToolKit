package com.example.cru3lgenius.studenttoolkit.Models;

import android.content.Context;
import java.text.SimpleDateFormat;

import java.util.TimeZone;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by denis on 1/22/17.
 */

public class Note implements Serializable {

    private String mTitle;
    private long mDateTime;
    private String mContent;
    private String Id;

    public Note(String mTitle, long mDateTime, String mContent,String Id) {
        this.mTitle = mTitle;
        this.mDateTime = mDateTime;
        this.mContent = mContent;
        this.Id = Id;
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
    public void setId(String Id){
        this.Id = Id;
    }
    public String getId(){return this.Id;}
    public String getmContent() {
        return mContent;
    }
    public String getDateFormatted(Context context){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(new Date(mDateTime));

    }
}
