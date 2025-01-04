package com.example.endure;

public class User {
    public String username;
    public String email;

    // Empty constructor required for Firebase
    public User() {}



    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
