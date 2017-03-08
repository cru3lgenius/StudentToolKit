package com.example.cru3lgenius.studenttoolkit.TabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities.SelectCardsForReview;
import com.example.cru3lgenius.studenttoolkit.Activities.Note_Activities.CreateNote;
import com.example.cru3lgenius.studenttoolkit.Adapters.FlashcardsAdapter;
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.Flashcard_Utilities;

import java.util.ArrayList;

/**
 * Created by cru3lgenius on 30.12.16.
 */

public class Flashcards_Fragment extends Fragment {
    View viewRoot;
    private Button reviewSelectedCards;
    private ListView listView;
    private ArrayList<Flashcard> flashcardsToReview = new ArrayList<Flashcard>();
    private FlashcardsAdapter adapter;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_flashcards, container, false);
        /* Initialize widgets block */
        setHasOptionsMenu(true);
        listView = (ListView)viewRoot.findViewById(R.id.lvFlashcards);
        reviewSelectedCards = (Button) viewRoot.findViewById(R.id.btnReviewSelectedCards);
        /* Create button functions */
        reviewSelectedCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CreateFlashcard.class));
            }
        });
        ArrayList<Flashcard> myFlashcards = Flashcard_Utilities.loadFlashcards(getContext());
        if(myFlashcards.isEmpty()){
            Toast.makeText(getContext(),"You have no flashcards yet!",Toast.LENGTH_LONG).show();
        }else {
            adapter = new FlashcardsAdapter(getContext(),R.layout.listview_layout,myFlashcards);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    CheckBox cb =(CheckBox)view.findViewById(R.id.cbFlashcardToReview);
                    cb.setChecked(!cb.isChecked());
                    Flashcard toReview = (Flashcard)adapter.getFlashcards().get(i);
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
                        Toast.makeText(getContext(),"You haven't selected any cards yet!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent i = new Intent(getContext(),ReviewSelectedCards.class);
                    i.putExtra("flashcardsToReview",flashcardsToReview);
                    startActivity(i);
                    getActivity().finish();
                }
            });


        }




        return viewRoot;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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

    public FlashcardsAdapter getAdapter(){
        return adapter;
    }

}