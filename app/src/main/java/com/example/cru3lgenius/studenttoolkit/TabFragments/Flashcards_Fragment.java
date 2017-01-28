package com.example.cru3lgenius.studenttoolkit.TabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities.CreateFlashcard;
import com.example.cru3lgenius.studenttoolkit.Activities.Flashcard_Activities.ReviewFlashcards;
import com.example.cru3lgenius.studenttoolkit.R;

/**
 * Created by cru3lgenius on 30.12.16.
 */

public class Flashcards_Fragment extends Fragment {
    View viewRoot;
    Button createFlashcard,reviewFlashcards;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_flashcards, container, false);
        /* Initialize widgets block */
        createFlashcard = (Button)viewRoot.findViewById(R.id.btnCreateFlashcard);
        reviewFlashcards = (Button)viewRoot.findViewById(R.id.btnReviewCards);

        /* Create button functions */
        createFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CreateFlashcard.class));
            }
        });

        reviewFlashcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ReviewFlashcards.class));
            }
        });

        return viewRoot;
    }
}