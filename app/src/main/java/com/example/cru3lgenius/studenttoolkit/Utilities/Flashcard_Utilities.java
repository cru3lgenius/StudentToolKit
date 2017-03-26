package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.health.ServiceHealthStats;
import android.os.health.SystemHealthManager;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Adapters.FlashcardsAdapterHashMap;
import com.example.cru3lgenius.studenttoolkit.Main.Session;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.TabFragments.Flashcards_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    private static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private static SharedPreferences prefs;
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static SharedPreferences.Editor prefsEdit;
    public static final String MY_FLASHCARDS_HASHMAP = "flashcardsHashMap"; // getString returns the json representations of the arrList
    static final Gson gson = new Gson();
    private static FlashcardsAdapterHashMap adapter = Flashcards_Fragment.getAdapter();



    public static void saveFlashcard(Context context,Flashcard card){
        /* Store cards in firebase*/
        database.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("flashcards").child(card.getId()).setValue(card);

        /* Store Cards Locally */
        /* --------------------*
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
        */
    }




    /* Blocks that handle flashcards in Firebase */
    public static void deleteFlashcardsFirebase(ArrayList<Flashcard> cards){
        HashMap<String,Flashcard> allFlashcards = TabsActivity.getAllCards();
        for(Flashcard each: cards){
            database.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("flashcards").child(each.getId()).removeValue();
            allFlashcards.remove(each.getId());

        }

    }
    public static void loadFlashcardsFirebase(final HashMap<String,Flashcard> cards){
        database.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("flashcards").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                /* Retrieving data from Firebase */
                String id = (String) dataSnapshot.child("id").getValue();
                String question = (String) dataSnapshot.child("question").getValue();
                String answer = (String) dataSnapshot.child("answer").getValue();
                String flashcardName = (String)dataSnapshot.child("flashcardName").getValue();
                long date = (long) dataSnapshot.child("date").getValue();
                Flashcard card = new Flashcard(id,question,answer,flashcardName);
                card.setDate(date);
                cards.put(card.getId(),card);
                adapter.updateAdapter(cards);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /* Block for handling flashcards locally*/

    public static void deleteFlashcardsLocally(ArrayList<Flashcard> cards, Context context, FlashcardsAdapterHashMap adapter){
        prefs = context.getSharedPreferences(FLASHCARD_PREFERENCES,Context.MODE_PRIVATE);
        prefsEdit = prefs.edit();

        /* Overriding the old data */
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

    public static HashMap<String,Flashcard> loadFlashcardsLocally(Context context){
        /* Loading data from the Shared Prefs */
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
}
