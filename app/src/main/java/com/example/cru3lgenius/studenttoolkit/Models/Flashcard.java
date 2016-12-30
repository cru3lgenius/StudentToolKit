package com.example.cru3lgenius.studenttoolkit.Models;

import java.io.Serializable;

/**
 * Created by Denis on 12/30/16.
 */

public class Flashcard implements Serializable{
    private String question;
    private String answer;

    public String getAnswer(){
        return this.answer;
    };
    public void setAnswer(String answer){
        this.answer = answer;
    }
    public void setQuestion(String question){
        this.question = question;
    }
}
