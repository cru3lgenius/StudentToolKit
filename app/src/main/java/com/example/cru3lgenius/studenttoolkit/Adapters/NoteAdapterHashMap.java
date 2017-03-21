package com.example.cru3lgenius.studenttoolkit.Adapters;

/**
 * Created by denis on 2/21/17.
 */
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class NoteAdapterHashMap extends BaseAdapter{
    private ArrayList mData = new ArrayList();

    public NoteAdapterHashMap(Map<String, Note> map) {
        mData.addAll(map.entrySet());

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Entry<String, Note> getItem(int position) {
        return (Entry) mData.get(position);
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
        Set<Entry<String,Note>> entries = map.entrySet();
        this.mData.addAll(map.entrySet());
        Collections.sort(this.mData, DATE_ORDER);
        this.notifyDataSetChanged();
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    public Comparator<Entry<String,Note>> DATE_ORDER = new Comparator<Entry<String,Note>>() {
        @Override
        public int compare(Entry<String, Note> o1, Entry<String, Note> o2) {
            if(o1.getValue().getmDateTime()<o2.getValue().getmDateTime()){
                return 1;
            }
            return -1;
        }


    };
}
