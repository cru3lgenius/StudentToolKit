package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Utilities.Note_Utilities;

import TabFragments.Notes_Fragment;

public class CreateNote extends AppCompatActivity {
    EditText noteTitle,noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        noteTitle = (EditText)findViewById(R.id.etNoteTitle);
        noteContent = (EditText)findViewById(R.id.etNoteContent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_save_note,menu);
        getMenuInflater().inflate(R.menu.menu_update_note,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_action_SaveNote:
                saveNote(false);
                break;
            case R.id.menu_action_updateNote:
                saveNote(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveNote(boolean toUpdate){
        if(toUpdate){
            //TODO: HERE YOU HANDLE THE UPDATE OF A NOTE
        }else{
        Note note = new Note(noteTitle.getText().toString(),System.currentTimeMillis(),noteContent.getText().toString());
        Note_Utilities.saveNote(getApplicationContext(),note);
        Intent i  = new Intent(getApplicationContext(), TabsActivity.class);
        startActivity(i);
        finish();
        }
    }
}
