package com.example.cru3lgenius.studenttoolkit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.cru3lgenius.studenttoolkit.Adapters.FlashcardWithAnswersAdapter;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ShowResults extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        listView = (ListView) findViewById(R.id.lvResults);
        Bundle b = getIntent().getExtras();
        int correctAnswersCount = (Integer) b.get("correctAnswersCount");
        HashMap<Flashcard,Boolean>correctAnswersMap = (HashMap<Flashcard,Boolean>)b.get("correctAnswersMap");
        Set<Flashcard> allCardsSet = correctAnswersMap.keySet();
        Set mySet = new HashSet(correctAnswersMap.values());
        ArrayList<Flashcard> allRevisitedFlashcards = new ArrayList<Flashcard>(allCardsSet);
        FlashcardWithAnswersAdapter adapter = new FlashcardWithAnswersAdapter(getApplicationContext(),R.layout.listview_layout,allRevisitedFlashcards,mySet);
        listView.setAdapter(adapter);
    }
}
