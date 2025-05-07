package com.example.progressBoard.controller;

import com.example.progressBoard.entity.User;
import com.example.progressBoard.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> CreateUser(@RequestBody User newUser) {
        try{
            userService.createUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("id/{UUID}")
    public ResponseEntity<?> DeleteUser(@PathVariable ObjectId UUID) {
        userService.deleteUser(UUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/join-group/{userId}/{groupId}")
    public ResponseEntity<String> joinGroup(@PathVariable ObjectId userId, @PathVariable ObjectId groupId) {
        try {
            userService.adderUserToGroup_user(userId, groupId);
            return new ResponseEntity<>("User successfully added to the group.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add user to group: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    get tasks
}
