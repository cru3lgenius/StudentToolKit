package com.example.cru3lgenius.studenttoolkit.Adapters;

/**
 * Created by denis on 2/21/17.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteAdapterHashMap extends BaseAdapter{
    private ArrayList mData;

    public NoteAdapterHashMap(Map<String, Note> map) {

        mData = new ArrayList();
        mData.addAll(map.entrySet());

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Note> getItem(int position) {
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
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_listview, parent, false);
        } else {
            result = convertView;
        }

        Note note = getItem(position).getValue();
        if(note!=null){
            TextView title = (TextView) result.findViewById(R.id.list_note_title);
            TextView date = (TextView) result.findViewById(R.id.list_note_date);
            TextView content = (TextView) result.findViewById(R.id.list_note_content);
            title.setText(note.getmTitle());
            date.setText(note.getDateFormatted(parent.getContext()));
            content.setText(note.getmContent());

            if(note.getmContent().length()>50){
                content.setText(note.getmContent().substring(0,50));
            }

        }


        return result;
    }
    public void updateAdapter(HashMap<String,Note> map){
        this.mData.clear();
        this.mData.addAll(map.entrySet());
        notifyDataSetChanged();
    }
}
