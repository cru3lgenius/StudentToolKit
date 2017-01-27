package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by denis on 1/23/17.
 */

public class Note_Utilities  {

    final static Gson gson = new Gson();
    static final String NOTE_PREFERENCES = "notePreferences";  // The key to Load all sharedPreferences related to Notes
    final static String NOTES_ARRAYLIST = "notesArrayList";


    public static void saveNote(Context context, Note note){
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_ARRAYLIST,"default");
        ArrayList<Note> allNotes = new ArrayList<Note>();
        if(!jsonAllNotes.equals("default")){
            allNotes = gson.fromJson(jsonAllNotes,new TypeToken<ArrayList<Note>>() {}.getType());
        }
        allNotes.add(note);
        jsonAllNotes = gson.toJson(allNotes);
        prefsEditor.putString(NOTES_ARRAYLIST,jsonAllNotes);
        prefsEditor.commit();
    }
    public static ArrayList<Note> loadNotes(Context context){
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_ARRAYLIST,"default");
        ArrayList<Note> allNotes = new ArrayList<Note>();
        if(jsonAllNotes.equals("default")){
            Toast.makeText(context,"You have no notes yet!",Toast.LENGTH_SHORT).show();
        }else{
            allNotes = gson.fromJson(jsonAllNotes,new TypeToken<ArrayList<Note>>(){}.getType());
        }
        return allNotes;
    }

    public static void deleteNote(Context context,int position){
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_ARRAYLIST,"default");
        ArrayList<Note> allNotes = new ArrayList<Note>();
        if(!jsonAllNotes.equals("default")){
            allNotes = gson.fromJson(jsonAllNotes,new TypeToken<ArrayList<Note>>(){}.getType());
            allNotes.remove(position);
            jsonAllNotes = gson.toJson(allNotes);
            prefsEditor.putString(NOTES_ARRAYLIST,jsonAllNotes);
            prefsEditor.commit();
        }
    }

}
