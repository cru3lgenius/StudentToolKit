package TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cru3lgenius.studenttoolkit.R;

/**
 * Created by cru3lgenius on 30.12.16.
 */

public class Flashcards_Fragment extends Fragment {
    View viewRoot;
    Button createFlashcard;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.tabs_activity_fragment, container, false);

        return viewRoot;
    }
}