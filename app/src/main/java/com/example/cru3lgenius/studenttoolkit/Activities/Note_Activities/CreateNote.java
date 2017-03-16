package com.example.cru3lgenius.studenttoolkit.Activities.Note_Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Utilities.Note_Utilities;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CreateNote extends AppCompatActivity {
    EditText noteTitle,noteContent;
    private Note currNote = null;
    private String id;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        noteTitle = (EditText)findViewById(R.id.etNoteTitle);
        noteContent = (EditText)findViewById(R.id.etNoteContent);
        ref = FirebaseDatabase.getInstance().getReference();
        Bundle b = getIntent().getExtras();
        if(getIntent().hasExtra("noteToDisplay")&&getIntent().hasExtra("noteId")){
            currNote = (Note) b.get("noteToDisplay");
            noteContent.setText(currNote.getmContent());
            noteTitle.setText(currNote.getmTitle());
            id = b.getString("noteId");
            getIntent().removeExtra("noteToDisplay");
            getIntent().removeExtra("noteId");
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_save_note,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_action_SaveNote:
                saveNote();
                break;
            case R.id.menu_action_deleteNote:
                deleteNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {

        /* Checks you have opened an existing note */
        if(currNote!=null){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("delete")
                    .setMessage("Are you sure you want to delete this note?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Note_Utilities.deleteNoteLocally(getApplicationContext(),Id);
                            Note_Utilities.deleteNoteFirebase(currNote.getId());
                            startActivity(new Intent(getApplicationContext(), TabsActivity.class));
                            Toast.makeText(getApplicationContext(),"Note was deleted",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No",null);
            dialog.show();


        }else{
            finish();
        }

    }

    public void saveNote(){
        /* If you are editing a note */
        if(currNote != null){
            currNote.setmTitle(noteTitle.getText().toString());
            currNote.setmContent(noteContent.getText().toString());
            currNote.setmDateTime(System.currentTimeMillis());
            Note_Utilities.saveNote(getApplicationContext(),currNote);
            }else{
            /* If you are creating new Note */
            String Id = UUID.randomUUID().toString();
            Note note = new Note(noteTitle.getText().toString(), System.currentTimeMillis(), noteContent.getText().toString(), Id);
            Note_Utilities.saveNote(getApplicationContext(), note);
        }
        Intent i = new Intent(getApplicationContext(), TabsActivity.class);
        startActivity(i);
        finish();

    }
}
