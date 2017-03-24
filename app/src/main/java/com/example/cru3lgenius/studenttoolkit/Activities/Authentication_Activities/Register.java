package com.example.cru3lgenius.studenttoolkit.Activities.Authentication_Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.User;
import com.example.cru3lgenius.studenttoolkit.R;
import com.example.cru3lgenius.studenttoolkit.Utilities.User_Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.UUID;

public class Register extends AppCompatActivity {

    private Button register;
    private EditText email,password;
    private TextView signIn;
    private static StorageReference storageReference =  FirebaseStorage.getInstance().getReferenceFromUrl("gs://studenttoolkit-c9f0f.appspot.com");;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.btnRegister);
        email = (EditText) findViewById(R.id.etEmailRegister);
        password = (EditText) findViewById(R.id.etPasswordRegister);
        signIn = (TextView) findViewById(R.id.tvSignIn);
        layout = (RelativeLayout) findViewById(R.id.activity_register);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
        System.out.println("SHAMARA");
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), TabsActivity.class));
            finish();
        }
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_register();
            }
        });
    }

    private void user_register() {
        final String email_str = email.getText().toString();
        String password_str = password.getText().toString();

        if(TextUtils.isEmpty(email_str)){
            makeToast("You cannot leave the email field empty");
            return;
        }
        if(TextUtils.isEmpty(password_str)){
            makeToast("You cannot leave the password field empty");
            return;
        }
        progressDialog.setMessage("Registering....");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email_str,password_str ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String ver = UUID.randomUUID().toString();
                    User new_user = new User(email_str,ver);
                    String email = email_str.replace('.','_');
                    ref.child("users").child(email).setValue(new_user);
                    makeToast("You registered successfully");
                    Intent intent = new Intent(getApplicationContext(),SignIn.class);
                    startActivity(intent);
                    finish();
                }else{
                    makeToast("Registration was unsuccussful, please try again !");
                }
                progressDialog.dismiss();
            }
        });

    }

    private void makeToast(String toShow){
        Toast.makeText(getApplicationContext(),toShow,Toast.LENGTH_SHORT).show();
    }

    /* Hides the keyboard by clicking somewhere */
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
