package com.example.cru3lgenius.studenttoolkit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cru3lgenius.studenttoolkit.R;

import java.io.Serializable;

public class CreateFlashcard extends AppCompatActivity {
    Button saveFlashcardButton;
    EditText answer,question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);
        saveFlashcardButton = (Button)findViewById(R.id.btnSaveFlashcard);

        saveFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    saveFlashcard();
                }
            }
        });
    }

    //TODO: Creates and saves a new flashcard
    private void saveFlashcard(){

    }
}
