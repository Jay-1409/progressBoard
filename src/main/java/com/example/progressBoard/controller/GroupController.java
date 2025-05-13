package com.example.progressBoard.controller;

import com.example.progressBoard.entity.Group;
import com.example.progressBoard.entity.User;
import com.example.progressBoard.entity.groupUserData;
import com.example.progressBoard.service.AuthService;
import com.example.progressBoard.service.GroupService;
import com.example.progressBoard.service.UserService;
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
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @PostMapping("/create-group/{userId}")
    private ResponseEntity<String> NewGroup(@PathVariable("userId") ObjectId userId, @RequestBody Group newGroup) {
        try {
            groupService.createGroup(newGroup);
            System.out.println("Group created successfully: " + newGroup);
            ObjectId groupId = newGroup.getId();
            userService.adderUserToGroup_user(userId, groupId);
            return new ResponseEntity<>(groupId.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Failed to create group: " + e.getMessage());
            return new ResponseEntity<>("Failed to create group", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("add-member/{userId}/{groupID}")
    public ResponseEntity<?> AddMember(@PathVariable("userId") ObjectId userID, @PathVariable("groupID") ObjectId groupID, @RequestParam String inPsas) {
        try {
//            ObjectId oI = new ObjectId(id);
            if(authService.validateGroupJoin(groupID, inPsas)) {
                userService.adderUserToGroup_user(userID, groupID);
                System.out.println("User " + userID + " added to group " + groupID);
            } else {
                System.out.println("User input password does not match");
                return new ResponseEntity<>("Action Done!", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Action Done!", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Failed to add user " + userID + " to group " + groupID + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//    @DeleteMapping("delete-member/{userId}/{groupId}")
//    public
    @GetMapping("/get-all-group-members/{GroupId}")
    public ResponseEntity<?> GetAllData(@PathVariable("GroupId") ObjectId GroupId) {
        try {
            List<?> data = groupService.getAllMem(GroupId);
            System.out.println("Fetched group data for GroupId: " + GroupId);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Failed to fetch group data for GroupId " + GroupId + ": " + e.getMessage());
            return new ResponseEntity<>("Could not fetch members", HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/get-user-groups/{UserId}")
    public ResponseEntity<?> getAllUserGroups(@PathVariable("UserId") ObjectId userId) {
        try {
            List<?> data = groupService.fetchUsersAllGroups(userId);
            System.out.println("Fetched Groups for UserId" + userId);
//            System.out.println(data);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch(Exception e){
            System.err.println("Failed to fetch group data for UserId " + userId + ": " + e.getMessage());
            return new ResponseEntity<>("Could not fetch members", HttpStatus.NO_CONTENT);
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteGroups(@RequestParam ObjectId userId, @RequestParam ObjectId groupId) {
        try {
            groupService.deleteGroupForUser(userId, groupId);
            return ResponseEntity.ok("Group successfully deleted for the user.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting group: " + e.getMessage());
        }
    }
    @GetMapping("/getUsers_and_progress")
    public ResponseEntity<?> getUserProgress(@RequestParam ObjectId groupId) {
        try {
            List<groupUserData> groupUserData = groupService.getAllMembersAndProgress(groupId);
            return new ResponseEntity<>(groupUserData, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error deleting group: " + e.getMessage());        }
    }
}
