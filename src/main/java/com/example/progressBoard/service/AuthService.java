package com.example.progressBoard.service;

import com.example.progressBoard.entity.Group;
import com.example.progressBoard.entity.User;
import com.example.progressBoard.repository.GroupRepository;
import com.example.progressBoard.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.Optional;
@Component
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    public boolean validateUser(String email, String inPass) {
        Optional<User> getUsr = userRepository.findByEmail(email);
        if(getUsr.isPresent()) {
            User activeUser = getUsr.get();
            String reqPass = activeUser.getPassword();
            System.out.println("Correct pass: " + reqPass + " Pass provided " + inPass);
            return reqPass.equals(inPass);
        }
        System.out.println("User not found: " + email);
        return false;
    }
    public ObjectId getUserObId(String email, String inPass) {
        Optional<User> getUsr = userRepository.findByEmail(email);
        if(getUsr.isPresent()) {
            User activeUser = getUsr.get();
            return activeUser.getId();
        }
        System.out.println("User not found: " + email);
        return null;
    }
    public boolean validateGroupJoin(ObjectId groupId, String inPass) {
        Optional<Group> getGrp = groupRepository.findById(groupId);
        if(getGrp.isPresent()){
            Group activGroup = getGrp.get();
            String reqPass = activGroup.getGroupPass();
            System.out.println("Adding user to group: " + groupId + "Entered pass: " + inPass + " Req Pass: " + reqPass);
            return reqPass.equals(inPass);
        }
        return false;
    }
    private final Map<String, Integer> otpStore = new ConcurrentHashMap<>();
    public int generateOtp(String email) {
        int otp = (int)(Math.random() * 9000) + 1000;
        otpStore.put(email, otp);
        return otp;
    }
    public boolean validateOtp(String email, int otp) {
        Integer storedOtp = otpStore.get(email);
        if (storedOtp != null && storedOtp == otp) {
            otpStore.remove(email);
            return true;
        }
        return false;
    }
}
