package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Adapters.NoteAdapter;
import com.example.cru3lgenius.studenttoolkit.Adapters.NoteAdapterHashMap;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by denis on 1/23/17.
 */

public class Note_Utilities  {
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final static Gson gson = new Gson();
    static final String NOTE_PREFERENCES = "notePreferences";  // The key to Load all sharedPreferences related to Notes
    final static String NOTES_HASHMAP = "notesHashMap";


    public static void saveNote(Context context, Note note){

        databaseReference.child("notes").child(note.getId()).setValue(note);

        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_HASHMAP,"default");
        HashMap<String,Note> allNotes = new HashMap<String,Note>();
        if(!jsonAllNotes.equals("default")){
            allNotes = gson.fromJson(jsonAllNotes,new TypeToken<HashMap<String,Note>>() {}.getType());
        }

        allNotes.put(note.getId(),note);
        jsonAllNotes = gson.toJson(allNotes);
        prefsEditor.putString(NOTES_HASHMAP,jsonAllNotes);
        String jsonNote = gson.toJson(note);
        prefsEditor.putString(note.getId(),jsonNote);

        prefsEditor.commit();
    }
    public static HashMap<String,Note> loadNotesLocally(Context context){
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_HASHMAP,"default");
        HashMap<String,Note> allNotes = new HashMap<String,Note>();
        if(!jsonAllNotes.equals("default")){
            System.out.println(jsonAllNotes);
            allNotes = gson.fromJson(jsonAllNotes,new TypeToken<HashMap<String,Note>>(){}.getType());
        }
        /*
        ArrayList<Note> allNotes = new ArrayList<Note>();
        for(String eachId:allNotesId){
            String jsonNote = (prefs.getString(eachId,"default"));
            Note note = gson.fromJson(jsonNote,Note.class);
            allNotes.add(note);
        }
        */

        return allNotes;
    }

    public static void deleteNote(Context context,String noteId){
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_HASHMAP,"default");
        HashMap<String,Note> allNotes = new HashMap<String,Note>();

        if(!jsonAllNotes.equals("default")){
            allNotes = gson.fromJson(jsonAllNotes,new TypeToken<HashMap<String,Note>>(){}.getType());
            allNotes.remove(noteId);
            jsonAllNotes = gson.toJson(allNotes);
            prefsEditor.putString(NOTES_HASHMAP,jsonAllNotes);
            prefsEditor.remove(noteId);
            prefsEditor.commit();
        }
    }

    public static void loadNotesFirebase(final NoteAdapterHashMap adapter , final ProgressDialog dialog, Context context, final HashMap<String,Note> allNotes){
        if(dialog!=null){
            dialog.show();
        }
        databaseReference.child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("KOLKO CHESTO VLIZAM TOKA");
                int counter = 0;
                for(DataSnapshot each: dataSnapshot.getChildren()) {

                    String title = (String) each.child("mTitle").getValue();
                    String content = (String) each.child("mContent").getValue();
                    long dateTime = (long) each.child("mDateTime").getValue();
                    String id = (String) each.child("id").getValue();
                    Note temp = new Note(title, dateTime, content, id);
                    allNotes.put(temp.getId(),temp);
                    System.out.println("Notify !!!!!!!!!!!!!!!!!");
                    adapter.updateAdapter(allNotes);
                    dialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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

}
