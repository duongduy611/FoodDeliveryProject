package com.example.foodappprm.Models;

public class User {
    private String fullNameText;
    private String emailText;
    private String passwordText;

    public User() {
    }

    public User(String fullNameText, String emailText, String passwordText) {
        this.fullNameText = fullNameText;
        this.emailText = emailText;
        this.passwordText = passwordText;
    }

    public User(String fullnameText, String emailText) {
    }
}
