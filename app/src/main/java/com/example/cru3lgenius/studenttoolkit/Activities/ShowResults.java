package com.example.cru3lgenius.studenttoolkit.Activities;

import android.content.Intent;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Adapters.FlashcardWithAnswersAdapter;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.TabsActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ShowResults extends AppCompatActivity {

    ListView listView;
    Button goBack;
    TextView yourResultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        listView = (ListView) findViewById(R.id.lvResults);
        goBack =  (Button) findViewById(R.id.btnGoBackToMenu);
        yourResultTextView = (TextView)findViewById(R.id.tvYourResults);
        Bundle b = getIntent().getExtras();
        int correctAnswersCount = (Integer) b.get("correctAnswersCount");
        HashMap<Flashcard,Boolean>correctAnswersMap = (HashMap<Flashcard,Boolean>)b.get("correctAnswersMap");
        for(Flashcard each: correctAnswersMap.keySet()){
            System.out.println(each.getFlashcardName() + " and answer is: " + correctAnswersMap.get(each));
        }
        Set<Flashcard> allCardsSet = correctAnswersMap.keySet();
        ArrayList<Boolean> boolArr = new ArrayList<Boolean>(correctAnswersMap.values());
        ArrayList<Flashcard> allRevisitedFlashcards = new ArrayList<Flashcard>(allCardsSet);
        System.out.println("size of mySet and then of allRevisitedCards " + boolArr.size() + allRevisitedFlashcards.size() );

        FlashcardWithAnswersAdapter adapter = new FlashcardWithAnswersAdapter(getApplicationContext(),R.layout.listview_layout,allRevisitedFlashcards,boolArr);
        yourResultTextView.setText("Results: " + "You answered " +correctAnswersCount + " (out of " + allRevisitedFlashcards.size() +")"+ " questions correctly");
        listView.setAdapter(adapter);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TabsActivity.class);
                startActivity(i);
            }
        });

    }
}
