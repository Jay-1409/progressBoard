package com.example.progressBoard.service;

import com.example.progressBoard.entity.Day;
import com.example.progressBoard.entity.User;
import com.example.progressBoard.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupService groupService;
    public void createUser(User newUser) {
        userRepository.save(newUser);
    }
    public void deleteUser(ObjectId UUID) {
        userRepository.deleteById(UUID);
    }
    public void addTaskToUser(ObjectId UUID, ObjectId TaskId) {
        Optional<User> usr = userRepository.findById(UUID);
        if(usr.isPresent()) {
            User activeUser = usr.get();
            activeUser.getDayIds().add(TaskId);
            userRepository.save(activeUser);
        }
    }

    public void adderUserToGroup_user(ObjectId UUID, ObjectId GUID) {
        Optional<User> usr = userRepository.findById(UUID);
        if(usr.isPresent()) {
            User activeUsr = usr.get();
            activeUsr.getGroupIds().add(GUID);
            userRepository.save(activeUsr);
            this.groupService.addMember(GUID, UUID);
        } else {
            System.out.println("COULD NOT FIND THE USER : " + UUID);
        }
    }
    public String getUserName(ObjectId userId) {
        Optional<User> reqUsr = userRepository.findById(userId);
        if(reqUsr.isPresent()){
            User activeUsr = reqUsr.get();
            return activeUsr.getUsername();
        }
        return "No user name found!";
    }
    public int calculateStreak(ObjectId userId) {
        Optional<User> reqUser = userRepository.findById(userId);
        if(reqUser.get()) {
            User activeUser = reqUser.get();
            List<Double> progress = activeUser.getPreviousProgress();
            int n = progress.toArray().length;
            int streak = 0;
            for (int i = progress.size() - 1; i >= 0; i--) {
                if (progress.get(i) == 100.0) {
                    streak++;
                } else {
                    break;
                }
            }
            return streak;
        }
        System.out.println("USER NOT FOUND: " + userId);
        return -1;
    }
}
