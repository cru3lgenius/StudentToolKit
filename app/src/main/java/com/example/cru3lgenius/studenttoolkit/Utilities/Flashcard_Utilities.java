package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.health.ServiceHealthStats;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Adapters.FlashcardsAdapterHashMap;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by denis on 1/25/17.
 */

public class Flashcard_Utilities {

    public static final String FLASHCARD_PREFERENCES = "flashcardPrefs"; // loads the sharedPreferences
    static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    static SharedPreferences prefs;
    static SharedPreferences.Editor prefsEdit;
    public static final String MY_FLASHCARDS_HASHMAP = "flashcardsHashMap"; // getString returns the json representations of the arrList
    static final Gson gson = new Gson();

    public static void saveFlashcard(Context context,Flashcard card){
        database.child("flashcards").push().setValue(card);
        prefs = context.getSharedPreferences(FLASHCARD_PREFERENCES,Context.MODE_PRIVATE);
        prefsEdit = prefs.edit();
        String jsonAllCards = prefs.getString(MY_FLASHCARDS_HASHMAP,"default");
        HashMap<String,Flashcard> allCards = new HashMap<String,Flashcard>();
        if(!jsonAllCards.equals("default")){
            allCards = gson.fromJson(jsonAllCards, new TypeToken<HashMap<String,Flashcard>>() {}.getType());
        }
        allCards.put(card.getId(),card);
        jsonAllCards = gson.toJson(allCards);
        prefsEdit.putString(MY_FLASHCARDS_HASHMAP,jsonAllCards);
        prefsEdit.commit();
        Toast.makeText(context,"Your Flashcard was saved successfully!",Toast.LENGTH_SHORT).show();
    }

    public static HashMap<String,Flashcard> loadFlashcardsLocally(Context context){

        prefs = context.getSharedPreferences(FLASHCARD_PREFERENCES,Context.MODE_PRIVATE);
        prefsEdit = prefs.edit();
        HashMap<String,Flashcard> allCards = new HashMap<String,Flashcard>();;
        String jsonAllCards = prefs.getString(MY_FLASHCARDS_HASHMAP,"default");
        if(!jsonAllCards.equals("default")){
            allCards = gson.fromJson(jsonAllCards, new TypeToken<HashMap<String,Flashcard>>() {}.getType());
            return allCards;
        }
        return allCards;
    }

    //TODO: loadFlashcards from firebase

    public static void deleteFlashcards(ArrayList<Flashcard> cards, Context context, FlashcardsAdapterHashMap adapter){
        prefs = context.getSharedPreferences(FLASHCARD_PREFERENCES,Context.MODE_PRIVATE);
        prefsEdit = prefs.edit();
        HashMap<String,Flashcard> allCards = new HashMap<String,Flashcard>();;
        String jsonAllCards = prefs.getString(MY_FLASHCARDS_HASHMAP,"default");
        if(!jsonAllCards.equals("default")){
            allCards = gson.fromJson(jsonAllCards, new TypeToken<HashMap<String,Flashcard>>() {}.getType());
        }
        for(Flashcard each:cards){
            if(allCards.containsKey(each.getId())){
                allCards.remove(each.getId());
            }
        }
        jsonAllCards = gson.toJson(allCards);
        prefsEdit.putString(MY_FLASHCARDS_HASHMAP,jsonAllCards);
        prefsEdit.commit();
        adapter.updateAdapter(allCards);
    }
}
