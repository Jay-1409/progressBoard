package com.example.progressBoard.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private String email;
    private List<ObjectId> groupIds = new ArrayList<>();
    private List<ObjectId> DayIds = new ArrayList<>();
    private double todayProgress;
    private List<Double> previousProgress;

    public List<Double> getPreviousProgress() {
        return previousProgress;
    }

    public void setPreviousProgress(List<Double> previousProgress) {
        this.previousProgress = previousProgress;
    }

    public double getTodayProgress() {
        return todayProgress;
    }

    public void setTodayProgress(double todayProgress) {
        this.todayProgress = todayProgress;
    }

    // object day, which contain the tasks for that day, the object id then stored into a list in the user
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ObjectId> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<ObjectId> groupIds) {
        this.groupIds = groupIds;
    }

    public List<ObjectId> getDayIds() {
        return DayIds;
    }

    public void setDayIds(List<ObjectId> taskIds) {
        this.DayIds = taskIds;
    }
}
