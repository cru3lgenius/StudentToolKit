package com.example.cru3lgenius.studenttoolkit.Adapters;

import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by denis on 3/8/17.
 */

public class FlashcardsAdapterHashMap extends BaseAdapter {



    private ArrayList mData;


    public FlashcardsAdapterHashMap(Map<String, Flashcard> map) {
        /* ArrayList to store the data from the MAP */
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Flashcard> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard_listview, parent, false);
        } else {
            result = convertView;
        }

        Flashcard flashcard = getItem(position).getValue();
        if(flashcard!=null){

            /* Adjust the view for the adapter */
            CheckBox flashcardCB = (CheckBox) result.findViewById(R.id.cbFlashcardToReview);
            TextView flashcardTV = (TextView) result.findViewById(R.id.tvFlashcardItem);
            flashcardTV.setTextAppearance(android.R.style.TextAppearance_Small);
            flashcardTV.setText(flashcard.getDateFormatted(parent.getContext()));
            flashcardCB.setTextAppearance(android.R.style.TextAppearance_Large);
            flashcardCB.setTypeface(null, Typeface.BOLD);
            flashcardCB.setText(flashcard.getFlashcardName());
        }


        return result;
    }
    public void updateAdapter(HashMap<String,Flashcard> map){

        /* ArrayList update */
        this.mData.clear();
        Set<Map.Entry<String,Flashcard>> entries = map.entrySet();
        this.mData.addAll(map.entrySet());

        /*  Sort flashcards */
        Collections.sort(this.mData, NAME_ORDER);
        this.notifyDataSetChanged();

    }

    /* Comparator for sorting flashcards */
    public Comparator<Map.Entry<String,Flashcard>> NAME_ORDER = new Comparator<Map.Entry<String,Flashcard>>() {
        @Override
        public int compare(Map.Entry<String, Flashcard> o1, Map.Entry<String, Flashcard> o2) {
            int result = String.CASE_INSENSITIVE_ORDER.compare(o1.getValue().getFlashcardName(),o2.getValue().getFlashcardName());
            return result;
        }


    };
}
