package com.example.cru3lgenius.studenttoolkit.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.example.cru3lgenius.studenttoolkit.Models.User;
import com.example.cru3lgenius.studenttoolkit.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by denis on 3/18/17.
 */

public class User_Utilities {

    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static void saveProfileChanges(String name,String gender,long age){
        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("personal_data").child("age").setValue(age);
        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("personal_data").child("gender").setValue(gender);
        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("personal_data").child("name").setValue(name);
    }

    public static void uploadProfilePicture(final User user, final Context context, Uri filepath, final StorageReference storageRef){

        /* Intialize the progressDialog */
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Upload");
        progressDialog.setMessage("Uploading your new profile picture...");
        StorageReference riversRef = storageRef.child(new String(auth.getCurrentUser().getEmail().replace('.','_')+"/profile.png"));
        progressDialog.show();

        /* Putfile in firebase storage */
        riversRef.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Your profile picture was uploaded successfully",Toast.LENGTH_SHORT).show();
                        ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).child("version").setValue(user.getVersion());

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
    public static void downloadProfilePicture(final User user, final Context context, StorageReference storageReference, final ImageView profilePicture) throws IOException {

        /* Loading picture from firebase using Glide */
        final StorageReference storageRef =  storageReference.child(new String(auth.getCurrentUser().getEmail().replace('.','_')+"/profile.png"));
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .error(R.drawable.profile_icon)
                .signature(new StringSignature(user.getVersion()))
                .into(profilePicture);
    }


}
