package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.provider.ContactsContract;

import com.example.cru3lgenius.studenttoolkit.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by denis on 3/18/17.
 */

public class User_Utilities {

    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static User loadUserFirebase(){
        final User currUser = new User(auth.getCurrentUser().toString());
        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = (String) dataSnapshot.child("name").getValue();
                System.out.println("Tuka koga vlizam?!!?!?!?! " + name);
                long age =  (long)dataSnapshot.child("age").getValue();
                String gender = (String)dataSnapshot.child("gender").getValue();
                currUser.setAge((int)age);
                currUser.setGender(gender);
                currUser.setName(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return currUser;
    }
    public static void saveProfileChanges(String name,String gender,long age){
        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("age").setValue(age);
        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("gender").setValue(gender);
        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("name").setValue(name);
    }
}
