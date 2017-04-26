package com.example.cru3lgenius.studenttoolkit.Models;

import android.content.Context;
import android.support.design.internal.ParcelableSparseArray;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Denis on 12/30/16.
 */

public class Flashcard  implements Serializable{
    private String Id;
    private String question;
    private String answer;
    private String flashcardName;
    private Long date;

    public Flashcard(String id, String question, String answer, String flashcardName) {
        this.Id = id;
        this.question = question;
        this.answer = answer;
        this.flashcardName = flashcardName;
        this.date = System.currentTimeMillis();
    }

    /* Setter and Getter methods for Flashcard */
    public String getAnswer(){
        return this.answer;
    };
    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getQuestion(){
        return this.question;
    }
    public void setQuestion(String question){
        this.question = question;
    }

    public String getFlashcardName(){
        return this.flashcardName;
    }
    public void setFlashcardName(String flashcardName){
        this.flashcardName = flashcardName;
    }

    public String getId(){
        return this.Id;
    }
    public void setId(String Id){
        this.Id = Id;
    }


    public String getDateFormatted(Context context){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(new Date(date));

    }
    public void setDate(long date){
        this.date = date;
    }
    public long getDate(){
        return this.date;
    }
}
