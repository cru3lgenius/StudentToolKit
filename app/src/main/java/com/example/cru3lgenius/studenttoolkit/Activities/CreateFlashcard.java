package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cru3lgenius.studenttoolkit.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.UUID;

public class CreateFlashcard extends AppCompatActivity {
    Button saveFlashcardButton;

    SharedPreferences prefs;
    SharedPreferences.Editor prefEdit;
    final Gson gson = new Gson();
    EditText answer,question,flashCardName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            }
        });

        prefs = getSharedPreferences(TabsActivity.FLASHCARD_PREFERENCES,MODE_PRIVATE);
        prefEdit = prefs.edit();

    }

    //TODO: Creates and saves a new flashcard
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
        Flashcard card = new Flashcard();
        card.setFlashcardName(flashCardNameStr);
        card.setAnswer(answerStr);
        card.setQuestion(questStr);
        card.setId(flashcardId);

       // String jsonCard = gson.toJson(card);
        TabsActivity.myFlashcards.add(card);
        String jsonFlashcardsList = gson.toJson(TabsActivity.myFlashcards);
        prefEdit.putInt(TabsActivity.FIRST_LOGIN,1);
        prefEdit.putString(TabsActivity.MY_FLASHCARDS_ARRAYLIST,jsonFlashcardsList);
        //prefEdit.putString(flashCardNameStr,jsonCard);
        prefEdit.commit();
        Toast.makeText(this.getApplicationContext(),"Your flashcard was saved successfully!",Toast.LENGTH_SHORT).show();
    }
}
