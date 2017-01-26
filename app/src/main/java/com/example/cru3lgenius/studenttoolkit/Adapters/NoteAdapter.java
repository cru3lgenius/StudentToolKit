package com.example.cru3lgenius.studenttoolkit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;

/**
 * Created by denis on 1/25/17.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, int resource, ArrayList<Note> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note_listview,null);
        }
        Note note = getItem(position);
        if(note!=null){
            TextView title = (TextView) convertView.findViewById(R.id.list_note_title);
            TextView date = (TextView) convertView.findViewById(R.id.list_note_date);
            TextView content = (TextView) convertView.findViewById(R.id.list_note_content);
            title.setText(note.getmTitle());
            date.setText(note.getDataFormatted(getContext()));
            content.setText(note.getmContent());

            if(note.getmContent().length()>50){
                content.setText(note.getmContent().substring(0,50));
            }

        }
        return convertView;
    }
}
