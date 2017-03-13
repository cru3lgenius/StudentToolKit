package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Adapters.NoteAdapter;
import com.example.cru3lgenius.studenttoolkit.Adapters.NoteAdapterHashMap;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.TabFragments.Notes_Fragment;
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
    final static NoteAdapterHashMap adapter = Notes_Fragment.getNoteAdapter();



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

    public static void deleteNoteLocally(Context context,String noteId){
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
    public static void deleteNoteFirebase(String noteId) {
        databaseReference.child("notes").child(noteId).removeValue();
        HashMap<String,Note> allNotes = TabsActivity.getAllNotes();
        allNotes.remove(noteId);
        adapter.updateAdapter(allNotes);

    }
    public static HashMap<String,Note> loadNotesLocally(Context context){
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_HASHMAP,"default");
        HashMap<String,Note> allNotes = new HashMap<String,Note>();
        if(!jsonAllNotes.equals("default")){
            //System.out.println(jsonAllNotes);
            allNotes = gson.fromJson(jsonAllNotes,new TypeToken<HashMap<String,Note>>(){}.getType());
        }

        //System.out.println("KFO PRAIM TUKA " + allNotes.keySet());
        return allNotes;
    }


    public static void loadNotesFirebase( final ProgressDialog dialog, Context context, final HashMap<String,Note> allNotes){
        if(dialog!=null){
            dialog.show();
        }

        databaseReference.child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReference.child("notes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String id = (String) dataSnapshot.child("id").getValue();
                String title = (String) dataSnapshot.child("mTitle").getValue();
                String content = (String) dataSnapshot.child("mContent").getValue();
                long date = (long) dataSnapshot.child("mDateTime").getValue();
                Note changedNote = new Note(title,date,content,id);
                allNotes.put(id,changedNote);
                adapter.updateAdapter(allNotes);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                String id = (String) dataSnapshot.child("id").getValue();
                String title = (String) dataSnapshot.child("mTitle").getValue();
                String content = (String) dataSnapshot.child("mContent").getValue();
                long date = (long) dataSnapshot.child("mDateTime").getValue();
                Note changedNote = new Note(title,date,content,id);
                allNotes.put(id,changedNote);
                adapter.updateAdapter(allNotes);
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
