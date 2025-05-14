package com.example.progressBoard.service;

import com.example.progressBoard.entity.Admin;
import com.example.progressBoard.entity.User;
import com.example.progressBoard.repository.AdminRepository;
import com.example.progressBoard.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    public boolean validate(ObjectId adminId) {
        return true;
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public boolean resetProgressForAllUsers() {
        List<User> allUsers = userRepository.findAll();
        for(User user : allUsers){
            user.setTodayProgress(0);
        }
        userRepository.saveAll(allUsers);
        return true;
    }
    public boolean createNewAdmin(String userName, String adminEmail, String password) {
        Optional<Admin> reqAdmin = adminRepository.findByEmail(adminEmail);
        if(reqAdmin.isPresent()) return true;
        else {
            Admin newAdmin = new Admin();
            newAdmin.setEmail(adminEmail);
            newAdmin.setPassword(password);
            try {
                adminRepository.save(newAdmin);
            } catch (Exception e){
                return false;
            }
        }
        return true;
    }
    public boolean validateAdminLogin(String adminEmail, String Password) {
        Optional<Admin> reqAdmin = adminRepository.findByEmail(adminEmail);
        if(reqAdmin.isPresent()){
            Admin activeAdmin = reqAdmin.get();
            if(activeAdmin.getPassword().equals(Password)){
                return true;
            } else {
                return false;
            }
        } return false;
    }
}
