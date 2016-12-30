package TabFragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cru3lgenius.studenttoolkit.R;

/**
 * Created by Denis on 12/30/16.
 */

public class Settings_Fragment extends Fragment{
    View viewRoot;
    Button createFlashcard;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_settings, container, false);

        return viewRoot;
    }
}

