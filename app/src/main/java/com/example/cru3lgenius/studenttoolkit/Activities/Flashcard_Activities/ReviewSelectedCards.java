package com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ReviewSelectedCards extends AppCompatActivity {

    private TextView question;
    private Button nextCard;
    private int cardCounter;
    private EditText answer;
    private RelativeLayout layout;
    private HashMap<Flashcard,String>answersMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_selected_cards);
        layout = (RelativeLayout) findViewById(R.id.activity_review_selected_cards);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
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

                /* These changes will happen only if there are still cards to be added */
                if(cardCounter!=flashcardsToReview.size()) {

                    answersMap.put(flashcardsToReview.get(cardCounter), answer.getText().toString());
                    cardCounter++;
                }
                /* Handles the case when this was the last card */
                if(cardCounter==flashcardsToReview.size()){
                    /* Goes to the activity when the check of your results will happen */
                    Intent i = new Intent(getApplicationContext(),CheckAnswers.class);
                    i.putExtra("answersMap",answersMap);
                    startActivity(i);
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
        /* Checks if there is only one card */
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

    /* Hides the keyboard by clicking somewhere */
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
