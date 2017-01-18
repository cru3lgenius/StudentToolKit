package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CheckAnswers extends AppCompatActivity {

    Button nextAnswerCheck;
    TextView yourAnswer,correctAnswer,cardName;
    RadioGroup radioGroup;
    RadioButton rbCorrectAnswer,rbFalseAnswer;
    int counter,correctAnswersCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_answers);
        /* Initialize widgets */
        nextAnswerCheck = (Button)findViewById(R.id.btnNextAnswerCheck);
        yourAnswer = (TextView) findViewById(R.id.tvYourAnswer);
        correctAnswer = (TextView)findViewById(R.id.tvCorrectAnswer);
        cardName = (TextView)findViewById(R.id.tvCardNameLabel);
        rbCorrectAnswer = (RadioButton) findViewById(R.id.rbCorrectAnswer);
        rbFalseAnswer = (RadioButton) findViewById(R.id.rbFalseAnswer);

        /* Extract transfered from previous activity data */
        Bundle b = getIntent().getExtras();
        final HashMap<Flashcard,String> answersMap = (HashMap<Flashcard,String>)b.get("answersMap");

        /* Put the information on the display */
        counter = 0;    // index showing which card is currently displayed
        correctAnswersCount = 0; // Showing how many cards user guessed right
        Set<Flashcard> revisitedCardsSet = answersMap.keySet();
        final ArrayList<Flashcard> allRevisitedCards = new ArrayList<Flashcard>(revisitedCardsSet);
        Flashcard currCard = allRevisitedCards.get(counter);
        cardName.setText(currCard.getFlashcardName());
        yourAnswer.setText(answersMap.get(currCard));
        correctAnswer.setText(currCard.getAnswer());

        /* If there is only one card to review */
        if(counter==allRevisitedCards.size()){
            nextAnswerCheck.setText("Check your results");
        }

        /* On click of the button */
        nextAnswerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!rbCorrectAnswer.isChecked()&&!rbFalseAnswer.isChecked()){
                    Toast.makeText(getApplicationContext(),"Before you continue you must select if your answer was correct or false!",Toast.LENGTH_SHORT).show();
                    return;
                }

                /* If you are at the last card */
                if(counter == allRevisitedCards.size()-1){

                    /* Increment the number of correct guessed cards */
                    if(rbCorrectAnswer.isChecked()){
                        correctAnswersCount++;
                    }

                    /* Finish and show the number of correct answers */
                    Intent i = new Intent(getApplicationContext(),ShowResults.class);
                    i.putExtra("correctAnswersCount",correctAnswersCount);
                    i.putExtra("allAnswersCount",allRevisitedCards.size());
                    startActivity(i);
                    return;
                }

                if(counter<allRevisitedCards.size()-1){

                    /* Increment the number of correct guessed cards */
                    if(rbCorrectAnswer.isChecked()){
                        correctAnswersCount++;
                    }

                    counter++;
                    Flashcard currCard = allRevisitedCards.get(counter);
                    cardName.setText(currCard.getFlashcardName());
                    yourAnswer.setText(answersMap.get(currCard));
                    correctAnswer.setText(currCard.getAnswer());

                    /* If the next flashcard is the last one */
                    if(counter==allRevisitedCards.size()-1){
                        nextAnswerCheck.setText("Check your results");
                    }
                    rbCorrectAnswer.setChecked(false);
                    rbFalseAnswer.setChecked(false);
                }
            }
        });


    }
}