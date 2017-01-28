package com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.Flashcard_Utilities;

import java.util.UUID;

public class CreateFlashcard extends AppCompatActivity {
    Button saveFlashcardButton;

    EditText answer,question,flashCardName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);
        saveFlashcardButton = (Button)findViewById(R.id.btnSaveFlashcard);
        answer = (EditText) findViewById(R.id.etAnswer);
        question = (EditText) findViewById(R.id.etQuestion);
        flashCardName = (EditText) findViewById(R.id.etFlashcardName);
        saveFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFlashcard();
                answer.setText("");
                question.setText("");
                flashCardName.setText("");
            }
        });


    }

    private void saveFlashcard(){
        /* Generates attributes for the card */
        String flashcardId = UUID.randomUUID().toString();
        String flashCardNameStr = flashCardName.getText().toString();
        String answerStr = answer.getText().toString();
        String questStr = question.getText().toString();

        /* Handle the case when the user does not give appropriate information */
        if(flashCardNameStr.equals("") || answerStr.equals("") || questStr.equals("")){
            Toast.makeText(this.getApplicationContext(),"Empty fields are not allowed!",Toast.LENGTH_SHORT).show();
            return;
        }

        /* Initialize the card with the above attributes */
        Flashcard card = new Flashcard(flashcardId, questStr, answerStr, flashCardNameStr);
        Flashcard_Utilities.saveFlashcard(getApplicationContext(),card);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), TabsActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
