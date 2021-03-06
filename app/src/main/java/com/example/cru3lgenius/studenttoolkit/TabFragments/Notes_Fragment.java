package com.example.cru3lgenius.studenttoolkit.TabFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cru3lgenius.studenttoolkit.Activities.Note_Activities.CreateNote;
import com.example.cru3lgenius.studenttoolkit.Adapters.NoteAdapterHashMap;
import com.example.cru3lgenius.studenttoolkit.Main.Session;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.Note_Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Created by denis on 1/22/17.
 */

public class Notes_Fragment extends Fragment {
    View viewRoot;
    private ProgressDialog progressDialog;
    private ListView displayNotes;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabaseReference;
    final static private HashMap<String,Note> allNotes = (HashMap<String,Note>) TabsActivity.getAllNotes();
    public static NoteAdapterHashMap noteAdapter = new NoteAdapterHashMap(allNotes);

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_notes, container, false);

        /* Initialize widgets */
        displayNotes = (ListView) viewRoot.findViewById(R.id.lvNotes);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        setHasOptionsMenu(true);
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading Notes...");
        progressDialog.setTitle("Notes");
        displayNotes.setAdapter(noteAdapter);

        /* In case there are no notes load from firebase */
        if(allNotes.isEmpty()){
            Note_Utilities.loadNotesFirebase(progressDialog,getContext(),allNotes);
        }

        displayNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* Start acitivity to edit/create new note */
                Intent i = new Intent(getActivity(), CreateNote.class);
                Map.Entry<String,Note> noteEntry = (Map.Entry<String,Note>)  displayNotes.getItemAtPosition(position);
                Note note = noteEntry.getValue();
                i.putExtra("noteToDisplay",note).putExtra("noteId",note.getId());
                startActivity(i);

            }
        });

        return viewRoot;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_action_CreateNewNote:
                Intent i = new Intent(getContext(), CreateNote.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_note,menu);
    }
    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    public static NoteAdapterHashMap getNoteAdapter() {
        return noteAdapter;
    }
}

