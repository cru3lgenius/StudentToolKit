package com.example.cru3lgenius.studenttoolkit.Models;

/**
 * Created by denis on 3/16/17.
 */

public class User {

    private String email_id;
    private String name = "Unknown";
    private String gender = "Unknown";
    private int age;

    public User(String email_id){
        this.email_id = email_id;
    }
    public User(String email_id,String name,String gender,String email,int age){
        this.email_id = email_id;
        this.name = name;
        this.gender = gender;
        this.age = age;
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
