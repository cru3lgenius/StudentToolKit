package com.example.cru3lgenius.studenttoolkit.TabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities.CreateFlashcard;
import com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities.ReviewSelectedCards;
import com.example.cru3lgenius.studenttoolkit.Adapters.FlashcardsAdapterHashMap;
import com.example.cru3lgenius.studenttoolkit.Main.Session;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.Flashcard_Utilities;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cru3lgenius on 30.12.16.
 */

public class Flashcards_Fragment extends Fragment {
    View viewRoot;
    private Button reviewSelectedCards,deleteButton ;
    private ListView listView;
    private ArrayList<Flashcard> selectedCards = new ArrayList<Flashcard>();                        //Stores cards to be either reviewed or deleted
    private static HashMap<String,Flashcard> allFlashcards = TabsActivity.getAllCards();    //Stores all cards
    private static FlashcardsAdapterHashMap adapter = new FlashcardsAdapterHashMap(allFlashcards);

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_flashcards, container, false);

        setHasOptionsMenu(true);

        /* Initializing widgets */
        deleteButton = (Button)viewRoot.findViewById(R.id.btnDelete);
        listView = (ListView)viewRoot.findViewById(R.id.lvFlashcards);
        reviewSelectedCards = (Button) viewRoot.findViewById(R.id.btnReviewSelectedCards);
        listView.setAdapter(adapter);


        /* Loading Flashcards from Firebase and updating the adapter after that */
        Flashcard_Utilities.loadFlashcardsFirebase(allFlashcards);
        adapter.updateAdapter(allFlashcards);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /* Adjust "checked or unchecked" checkbox */
                CheckBox cb =(CheckBox)view.findViewById(R.id.cbFlashcardToReview);
                cb.setChecked(!cb.isChecked());

                /* Load the current card */
                Flashcard toReview = (Flashcard)adapter.getItem(i).getValue();

                /* Adding/Removing from container depending on the "check-value" of the checkbox */
                if(cb.isChecked()){
                    selectedCards.add(toReview);
                }
                else if(!cb.isChecked() && selectedCards.contains(toReview)){

                    selectedCards.remove(toReview);
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedCards.isEmpty()){
                    Toast.makeText(getContext(),"You haven't selected any cards for deletion",Toast.LENGTH_SHORT).show();
                    return;
                }

                clearSelectedCards();
                resetCheckboxes();
                adapter.updateAdapter(allFlashcards);
            }
        });

        reviewSelectedCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedCards.isEmpty()){
                    Toast.makeText(getContext(),"You haven't selected any cards yet!",Toast.LENGTH_SHORT).show();
                    return;
                }

                /* Start the new activity */
                Intent i = new Intent(getContext(),ReviewSelectedCards.class);
                i.putExtra("flashcardsToReview",selectedCards);
                startActivity(i);

                /* Close current activity*/
                getActivity().finish();
                selectedCards.clear();
                resetCheckboxes();
                adapter.notifyDataSetChanged();
            }
        });

        return viewRoot;
    }

    private void clearSelectedCards() {
        deleteCards(selectedCards);
        selectedCards.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* On clicking on the + sign starts new Activity that handles creating a new flashcard */
        switch(item.getItemId()){
            case R.id.menu_action_create_flashcard:
                Intent i = new Intent(getContext(),CreateFlashcard.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_flashcard,menu);
    }
    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    public static FlashcardsAdapterHashMap getAdapter(){
        return adapter;
    }

    /* Deletes flashcards from firebase */
    public void deleteCards(ArrayList<Flashcard> cards){
        Flashcard_Utilities.deleteFlashcardsFirebase(cards);
    }

    /* Reset checkboxes ticks */
    public void resetCheckboxes(){
        CheckBox cb;

        for(int i=0; i<listView.getChildCount();i++)
        {
            cb = (CheckBox)listView.getChildAt(i).findViewById(R.id.cbFlashcardToReview);
            cb.setChecked(false);
        }
    }

}