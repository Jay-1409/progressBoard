package com.example.progressBoard.entity;

import org.bson.types.ObjectId;

import java.util.List;

public class TaskProgress {
    private List<Day.Task> tasks;
    private double progress;
    private ObjectId userId;
    public TaskProgress(List<Day.Task> tasks, double progress) {
        this.tasks = tasks;
        this.progress = progress;
    }

    public List<Day.Task> getTasks() {
        return tasks;
    }

    public double getProgress() {
        return progress;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }
}
