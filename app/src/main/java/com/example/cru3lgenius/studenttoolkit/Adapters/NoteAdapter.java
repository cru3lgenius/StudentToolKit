package com.example.cru3lgenius.studenttoolkit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;

/**
 * Created by denis on 1/25/17.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, int resource, Note[] objects) {
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
            
        }
        return super.getView(position, convertView, parent);
    }
}
