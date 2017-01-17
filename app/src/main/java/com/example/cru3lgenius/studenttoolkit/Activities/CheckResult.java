package com.example.cru3lgenius.studenttoolkit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CheckResult extends AppCompatActivity {

    Button nextAnswerCheck;
    TextView yourAnswer,correctAnswer,cardName;
    RadioGroup radioGroup;
    RadioButton rbCorrectAnswer,rbFalseAnswer;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_result);
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
        counter = 0;
        Set<Flashcard> foo = answersMap.keySet();
        final ArrayList<Flashcard> allRevisitedCards = new ArrayList<Flashcard>(foo);
        Flashcard currCard = allRevisitedCards.get(counter);
        cardName.setText(currCard.getFlashcardName());
        yourAnswer.setText(answersMap.get(currCard));
        correctAnswer.setText(currCard.getAnswer());


        /* On click of the button */
        nextAnswerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* TODO: Finish with the check */
                if(counter == allRevisitedCards.size()-1){
                    /* Finish and show the number of correct answers */
                    return;
                }
                if(counter<allRevisitedCards.size()){
                    counter++;
                    Flashcard currCard = allRevisitedCards.get(counter);
                    cardName.setText(currCard.getFlashcardName());
                    yourAnswer.setText(answersMap.get(currCard));
                    correctAnswer.setText(currCard.getAnswer());
                }


            }
        });


    }
}
