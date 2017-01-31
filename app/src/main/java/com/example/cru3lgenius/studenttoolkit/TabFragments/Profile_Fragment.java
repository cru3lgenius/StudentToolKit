package com.example.cru3lgenius.studenttoolkit.TabFragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Activities.Authentication_Activities.Register;
import com.example.cru3lgenius.studenttoolkit.Activities.Authentication_Activities.SignIn;
import com.example.cru3lgenius.studenttoolkit.R;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

/**
 * Created by Denis on 12/30/16.
 */

public class Profile_Fragment extends Fragment{
    View viewRoot;
    Button logout;
    FirebaseAuth auth;
    TextView profileName;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_profile, container, false);
        logout = (Button) viewRoot.findViewById(R.id.btnLogout);
        auth = FirebaseAuth.getInstance();
        profileName = (TextView) viewRoot.findViewById(R.id.tvProfileName);
        profileName.setText("Hello, " + auth.getCurrentUser().getEmail().toString());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getActivity(), SignIn.class));
                getActivity().finish();
            }
        });
        return viewRoot;
    }
}

