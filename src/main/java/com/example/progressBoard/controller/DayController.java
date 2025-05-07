package com.example.progressBoard.controller;

import com.example.progressBoard.entity.Day;
import com.example.progressBoard.service.DayService;
import com.example.progressBoard.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class DayController {
    @Autowired
    private DayService dayService;
    @PostMapping("/add-task/{userId}")
    private ResponseEntity<?> addDay(@PathVariable("userId") ObjectId userId, @RequestBody Day newDay) {
        try {
            dayService.addDay(userId, newDay);
            return new ResponseEntity<>(newDay, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not add the daily tasks", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-user-tasks/{userId}")
    private ResponseEntity<?> getUserTasks(@PathVariable("userId") ObjectId userId) {
        try {
            return new ResponseEntity<>(dayService.getTasks(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not get user task data", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update-user-tasks/{userId}")
    private ResponseEntity<?> updateUserTasks(@PathVariable("userId") ObjectId userId, @RequestBody Day updatedDay) {
        try {
            dayService.updateDay(userId,updatedDay);
            return new ResponseEntity<Day>(updatedDay ,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
