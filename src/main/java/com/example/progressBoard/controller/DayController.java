package com.example.progressBoard.controller;

import com.example.progressBoard.entity.Day;
import com.example.progressBoard.service.DayService;
import com.example.progressBoard.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/task")
public class DayController {
    @Autowired
    private DayService dayService;
    @PostMapping("/add-task/{userId}")
    private ResponseEntity<?> addDay(@PathVariable("userId") ObjectId userId, @RequestBody Day newDay) {
//        System.out.println("Received request to add tasks for userId: " + userId);
//        System.out.println("Request Body (newDay): " + newDay.getTasks());
//        System.out.println("Request Body (newDay): " + newDay.getDateFor());
//        System.out.println("Request Body (newDay): " + newDay.getUserId());
        try {
            System.out.println("Adding tasks for user " + userId + ": " + newDay);
            dayService.addDay(userId, newDay);
            System.out.println("Task added successfully for user: " + userId + " | Task: " + newDay);
            return new ResponseEntity<>(newDay, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Failed to add task for user " + userId + ": " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Could not add the daily tasks", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-user-tasks/{userId}")
    private ResponseEntity<?> getUserTasks(@PathVariable("userId") ObjectId userId) {
        try {
            System.out.println("Fetching tasks for user: " + userId);
            return new ResponseEntity<>(dayService.getTasks(userId), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Failed to get tasks for user " + userId + ": " + e.getMessage());
            return new ResponseEntity<>("Could not get user task data", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-user-tasks-date/{userId}")
    private ResponseEntity<?> getUserTasks(@PathVariable("userId") ObjectId userId, @RequestBody LocalDate reqDate) {
        try {
            System.out.println("Fetching tasks for user: " + userId);
            return new ResponseEntity<>(dayService.getTasksByDate(userId, reqDate), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Failed to get tasks for user " + userId + ": " + e.getMessage());
            return new ResponseEntity<>("Could not get user task data", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update-user-tasks/{userId}")
    private ResponseEntity<?> updateUserTasks(@PathVariable("userId") ObjectId userId, @RequestBody Day updatedDay) {
        try {
            dayService.updateDay(userId, updatedDay);
            System.out.println("Updated task for user: " + userId + " | Updated data: " + updatedDay);
            return new ResponseEntity<Day>(updatedDay, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Failed to update task for user " + userId + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/is-today-present/{userId}")
    private ResponseEntity<?> isTodayPresent(@PathVariable("userId") ObjectId userId) {
        try {
            return new ResponseEntity<>(dayService.doesTodayExists(userId), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Failed to check today for user " + userId + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
