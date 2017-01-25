package com.example.cru3lgenius.studenttoolkit.Models;

import android.support.design.internal.ParcelableSparseArray;

import java.io.Serializable;

/**
 * Created by Denis on 12/30/16.
 */

public class Flashcard  implements Serializable{
    private String Id;
    private String question;
    private String answer;
    private String flashcardName;

    public Flashcard(String id, String question, String answer, String flashcardName) {
        this.Id = id;
        this.question = question;
        this.answer = answer;
        this.flashcardName = flashcardName;
    }

    public String getAnswer(){
        return this.answer;
    };
    public void setAnswer(String answer){
        this.answer = answer;
    }
    public void setQuestion(String question){
        this.question = question;
    }
    public void setFlashcardName(String flashcardName){
        this.flashcardName = flashcardName;
    }
    public String getFlashcardName(){
        return this.flashcardName;
    }
    public String getId(){
        return this.Id;
    }
    public void setId(String Id){
        this.Id = Id;

    }
    public String getQuestion(){
        return this.question;
    }
}
