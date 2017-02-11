package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by denis on 1/23/17.
 */

public class Note_Utilities  {
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final static Gson gson = new Gson();
    static final String NOTE_PREFERENCES = "notePreferences";  // The key to Load all sharedPreferences related to Notes
    final static String NOTES_ARRAYLIST = "notesArrayList";


    public static void saveNote(Context context, Note note){

        databaseReference.child("notes").child(note.getId()).setValue(note);

        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotesId = prefs.getString(NOTES_ARRAYLIST,"default");
        ArrayList<String> allNotesId = new ArrayList<String>();
        if(!jsonAllNotesId.equals("default")){
            allNotesId = gson.fromJson(jsonAllNotesId,new TypeToken<ArrayList<String>>() {}.getType());
        }
        if(allNotesId.contains(note.getId())) {
            allNotesId.remove(note.getId());
        }
        allNotesId.add(note.getId());
        jsonAllNotesId = gson.toJson(allNotesId);
        prefsEditor.putString(NOTES_ARRAYLIST,jsonAllNotesId);
        String jsonNote = gson.toJson(note);
        prefsEditor.putString(note.getId(),jsonNote);

        prefsEditor.commit();
    }
    public static ArrayList<Note> loadNotesLocally(Context context){
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_ARRAYLIST,"default");
        ArrayList<String> allNotesId = new ArrayList<String>();
        if(!jsonAllNotes.equals("default")){
            System.out.println(jsonAllNotes);
            allNotesId = gson.fromJson(jsonAllNotes,new TypeToken<ArrayList<String>>(){}.getType());
        }
        ArrayList<Note> allNotes = new ArrayList<Note>();
        for(String eachId:allNotesId){
            String jsonNote = (prefs.getString(eachId,"default"));
            Note note = gson.fromJson(jsonNote,Note.class);
            allNotes.add(note);
        }
        return allNotes;
    }

    public static void deleteNote(Context context,String noteId){
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotesId = prefs.getString(NOTES_ARRAYLIST,"default");
        ArrayList<String> allNotesId = new ArrayList<String>();

        if(!jsonAllNotesId.equals("default")){
            allNotesId = gson.fromJson(jsonAllNotesId,new TypeToken<ArrayList<String>>(){}.getType());
            allNotesId.remove(noteId);
            jsonAllNotesId = gson.toJson(allNotesId);
            prefsEditor.putString(NOTES_ARRAYLIST,jsonAllNotesId);
            prefsEditor.remove(noteId);
            prefsEditor.commit();
        }
    }

    public static ArrayList<Note> loadNotesFirebase(){
        final ArrayList<Note> allNotes  = new ArrayList<Note>();
        databaseReference.child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int counter = 0;
                for(DataSnapshot each: dataSnapshot.getChildren()){

                    String title = (String) each.child("mTitle").getValue();
                    String content = (String)each.child("mContent").getValue();
                    long dateTime = (long)each.child("mDateTime").getValue();
                    String id = (String)each.child("id").getValue();
                    Note temp = new Note(title,dateTime,content,id);
                    System.out.println(temp);
                    allNotes.add(temp);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return allNotes;
    }

}
