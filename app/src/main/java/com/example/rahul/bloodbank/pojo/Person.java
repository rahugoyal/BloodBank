package com.example.rahul.bloodbank.pojo;

/**
 * Created by Rahul Goyal on 11/18/2016.
 */

public class Person {

    private String username;
    private String password;

    public Person(String username, String password) {
        this.username= username;
        this.password = password;
    }

    public Person(){

    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String address) {
        this.password = password;
    }
}
