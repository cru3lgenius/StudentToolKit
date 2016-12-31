package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.TabsActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ReviewFlashcards extends AppCompatActivity {
    final Gson gson = new Gson();
    SharedPreferences sharedPrefs;
    ArrayList<String>myFlashcards = TabsActivity.myFlashcards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_flashcards);
        sharedPrefs = getSharedPreferences(TabsActivity.FLASHCARD_PREFERENCES,MODE_PRIVATE);
        String jsonFlashcardList = sharedPrefs.getString(TabsActivity.MY_FLASHCARDS_ARRAYLIST,"default");
        if(jsonFlashcardList.equals("default")){
            Toast.makeText(getApplicationContext(),"You have no flashcards yet!",Toast.LENGTH_LONG).show();
        }else {
            myFlashcards = (ArrayList<String>) gson.fromJson(jsonFlashcardList, ArrayList.class);
             
            for(String each:myFlashcards){
                System.out.println(each);
            }
        }

    }
}
