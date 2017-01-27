package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Utilities.Note_Utilities;

import TabFragments.Notes_Fragment;

public class CreateNote extends AppCompatActivity {
    EditText noteTitle,noteContent;
    private Note currNote = null;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        noteTitle = (EditText)findViewById(R.id.etNoteTitle);
        noteContent = (EditText)findViewById(R.id.etNoteContent);
        Bundle b = getIntent().getExtras();
        if(getIntent().hasExtra("noteToDisplay")&&getIntent().hasExtra("positionOfNote")){
            currNote = (Note) b.get("noteToDisplay");
            noteContent.setText(currNote.getmContent());
            noteTitle.setText(currNote.getmTitle());
            position = b.getInt("positionOfNote");
            getIntent().removeExtra("noteToDisplay");
            getIntent().removeExtra("positionOfNote");
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
        if(currNote!=null){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("delete")
                    .setMessage("Are you sure you want to delete this note?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Note_Utilities.deleteNote(getApplicationContext(),position);
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
        if(currNote != null){
            Note_Utilities.deleteNote(getApplicationContext(),position);
        }
        Note note = new Note(noteTitle.getText().toString(),System.currentTimeMillis(),noteContent.getText().toString());
        Note_Utilities.saveNote(getApplicationContext(),note);
        Intent i  = new Intent(getApplicationContext(), TabsActivity.class);
        startActivity(i);
        finish();

    }
}
