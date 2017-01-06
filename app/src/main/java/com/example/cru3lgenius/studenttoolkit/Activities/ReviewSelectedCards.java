package com.example.cru3lgenius.studenttoolkit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;

public class ReviewSelectedCards extends AppCompatActivity {

    TextView question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_selected_cards);
        question = (TextView)findViewById(R.id.tvQuestionField);
        Bundle bundle = getIntent().getExtras();
        ArrayList<Flashcard>flashcardsToReview = (ArrayList<Flashcard>)bundle.get("flashcardsToReview");
        question.setText(flashcardsToReview.get(0).getQuestion());
        for(Flashcard each:flashcardsToReview){
            System.out.println(each.getFlashcardName());
        }
    }
}
