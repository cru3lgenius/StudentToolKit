package com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Adapters.FlashcardWithAnswersAdapter;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ShowResults extends AppCompatActivity {

    private ListView listView;
    private Button goBackButton;
    private TextView yourResultLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        /* Initialize widgets */
        listView = (ListView) findViewById(R.id.lvResults);
        goBackButton =  (Button) findViewById(R.id.btnGoBackToMenu);
        yourResultLabel = (TextView)findViewById(R.id.tvYourResults);

        /* Load data from the previous activity */
        Bundle b = getIntent().getExtras();
        int correctAnswersCount = (Integer) b.get("correctAnswersCount");
        HashMap<Flashcard,Boolean>correctAnswersMap = (HashMap<Flashcard,Boolean>)b.get("correctAnswersMap");

        /* Initializing collections representing the results */
        Set<Flashcard> allCardsSet = correctAnswersMap.keySet();
        ArrayList<Boolean> boolArr = new ArrayList<Boolean>(correctAnswersMap.values());
        ArrayList<Flashcard> allRevisitedFlashcards = new ArrayList<Flashcard>(allCardsSet);


        FlashcardWithAnswersAdapter adapter = new FlashcardWithAnswersAdapter(getApplicationContext(),R.layout.listview_layout,allRevisitedFlashcards,boolArr);
        listView.setAdapter(adapter);
        yourResultLabel.setText("Results: " + "You answered " +correctAnswersCount + " (out of " + allRevisitedFlashcards.size() +")"+ " questions correctly");
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TabsActivity.class);
                startActivity(i);
            }
        });

    }
}
