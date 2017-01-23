package TabFragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.cru3lgenius.studenttoolkit.Activities.CreateNote;
import com.example.cru3lgenius.studenttoolkit.R;

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
        System.out.println("INFLATE-nah ta");
    }
    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }
}
