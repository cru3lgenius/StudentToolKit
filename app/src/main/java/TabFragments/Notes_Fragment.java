package TabFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Activities.CreateNote;
import com.example.cru3lgenius.studenttoolkit.Activities.EditNote;
import com.example.cru3lgenius.studenttoolkit.Adapters.NoteAdapter;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.Note_Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 1/22/17.
 */

public class Notes_Fragment extends Fragment {
    View viewRoot;
    ListView displayNotes;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_notes, container, false);
        displayNotes = (ListView) viewRoot.findViewById(R.id.lvNotes);
        setHasOptionsMenu(true);
        ArrayList<Note> allNotes  = Note_Utilities.loadNotes(getContext());
        return viewRoot;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_action_CreateNewNote:
                Intent i = new Intent(getContext(), CreateNote.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_note,menu);
    }
    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }
    @Override
    public void onResume() {
        super.onResume();
        final ArrayList<Note> notes = (ArrayList<Note>) Note_Utilities.loadNotes(getContext());
        if(notes.isEmpty()){
            Toast.makeText(getContext(), "You have no notes yet!", Toast.LENGTH_SHORT).show();
            return;
        }
        NoteAdapter noteAdapter = new NoteAdapter(getContext(),R.layout.item_note_listview,notes);
        displayNotes.setAdapter(noteAdapter);
        displayNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), CreateNote.class);
                Note note = notes.get(position);
                i.putExtra("noteToDisplay",note).putExtra("positionOfNote",position);
                startActivity(i);

            }
        });
    }

}
