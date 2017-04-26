package com.example.cru3lgenius.studenttoolkit.Main;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cru3lgenius.studenttoolkit.Models.Flashcard;
import com.example.cru3lgenius.studenttoolkit.Models.Note;
import com.example.cru3lgenius.studenttoolkit.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

/**
 * Created by denis on 3/26/17.
 */
public class Session {
    private Context ctx;
    static Gson gson = new Gson();
    final private String USER_PREFERENCES_KEY;
    static final private String USER_KEY = "user";
    static private SharedPreferences preferences;
    static private SharedPreferences.Editor editor;
    private FirebaseAuth auth = FirebaseAuth.getInstance();


    public Session(Context ctx){

        this.ctx = ctx;
        USER_PREFERENCES_KEY = auth.getCurrentUser().getEmail().replace('.','_');
        preferences = this.ctx.getSharedPreferences(USER_PREFERENCES_KEY,Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    /* Converts user to json and stores it as shared preferences */
    public void storeUser(User user){

        String jsonUser = gson.toJson(user);
        editor.putString(USER_KEY,jsonUser);
        editor.commit();

    }

    /* Loads the user from shared preferences */
    public User retrieveUser(){

        /* Parsing jsonString to User Object */
        String jsonUser = preferences.getString(USER_KEY,"default");
        User user = new User();
        if(!jsonUser.equals("default")){
            user = gson.fromJson(jsonUser,User.class);
        }
        return user;
    }

}

