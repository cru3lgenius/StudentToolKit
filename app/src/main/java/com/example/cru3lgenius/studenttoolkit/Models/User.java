package com.example.cru3lgenius.studenttoolkit.Models;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;

/**
 * Created by denis on 3/16/17.
 */

public class User  {

    private String email_id;
    private String name = "Unknown";
    private String gender = "Unknown";
    private int age = 0;
    private String version = " Unknown";

    /* Different constructors for the user */
    public User(){
    }
    public User(String email_id,String version){
        this.email_id = email_id;
        this.version = version;
    }
    public User(String email_id,String name,String gender,String email,int age,String version){
        this.email_id = email_id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.version = version;
     }

    /* Setter and getter methods for the User */
    public String getVersion(){
        return this.version;
    }
    public void setVersion(String ver){
        this.version = ver;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
