package com.example.cru3lgenius.studenttoolkit.Adapters;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on 3/8/17.
 */

public class FlashcardsAdapterHashMap extends BaseAdapter {
    private ArrayList mData;
    public FlashcardsAdapterHashMap(Map<String, Flashcard> map) {

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
        // TODO implement logic with ID
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
            CheckBox flashcardCB = (CheckBox) result.findViewById(R.id.cbFlashcardToReview);
            flashcardCB.setTextAppearance(android.R.style.TextAppearance_Large);
            flashcardCB.setText(flashcard.getFlashcardName());



        }


        return result;
    }
    public void updateAdapter(HashMap<String,Flashcard> map){

        this.mData = new ArrayList(map.entrySet());

        this.notifyDataSetChanged();

    }
}
