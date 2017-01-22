package com.example.cru3lgenius.studenttoolkit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.cru3lgenius.studenttoolkit.R;

public class CreateNote extends AppCompatActivity {
    EditText noteTitle,noteContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        noteTitle = (EditText)findViewById(R.id.etNoteTitle);
        noteContent = (EditText)findViewById(R.id.etNoteContent);
    }
}
