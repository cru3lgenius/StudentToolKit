package com.example.cru3lgenius.studenttoolkit.Activities.Authentication_Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.cru3lgenius.studenttoolkit.Main.TabsActivity;
import com.example.cru3lgenius.studenttoolkit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class SignIn extends AppCompatActivity {

    private Button signIn;
    private TextView register;
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
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), TabsActivity.class));
            finish();
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

}
