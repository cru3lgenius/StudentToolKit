package com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.Flashcard_Utilities;

import java.util.UUID;

public class CreateFlashcard extends AppCompatActivity {
    private Button saveFlashcardButton;

    private EditText answerTextField,questionTextField,flashCardNameTextField;
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);

        /* Adjust the actionbar */
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Initialize widgets */
        saveFlashcardButton = (Button)findViewById(R.id.btnSaveFlashcard);
        answerTextField = (EditText) findViewById(R.id.etAnswer);
        questionTextField = (EditText) findViewById(R.id.etQuestion);
        flashCardNameTextField = (EditText) findViewById(R.id.etFlashcardName);

        /* Start saving action on click */
        saveFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFlashcard();
                finish();

            }
        });

         /* hide the keyboard on click */
        layout = (RelativeLayout) findViewById(R.id.activity_create_flashcard);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });



    }

    private void saveFlashcard(){
        /* Generates attributes for the card */
        String flashcardId = UUID.randomUUID().toString();
        String flashCardNameStr = flashCardNameTextField.getText().toString();
        String answerStr = answerTextField.getText().toString();
        String questStr = questionTextField.getText().toString();

        /* Handle the case when the user does not give appropriate information */
        if(flashCardNameStr.equals("") || answerStr.equals("") || questStr.equals("")){
            Toast.makeText(this.getApplicationContext(),"Empty fields are not allowed!",Toast.LENGTH_SHORT).show();
            return;
        }

        /* Initialize the card with the above attributes */
        Flashcard card = new Flashcard(flashcardId, questStr, answerStr, flashCardNameStr);
        Flashcard_Utilities.saveFlashcard(getApplicationContext(),card);

    }

    // Return to Flashcard fragment button
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    /* Hides the keyboard by clicking somewhere */
    private void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
