package TabFragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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





        return viewRoot;
    }
}
