package com.example.cru3lgenius.studenttoolkit.Main;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.Models.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by denis on 3/22/17.
 */

public class Session {
    private static HashMap<String,Flashcard> allCards;
    private static HashMap<String,Note> allNotes;
    private static User currUser;


    public Session(User user){
        this.currUser = user;
        allNotes = new HashMap<String, Note>();
        allCards = new HashMap<String, Flashcard>();
    }

    public static HashMap<String, Flashcard> getAllCards() {
        return allCards;
    }

    public void setAllCards(HashMap<String, Flashcard> allCards) {
        this.allCards = allCards;
    }

    public static HashMap<String, Note> getAllNotes() {
        return allNotes;
    }

    public void setAllNotes(HashMap<String, Note> allNotes) {
        this.allNotes = allNotes;
    }

    public static User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }
}

