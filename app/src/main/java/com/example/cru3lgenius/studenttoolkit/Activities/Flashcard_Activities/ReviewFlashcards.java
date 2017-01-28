package com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Adapters.FlashcardsAdapter;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.Flashcard_Utilities;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ReviewFlashcards extends AppCompatActivity {
    final Gson gson = new Gson();

    SharedPreferences sharedPrefs;
    ArrayList<Flashcard> flashcardsToReview = new ArrayList<Flashcard>();
    FlashcardsAdapter arrayAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_flashcards);

        listView = (ListView) findViewById(R.id.lvFlashcards);
        Button reviewSelectedCards = (Button)findViewById(R.id.btnReviewSelectedCards);
        ArrayList<Flashcard> myFlashcards = Flashcard_Utilities.loadFlashcards(getApplicationContext());
        if(myFlashcards.isEmpty()){
            Toast.makeText(getApplicationContext(),"You have no flashcards yet!",Toast.LENGTH_LONG).show();
        }else {
            arrayAdapter = new FlashcardsAdapter(this,R.layout.listview_layout,myFlashcards);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    CheckBox cb =(CheckBox)view.findViewById(R.id.cbFlashcardToReview);
                    cb.setChecked(!cb.isChecked());
                    Flashcard toReview = (Flashcard)arrayAdapter.getFlashcards().get(i);
                    if(cb.isChecked()){
                        flashcardsToReview.add(toReview);
                    }
                    else if(!cb.isChecked() && flashcardsToReview.contains(toReview)){
                        flashcardsToReview.remove(toReview);
                    }

                }
            });
            reviewSelectedCards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(flashcardsToReview.isEmpty()){
                        Toast.makeText(getApplicationContext(),"You haven't selected any cards yet!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent i = new Intent(getApplicationContext(),ReviewSelectedCards.class);
                    i.putExtra("flashcardsToReview",flashcardsToReview);
                    startActivity(i);
                    finish();
                }
            });

        }

    }


}
