package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.health.ServiceHealthStats;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by denis on 1/25/17.
 */

public class Flashcard_Utilities {

    public static final String FLASHCARD_PREFERENCES = "flashcardPrefs"; // loads the sharedPreferences
    static SharedPreferences prefs;
    static SharedPreferences.Editor prefsEdit;
    public static final String MY_FLASHCARDS_ARRAYLIST = "flashcardsArrayList"; // getString returns the json representations of the arrList
    static final Gson gson = new Gson();

    public static void saveFlashcard(Context context,Flashcard card){

        prefs = context.getSharedPreferences(FLASHCARD_PREFERENCES,Context.MODE_PRIVATE);
        prefsEdit = prefs.edit();
        String jsonAllCards = prefs.getString(MY_FLASHCARDS_ARRAYLIST,"default");
        ArrayList<Flashcard> allCards = new ArrayList<Flashcard>();
        if(!jsonAllCards.equals("default")){
            allCards = gson.fromJson(jsonAllCards, new TypeToken<ArrayList<Flashcard>>() {}.getType());
        }
        allCards.add(card);
        jsonAllCards = gson.toJson(allCards);
        prefsEdit.putString(MY_FLASHCARDS_ARRAYLIST,jsonAllCards);
        prefsEdit.commit();
        Toast.makeText(context,"Your Flashcard was saved successfully!",Toast.LENGTH_SHORT).show();
    }

    public static ArrayList<Flashcard> loadFlashcards(Context context){

        prefs = context.getSharedPreferences(FLASHCARD_PREFERENCES,Context.MODE_PRIVATE);
        prefsEdit = prefs.edit();
        ArrayList<Flashcard> allCards;
        String jsonAllCards = prefs.getString(MY_FLASHCARDS_ARRAYLIST,"default");
        if(!jsonAllCards.equals("default")){
            allCards = gson.fromJson(jsonAllCards, new TypeToken<ArrayList<Flashcard>>() {}.getType());
            return allCards;
        }
        return new ArrayList<Flashcard>();
    }
}
