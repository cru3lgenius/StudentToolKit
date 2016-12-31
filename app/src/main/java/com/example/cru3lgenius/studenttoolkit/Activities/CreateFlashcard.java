package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.google.gson.Gson;

import java.io.Serializable;

public class CreateFlashcard extends AppCompatActivity {
    Button saveFlashcardButton;
    public static final String FLASHCARD_PREFERENCES = "flashcardPrefs";
    SharedPreferences prefs;
    SharedPreferences.Editor prefEdit;
    final Gson gson = new Gson();
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

        prefs = getSharedPreferences(FLASHCARD_PREFERENCES,MODE_PRIVATE);
        prefEdit = prefs.edit();

    }

    //TODO: Creates and saves a new flashcard
    private void saveFlashcard(){
        String quest = "KFO STAVA";
        String anwer = "NISHTO";
        Flashcard card = new Flashcard();
        card.setAnswer(anwer);
        card.setQuestion(quest);
        System.out.println("ORIGINAL OBJECT :" + card);
        String jsonCard = gson.toJson(card);
        System.out.println("AFTER GSON :" + jsonCard);
        prefEdit.putString("idFlashcard",jsonCard);
        prefEdit.commit();
    }
}
