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

    private Button registerButton;
    private EditText emailTextField,passwordTextField;
    private TextView signInLabel;
    private static StorageReference storageReference =  FirebaseStorage.getInstance().getReferenceFromUrl("gs://studenttoolkit-c9f0f.appspot.com");;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* Initialize widgets */
        registerButton = (Button) findViewById(R.id.btnRegister);
        emailTextField = (EditText) findViewById(R.id.etEmailRegister);
        passwordTextField = (EditText) findViewById(R.id.etPasswordRegister);
        signInLabel = (TextView) findViewById(R.id.tvSignIn);
        progressDialog = new ProgressDialog(this);

        /* Hide keyboard on click */
        layout = (RelativeLayout) findViewById(R.id.activity_register);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        /* Intialize Firebase variables */
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        //Skip this activity if already signed in
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), TabsActivity.class));
            finish();
        }

        /* On click on signIn label open the SignIn activity */
        signInLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();

            }
        });

        /* On click on register button register user */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });
    }



    private void userRegister() {
        final String email_str = emailTextField.getText().toString();
        String password_str = passwordTextField.getText().toString();

        /* Check user input */
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

        /* Registering the user in firebase */
        auth.createUserWithEmailAndPassword(email_str,password_str ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //Initialize new user
                    String ver = UUID.randomUUID().toString();
                    User new_user = new User(email_str,ver);
                    String email = email_str.replace('.','_');

                    //Store data on firebase
                    ref.child("users").child(email).child("version").setValue(new_user.getVersion());
                    ref.child("users").child(email).child("personal_data").child("name").setValue(new_user.getName());
                    ref.child("users").child(email).child("personal_data").child("gender").setValue(new_user.getGender());
                    ref.child("users").child(email).child("personal_data").child("age").setValue(new_user.getAge());

                    //Start SignInActivity
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

    /* Toast helper function */
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
