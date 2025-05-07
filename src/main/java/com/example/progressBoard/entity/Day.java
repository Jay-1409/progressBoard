package com.example.progressBoard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
@Document(collection = "day-tasks")
public class Day {
    @Id
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public LocalDate getDateFor() {
        return dateFor;
    }

    public void setDateFor(LocalDate dateFor) {
        this.dateFor = dateFor;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    private ObjectId userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFor;
    private List<Task> tasks;
    public static class Task {
//        @Id
//        private ObjectId TaskId;
        private String title;
        private boolean completed;
        public Task() {}
        public Task(String title, boolean completed) {
            this.title = title;
            this.completed = completed;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public boolean isCompleted() {
            return completed;
        }
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
