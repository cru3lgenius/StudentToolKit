package com.example.cru3lgenius.studenttoolkit.TabFragments;

import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Activities.Authentication_Activities.Register;
import com.example.cru3lgenius.studenttoolkit.Activities.Authentication_Activities.SignIn;
import com.example.cru3lgenius.studenttoolkit.Main.Session;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.User;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.User_Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Denis on 12/30/16.
 */

public class Profile_Fragment extends Fragment{
    private static final int PICK_IMAGE = 200;
    View viewRoot;
    private Session session;
    private User currUser;
    private static ImageView profilePicture;
    private static StorageReference storageRef;

    private KeyListener gender1_listener,age1_listener,profilename1_listener;
    private Button logout,edit;
    private boolean clicked = false;
    private static FirebaseAuth auth;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private RelativeLayout layout;
    private Uri filePath;

    private static EditText profileName1,age1,gender1;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize widgets and used objects
        session = new Session(getContext());
        currUser = Session.retrieveUser();
        logout = (Button) viewRoot.findViewById(R.id.btnLogout);
        edit = (Button) viewRoot.findViewById(R.id.btnEditProfile);
        auth = FirebaseAuth.getInstance();
        profilePicture = (ImageView)viewRoot.findViewById(R.id.ivProfilePicture);
        profileName1 = (EditText) viewRoot.findViewById(R.id.etProfileName1);
        age1 = (EditText) viewRoot.findViewById(R.id.etAge1);
        gender1 = (EditText) viewRoot.findViewById(R.id.etGender1);
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://studenttoolkit-c9f0f.appspot.com");

        // Temps to handle editable and not editable EditTexts
        gender1_listener = gender1.getKeyListener();
        age1_listener = age1.getKeyListener();
        profilename1_listener = profileName1.getKeyListener();


        // Makes the EditTexts not editable
        gender1.setKeyListener(null);
        age1.setKeyListener(null);
        profileName1.setKeyListener(null);

        //On click on the Profile picture pops a new Window
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureChooser();}
        });
        layout = (RelativeLayout) viewRoot.findViewById(R.id.fragment_profile);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        // Update EditTexts with the information about the current user
        profileName1.setText(currUser.getName());
        gender1.setText(currUser.getGender());
        age1.setText(Integer.toString(currUser.getAge()));


        try {
            User_Utilities.downloadProfilePicture(currUser,getContext(),storageRef,profilePicture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // By loging out clear all the data you stored
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getActivity(), SignIn.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                TabsActivity.getAllCards().clear();
                TabsActivity.getAllNotes().clear();
                Flashcards_Fragment.getAdapter().updateAdapter(TabsActivity.getAllCards());
                Notes_Fragment.getNoteAdapter().updateAdapter(TabsActivity.getAllNotes());
                getActivity().finish();

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Edit and Save mode */
                if(!clicked){

                    //Makes editable
                    age1.setKeyListener(age1_listener);
                    profileName1.setKeyListener(profilename1_listener);
                    gender1.setKeyListener(gender1_listener);
                    edit.setText("Save Changes");

                }else{
                    long age = Long.parseLong(age1.getText().toString());
                    String gender = gender1.getText().toString();
                    String name = profileName1.getText().toString();
                    User_Utilities.saveProfileChanges(name,gender,age);

                    //Makes not Editable again
                    age1.setKeyListener(null);
                    profileName1.setKeyListener(null);
                    gender1.setKeyListener(null);
                    edit.setText("Edit Profile");

                }
                clicked = !clicked;
            }
        });

        // Updates personal Data on Edit
        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("personal_data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null||dataSnapshot.getChildrenCount()==0) {
                    return;
                }

                String name = (String) dataSnapshot.child("name").getValue();
                String gender = (String) dataSnapshot.child("gender").getValue();
                long age = (long) dataSnapshot.child("age").getValue();
                User currUser = Session.retrieveUser();
                currUser.setName(name);
                currUser.setGender(gender);
                currUser.setAge((int)age);
                Session.storeUser(currUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return viewRoot;
    }


    /* Hides the keyboard by clicking somewhere */
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void pictureChooser(){

        // Opens a window to choose your new profile picture
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose your profile picture."),PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode == RESULT_OK && data!=null){
            filePath = data.getData();
            try {
                // Upload your new picture and make necessary updates to make that clear to all users
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);
                String ver = UUID.randomUUID().toString();
                currUser.setVersion(ver);
                Session.storeUser(currUser);
                User_Utilities.uploadProfilePicture(currUser,getContext(),filePath,storageRef);
                profilePicture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

}

