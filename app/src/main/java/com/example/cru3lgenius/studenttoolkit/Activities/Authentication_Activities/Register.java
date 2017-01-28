package com.example.cru3lgenius.studenttoolkit.Activities.Authentication_Activities;

import android.app.ProgressDialog;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    Button register;
    EditText email,password;
    TextView signIn;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.btnRegister);
        email = (EditText) findViewById(R.id.etEmailRegister);
        password = (EditText) findViewById(R.id.etPasswordRegister);
        signIn = (TextView) findViewById(R.id.tvSignIn);
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: GO TO SIGN IN
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
        String email_str = email.getText().toString();
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
                    makeToast("You registered successfully");
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
}
