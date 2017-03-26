package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.cru3lgenius.studenttoolkit.Adapters.NoteAdapterHashMap;
import com.example.cru3lgenius.studenttoolkit.Main.Session;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.TabFragments.Notes_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

/**
 * Created by denis on 1/23/17.
 */

public class Note_Utilities  {
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    final static Gson gson = new Gson();
    static final String NOTE_PREFERENCES = "notePreferences";  // The key to Load all sharedPreferences related to Notes
    final static String NOTES_HASHMAP = "notesHashMap";
    final static NoteAdapterHashMap adapter = Notes_Fragment.getNoteAdapter();



    public static void saveNote(Context context, Note note){

        /* Save the note in firebase */
        databaseReference.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("notes").child(note.getId()).setValue(note);

        /* Local Save
        // Save the note locally using Shared Preferences
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_HASHMAP,"default");
        HashMap<String,Note> allNotes = new HashMap<String,Note>();
        if(!jsonAllNotes.equals("default")){
            // Translate JSon to HashMap
            allNotes = gson.fromJson(jsonAllNotes,new TypeToken<HashMap<String,Note>>() {}.getType());
        }

        allNotes.put(note.getId(),note);

        //Translate HashMap to JSon and save the string as Shared Preferences
        jsonAllNotes = gson.toJson(allNotes);
        prefsEditor.putString(NOTES_HASHMAP,jsonAllNotes);
        String jsonNote = gson.toJson(note);
        prefsEditor.putString(note.getId(),jsonNote);

        prefsEditor.commit();
        */

    }


    /* Blocks dedicated to storing and retrieving data from Firebase */

    public static void deleteNoteFirebase(String noteId) {
        databaseReference.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("notes").child(noteId).removeValue();
        HashMap<String,Note> allNotes = TabsActivity.getAllNotes();
        allNotes.remove(noteId);
        adapter.updateAdapter(allNotes);

    }
    public static void loadNotesFirebase( final ProgressDialog dialog, final Context context, final HashMap<String,Note> allNotes){
        if(dialog!=null){
            dialog.show();
        }

        databaseReference.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReference.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("notes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                /* Retrieving data from Firebase */
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
                TabsActivity.getAllNotes().remove(dataSnapshot.getKey());
                adapter.updateAdapter(TabsActivity.getAllNotes());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /* Blocks that work when notes are stored locally and not in firebase */
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

    public static HashMap<String,Note> loadNotesLocally(Context context){
        SharedPreferences prefs = context.getSharedPreferences(NOTE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String jsonAllNotes = prefs.getString(NOTES_HASHMAP,"default");
        HashMap<String,Note> allNotes = new HashMap<String,Note>();
        if(!jsonAllNotes.equals("default")){
            allNotes = gson.fromJson(jsonAllNotes,new TypeToken<HashMap<String,Note>>(){}.getType());
        }
        return allNotes;
    }




}
