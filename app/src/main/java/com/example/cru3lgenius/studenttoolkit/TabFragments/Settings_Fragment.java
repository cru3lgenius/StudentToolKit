package com.example.cru3lgenius.studenttoolkit.TabFragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cru3lgenius.studenttoolkit.Activities.Authentication_Activities.Register;
import com.example.cru3lgenius.studenttoolkit.R;

/**
 * Created by Denis on 12/30/16.
 */

public class Settings_Fragment extends Fragment{
    View viewRoot;
    Button test;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_settings, container, false);
        test = (Button) viewRoot.findViewById(R.id.btnTEST);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Register.class));
            }
        });
        return viewRoot;
    }
}

