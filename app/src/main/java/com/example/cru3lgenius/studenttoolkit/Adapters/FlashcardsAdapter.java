package com.example.cru3lgenius.studenttoolkit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 1/3/17.
 */

public class FlashcardsAdapter extends ArrayAdapter<Flashcard> {
    private List <Flashcard> flashcards ;

    private static LayoutInflater layoutInflater;
    public FlashcardsAdapter(Context context, int resource ,List<Flashcard>flashcards) {
        super(context, resource);
        this.flashcards = flashcards;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        public TextView flashcardName;
    }

    public int getCount(){
        return this.flashcards.size();
    }

    public ArrayList<Flashcard> getFlashcards(){
        return (ArrayList<Flashcard>)this.flashcards;
    }
    public void setUsersList(List<Flashcard> flashcards){
        this.flashcards = flashcards;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View vi = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            vi = layoutInflater.inflate(R.layout.flashcard_listview, null);
            holder = new ViewHolder();
            holder.flashcardName = (TextView)  vi.findViewById(R.id.tvFlashcardList);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();

        }


        holder.flashcardName.setText(flashcards.get(position).getFlashcardName());


        return vi;
    }
}
