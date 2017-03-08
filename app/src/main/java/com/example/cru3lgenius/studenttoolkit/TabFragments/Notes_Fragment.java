package com.example.cru3lgenius.studenttoolkit.TabFragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.provider.ContactsContract;
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
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Activities.Note_Activities.CreateNote;
import com.example.cru3lgenius.studenttoolkit.Adapters.NoteAdapter;
import com.example.cru3lgenius.studenttoolkit.Adapters.NoteAdapterHashMap;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.Note_Utilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    //private NoteAdapter noteAdapter;
    private static NoteAdapterHashMap  noteAdapter;
    private DatabaseReference mDatabaseReference;
    final private HashMap<String,Note> allNotes = (HashMap<String,Note>) TabsActivity.getAllNotes();
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_notes, container, false);
        displayNotes = (ListView) viewRoot.findViewById(R.id.lvNotes);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        setHasOptionsMenu(true);
        //ArrayList<Note> allNotes  = Note_Utilities.loadNotesLocally(getContext());
        //final ArrayList<Note> allNotesList = new ArrayList<Note>(allNotes.values());
        //noteAdapter = new NoteAdapter(getContext(),R.layout.item_note_listview,allNotesList);
        noteAdapter = new NoteAdapterHashMap(allNotes);
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading Notes...");
        progressDialog.setTitle("Notes");

        if(allNotes.isEmpty()){
            Note_Utilities.loadNotesFirebase(progressDialog,getContext(),allNotes);

            /* Version with  Loading notes locally

            HashMap<String,Note> temp = Note_Utilities.loadNotesLocally(getContext());
            for(Map.Entry<String,Note> each: temp.entrySet()){
                allNotes.put(each.getKey(),each.getValue());
            }
            noteAdapter.updateAdapter(allNotes);

            */
        }

        displayNotes.setAdapter(noteAdapter);
        displayNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

