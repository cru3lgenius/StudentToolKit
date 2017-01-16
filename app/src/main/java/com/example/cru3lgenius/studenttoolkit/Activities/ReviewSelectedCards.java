package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ReviewSelectedCards extends AppCompatActivity {

    TextView question;
    Button nextCard;
    private int cardCounter;
    EditText answer;
    HashMap<Flashcard,String>answersMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_selected_cards);
        answersMap = new HashMap<Flashcard,String >();

        answer = (EditText) findViewById(R.id.etAnswerGuess);
        question = (TextView)findViewById(R.id.tvQuestionField);
        nextCard = (Button)findViewById(R.id.btnNextCard);
        cardCounter = 0;
        Bundle bundle = getIntent().getExtras();
        final ArrayList<Flashcard>flashcardsToReview = (ArrayList<Flashcard>)bundle.get("flashcardsToReview");
        nextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cardCounter!=flashcardsToReview.size()) {
                    System.out.println(flashcardsToReview.size());
                    answersMap.put(flashcardsToReview.get(cardCounter), answer.getText().toString());
                    cardCounter++;
                }
                /* Handles the case when this was the last card */
                if(cardCounter==flashcardsToReview.size()){
                    startActivity(new Intent(getApplicationContext(),CheckResult.class));
                    return;

                }
                if(checkIfLastcard(flashcardsToReview)){
                    nextCard.setText("Finish!");
                }
                answer.setText("");
                question.setText(flashcardsToReview.get(cardCounter).getQuestion());
            }
        });
        /* Loads the question from the first card */
        question.setText(flashcardsToReview.get(cardCounter).getQuestion());
        if(checkIfLastcard(flashcardsToReview)){
            nextCard.setText("Finish!");
        }

    }

    boolean checkIfLastcard(ArrayList<Flashcard>arr){
        if(cardCounter==arr.size()-1){
            return true;
        }
        return false;
    }
}
