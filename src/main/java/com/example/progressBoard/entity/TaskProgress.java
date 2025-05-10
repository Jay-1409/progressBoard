package com.example.progressBoard.entity;

import java.util.List;

public class TaskProgress {
    private List<Day.Task> tasks;
    private double progress;

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
}
