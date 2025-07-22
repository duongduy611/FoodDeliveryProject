package com.example.foodappprm.Models;

public class User {
    private String fullNameText;
    private String emailText;
    private String passwordText;
    private String role;

    // Empty constructor required for Firebase
    public User() {
    }

    public User(String fullNameText, String emailText, String passwordText) {
        this.fullNameText = fullNameText;
        this.emailText = emailText;
        this.passwordText = passwordText;
    }

    public User(String fullNameText, String emailText) {
        this.fullNameText = fullNameText;
        this.emailText = emailText;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullNameText() {
        return fullNameText;
    }

    public void setFullNameText(String fullNameText) {
        this.fullNameText = fullNameText;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
    }
}
