package com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CheckAnswers extends AppCompatActivity {

    private Button nextAnswerCheckButton;
    private TextView yourAnswerLabel,correctAnswerLabel,cardNameLabel;
    private RadioGroup radioGroup;
    private RadioButton correctAnswerRadioButton,falseAnswerRadioButton;
    private int counter,correctAnswersCount;
    private RelativeLayout layout;
    private HashMap<Flashcard,Boolean> correctAnswersMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_answers);

        /* Hide keyboard */
        layout = (RelativeLayout) findViewById(R.id.activity_check_answers);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        /* Initialize widgets */
        radioGroup = (RadioGroup) findViewById(R.id.rgCheckAnswerRadioButtons);
        nextAnswerCheckButton = (Button)findViewById(R.id.btnNextAnswerCheck);
        yourAnswerLabel = (TextView) findViewById(R.id.tvYourAnswer);
        correctAnswerLabel = (TextView)findViewById(R.id.tvCorrectAnswer);
        cardNameLabel = (TextView)findViewById(R.id.tvCardNameLabel);
        correctAnswerRadioButton= (RadioButton) findViewById(R.id.rbCorrectAnswer);
        falseAnswerRadioButton = (RadioButton) findViewById(R.id.rbFalseAnswer);
        correctAnswersMap = new HashMap<Flashcard,Boolean>();


        /* Extract data transfered from previous activity */
        Bundle b = getIntent().getExtras();
        final HashMap<Flashcard,String> answersMap = (HashMap<Flashcard,String>)b.get("answersMap");

        /* Put the information on display */
        counter = 0;    // index showing which card is currently displayed
        correctAnswersCount = 0; // Showing how many cards user guessed right
        Set<Flashcard> revisitedCardsSet = answersMap.keySet();
        final ArrayList<Flashcard> allRevisitedCards = new ArrayList<Flashcard>(revisitedCardsSet);
        Flashcard currCard = allRevisitedCards.get(counter);
        cardNameLabel.setText(currCard.getFlashcardName());
        yourAnswerLabel.setText(answersMap.get(currCard));
        correctAnswerLabel.setText(currCard.getAnswer());

        /* If there is only one card to review */
        if(counter==allRevisitedCards.size()){
            nextAnswerCheckButton.setText("Check your results");
        }

        /* Button Action */
        nextAnswerCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Check if user input was correct */
                if(!correctAnswerRadioButton.isChecked()&&!falseAnswerRadioButton.isChecked()){
                    Toast.makeText(getApplicationContext(),"Before you continue you must select if your answer was correct or false!",Toast.LENGTH_SHORT).show();
                    return;
                }

                /* Put in the map whether the answer is correct or not */
                correctAnswersMap.put(allRevisitedCards.get(counter),correctAnswerRadioButton.isChecked());

                /* If you are at the last card */
                if(counter == allRevisitedCards.size()-1){

                    /* Increment the number of correct guessed cards */
                    if(correctAnswerRadioButton.isChecked()){
                        correctAnswersCount++;
                    }

                    /* Finish and show the number of correct answers */
                    Intent i = new Intent(getApplicationContext(),ShowResults.class);
                    i.putExtra("correctAnswersCount",correctAnswersCount);
                    i.putExtra("correctAnswersMap",correctAnswersMap);
                    startActivity(i);
                    return;
                }

                if(counter<allRevisitedCards.size()-1){

                    /* Increment the number of correct guessed cards */
                    if(correctAnswerRadioButton.isChecked()){
                        correctAnswersCount++;
                    }

                    counter++;
                    Flashcard currCard = allRevisitedCards.get(counter);
                    cardNameLabel.setText(currCard.getFlashcardName());
                    yourAnswerLabel.setText(answersMap.get(currCard));
                    correctAnswerLabel.setText(currCard.getAnswer());

                    /* If the next flashcard is the last one */
                    if(counter==allRevisitedCards.size()-1){
                        nextAnswerCheckButton.setText("Check your results");
                    }
                    radioGroup.clearCheck();
                }
            }
        });


    }
    /* Hides the keyboard by clicking somewhere */
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
