package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cru3lgenius.studenttoolkit.Models.User;
import com.example.cru3lgenius.studenttoolkit.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by denis on 3/18/17.
 */

public class User_Utilities {

    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static User loadUserFirebase(final User currUser, final EditText name,final EditText gender,final EditText age){
        System.out.println("KOGA?!??!?");
        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String user_name = (String) dataSnapshot.child("name").getValue();
                System.out.println("Tuka koga vlizam?!!?!?!?! " + name);
                long user_age =  (long)dataSnapshot.child("age").getValue();
                String user_gender = (String)dataSnapshot.child("gender").getValue();
                currUser.setAge((int)user_age);
                currUser.setGender(user_gender);
                currUser.setName(user_name);
                name.setText(user_name);
                gender.setText(user_gender);
                age.setText(Integer.toString((int)user_age));

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

    public static void uploadProfilePicture(final Context context, Uri filepath, StorageReference storageRef){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Uploading your new profile picture...");
//        progressDialog.show();
        StorageReference riversRef = storageRef.child(new String(auth.getCurrentUser().getEmail().replace('.','_')+"/profile.png"));

        riversRef.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Your profile picture was uploaded successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(context,exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static void downloadProfilePicture(Context context,StorageReference storageReference,ImageView profilePicture){
        // Reference to an image file in Firebase Storage
        // ImageView in your Activity
        System.out.println("ajIJDAJAJIDJADIJDAJDIOAJDOAJDOIJIODAJDAO");
        StorageReference storageRef =  storageReference.child(new String(auth.getCurrentUser().getEmail().replace('.','_')+"/profile.png"));
        ImageView imageView = profilePicture;
        // Load the image using Glide
        Glide.with(context /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .error(R.drawable.profile_icon)
                .into(imageView);
    }
}
