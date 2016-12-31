package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.google.gson.Gson;

public class ReviewFlashcards extends AppCompatActivity {
    final Gson gson = new Gson();
    SharedPreferences sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_flashcards);
        sharedPrefs = getSharedPreferences("flashcardPrefs",MODE_PRIVATE);
        String a = sharedPrefs.getString("idFlashcard","Default");
        Flashcard restoredCard = gson.fromJson(a,Flashcard.class);
        System.out.println("RESTORED FLASHCARD ANSWER: " + restoredCard.getAnswer());

    }
}
