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
import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.Flashcard_Utilities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cru3lgenius on 30.12.16.
 */

public class Flashcards_Fragment extends Fragment {
    View viewRoot;
    private Button reviewSelectedCards,deleteBtn;
    private ListView listView;
    private ArrayList<Flashcard> selectedCards = new ArrayList<Flashcard>();
    private FlashcardsAdapterHashMap adapter;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_flashcards, container, false);
        /* Initialize widgets block */
        setHasOptionsMenu(true);
        deleteBtn = (Button)viewRoot.findViewById(R.id.btnDelete);
        listView = (ListView)viewRoot.findViewById(R.id.lvFlashcards);
        reviewSelectedCards = (Button) viewRoot.findViewById(R.id.btnReviewSelectedCards);
        /* Create button functions */
        reviewSelectedCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedCards.isEmpty()){
                    Toast.makeText(getContext(),"You haven't selected any cards yet!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(getContext(),ReviewSelectedCards.class);
                i.putExtra("flashcardsToReview",selectedCards);
                startActivity(i);
                getActivity().finish();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedCards.isEmpty()){
                    Toast.makeText(getContext(),"You haven't selected any cards for deletion",Toast.LENGTH_SHORT).show();
                    return;
                }
                delete(selectedCards);
                System.out.println("PRAA NESHTO");
            }
        });
        HashMap<String,Flashcard> myFlashcards = Flashcard_Utilities.loadFlashcardsLocally(getContext());

        if(myFlashcards.isEmpty()){
            Toast.makeText(getContext(),"You have no flashcards yet!",Toast.LENGTH_LONG).show();
        }else {
            adapter = new FlashcardsAdapterHashMap(myFlashcards);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    CheckBox cb =(CheckBox)view.findViewById(R.id.cbFlashcardToReview);
                    cb.setChecked(!cb.isChecked());
                    Flashcard toReview = (Flashcard)adapter.getItem(i).getValue();

                    if(cb.isChecked()){
                        selectedCards.add(toReview);
                    }
                    else if(!cb.isChecked() && selectedCards.contains(toReview)){

                        selectedCards.remove(toReview);
                    }

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

    public FlashcardsAdapterHashMap getAdapter(){
        return adapter;
    }

    public void delete(ArrayList<Flashcard> cards){
        Flashcard_Utilities.deleteFlashcards(cards,getContext(),adapter);
    }



}