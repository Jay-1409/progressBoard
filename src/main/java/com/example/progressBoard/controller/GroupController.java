package com.example.progressBoard.controller;

import com.example.progressBoard.entity.Group;
import com.example.progressBoard.entity.User;
import com.example.progressBoard.service.GroupService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @PostMapping("/create-group")
    private ResponseEntity<Group> NewGroup(@RequestBody Group newGroup) {
        try {
            groupService.createGroup(newGroup);
            System.out.println("Group created successfully: " + newGroup);
            return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Failed to create group: " + e.getMessage());
            return new ResponseEntity<>(newGroup, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("add-member/{userId}/{groupID}")
    public ResponseEntity<?> AddMember(@PathVariable("userId") ObjectId userID, @PathVariable("groupID") ObjectId groupID) {
        try {
            groupService.addMember(groupID, userID);
            System.out.println("User " + userID + " added to group " + groupID);
            return new ResponseEntity<>("User added to group", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Failed to add user " + userID + " to group " + groupID + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-all-group-data/{GroupId}")
    public ResponseEntity<?> GetAllData(@PathVariable("GroupId") ObjectId GroupId) {
        try {
            List<?> data = groupService.getAll(GroupId);
            System.out.println("Fetched group data for GroupId: " + GroupId);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Failed to fetch group data for GroupId " + GroupId + ": " + e.getMessage());
            return new ResponseEntity<>("Could not fetch members", HttpStatus.NO_CONTENT);
        }
    }

}
