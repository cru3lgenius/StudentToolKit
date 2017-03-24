package com.example.cru3lgenius.studenttoolkit.Activities.Authentication_Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cru3lgenius.studenttoolkit.Main.Session;
import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.Models.User;
import com.example.cru3lgenius.studenttoolkit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.UUID;

public class SignIn extends AppCompatActivity {

    private Button signIn;
    Gson gson = new Gson();
    private TextView register;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth auth;
    private EditText emailLogin,passLogin;
    private ProgressDialog progressDialog;
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        layout = (RelativeLayout) findViewById(R.id.activity_sign_in);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
        signIn = (Button) findViewById(R.id.btnSignIn);
        register = (TextView)findViewById(R.id.tvSignIn);
        emailLogin = (EditText) findViewById(R.id.etEmailLogin);
        passLogin =  (EditText) findViewById(R.id.etPasswordLogin);
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        System.out.println("STAVA SI");

        if(auth.getCurrentUser()!=null){
            ref.child("users").child(auth.getCurrentUser().getEmail().replace('.','_').toString()).
                    addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  String version = UUID.randomUUID().toString();
                  User currUser =  new User(version,auth.getCurrentUser().getEmail().replace('.','_'));
                  String user_name = (String) dataSnapshot.child("name").getValue();
                  long user_age =  (long)dataSnapshot.child("age").getValue();
                  String user_gender = (String)dataSnapshot.child("gender").getValue();
                  currUser.setAge((int)user_age);
                  currUser.setGender(user_gender);
                  currUser.setName(user_name);
                  loadMain(currUser);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_login();
            }


        });
    }
    private void user_login() {
        String email = emailLogin.getText().toString();
        String pass = passLogin.getText().toString();

        if(TextUtils.isEmpty(email) ||TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(),"No empty fields are allowed!",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Signing in ....");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email,pass).
                addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), TabsActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Signing in was unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /* Hides the keyboard by clicking somewhere */
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void loadMain(User user){
        System.out.println("KRISKO");
        Session session = new Session(user);
        String jsonSession = gson.toJson(session);
        startActivity(new Intent(getApplicationContext(),TabsActivity.class).putExtra("jsonSession", jsonSession));
        finish();
    }

}
