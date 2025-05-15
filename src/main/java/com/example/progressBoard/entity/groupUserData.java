package com.example.progressBoard.entity;

import org.bson.types.ObjectId;

public class groupUserData {
    String userName;
    double userProgress;
    String userId;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(double userProgress) {
        this.userProgress = userProgress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
