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
    private EditText noteTitleTextField,noteContentTextField;
    private Note currNote = null;
    private String id;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        /* Adjust the actionbar return button */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        noteTitleTextField = (EditText)findViewById(R.id.etNoteTitle);
        noteContentTextField = (EditText)findViewById(R.id.etNoteContent);
        ref = FirebaseDatabase.getInstance().getReference();


        /* Extract data and checks if there is a loaded note from the previous activity */
        Bundle b = getIntent().getExtras();
        if(getIntent().hasExtra("noteToDisplay")&&getIntent().hasExtra("noteId")){
            currNote = (Note) b.get("noteToDisplay");
            noteContentTextField.setText(currNote.getmContent());
            noteTitleTextField.setText(currNote.getmTitle());
            id = b.getString("noteId");
            getIntent().removeExtra("noteToDisplay");
            getIntent().removeExtra("noteId");
        }

    }

    /* Initializing save button on actionbar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_save_note,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Creating return button in actionbar */
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    /* Handle different icon-actions in the actionbar */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_action_SaveNote:
                if(noteTitleTextField.getText().toString().isEmpty()||noteContentTextField.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"No empty fields are allowed!",Toast.LENGTH_SHORT).show();
                    break;
                }
                saveNote();
                break;
            case R.id.menu_action_deleteNote:
                deleteNote();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void deleteNote() {

        /* Checks you have opened an existing note and reacts accordingly */
        if(currNote!=null){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("delete")
                    .setMessage("Are you sure you want to delete this note?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

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

    private void saveNote(){
        /* If you are editing a note */
        if(currNote != null){
            currNote.setmTitle(noteTitleTextField.getText().toString());
            currNote.setmContent(noteContentTextField.getText().toString());
            currNote.setmDateTime(System.currentTimeMillis());
            Note_Utilities.saveNote(currNote);
            }else{
            /* If you are creating new Note */
            String Id = UUID.randomUUID().toString();
            Note note = new Note(noteTitleTextField.getText().toString(), System.currentTimeMillis(), noteContentTextField.getText().toString(), Id);
            Note_Utilities.saveNote(note);
        }

        finish();

    }

}
