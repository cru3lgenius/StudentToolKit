package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Adapters.FlashcardsAdapter;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.TabsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReviewFlashcards extends AppCompatActivity {
    final Gson gson = new Gson();

    SharedPreferences sharedPrefs;
    ArrayList<Flashcard> myFlashcards = TabsActivity.myFlashcards;
    ArrayList<Flashcard> flashcardsToReview = new ArrayList<Flashcard>();
    FlashcardsAdapter arrayAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_flashcards);
        sharedPrefs = getSharedPreferences(TabsActivity.FLASHCARD_PREFERENCES,MODE_PRIVATE);
        listView = (ListView) findViewById(R.id.lvFlashcards);
        Button reviewSelectedCards = (Button)findViewById(R.id.btnReviewSelectedCards);
        String jsonFlashcardList = sharedPrefs.getString(TabsActivity.MY_FLASHCARDS_ARRAYLIST,"default");
        if(jsonFlashcardList.equals("default")){
            Toast.makeText(getApplicationContext(),"You have no flashcards yet!",Toast.LENGTH_LONG).show();
        }else {
            System.out.println(jsonFlashcardList);
            myFlashcards = (ArrayList<Flashcard>) gson.fromJson(jsonFlashcardList,new TypeToken<ArrayList<Flashcard>>() {}.getType());
            for(Flashcard each:myFlashcards){
                System.out.println(each.getFlashcardName());
            }
            //arrayAdapter = new ArrayAdapter<Flashcard>(this,R.layout.listview_layout,myFlashcards);
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
                    Intent i = new Intent(getApplicationContext(),ReviewSelectedCards.class);
                    i.putExtra("flashcardsToReview",flashcardsToReview);
                    startActivity(i);
                }
            });

        }

    }


}
