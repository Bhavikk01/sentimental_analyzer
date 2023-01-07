package com.example.sentimental_analyzer.models;

public class UserModel {
    private final String userName;
    private final String userEmail;
    private final String uid;
    private final String userPhoto;

    public UserModel(String userName, String userEmail, String uid, String userPhoto) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.uid = uid;
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUid() {
        return uid;
    }

    public String getUserPhoto() {
        return userPhoto;
    }
}