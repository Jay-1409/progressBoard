package com.example.progressBoard.controller;

import com.example.progressBoard.service.AdminService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.awt.image.RescaleOp;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/new-admin/{currentAdminMail}")
    public ResponseEntity<?> makeNewAdmin (@PathVariable("currentAdminMail") String currentAdminMail,@RequestParam String pass, @RequestParam String newAdminName, @RequestParam String newAdminEmail, @RequestParam String newAdminPass) {
        if(adminService.validateAdminLogin(currentAdminMail,pass)){
            try {
                adminService.createNewAdmin(newAdminName,newAdminEmail,newAdminPass);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
    @GetMapping("/admin-login/{adminMail}/{adminPass}")
    public ResponseEntity<?> adminLogin(@PathVariable("adminMail") String adminMail, @PathVariable("adminPass") String adminPass) {
        try {
             return new ResponseEntity<>(adminService.validateAdminLogin(adminMail,adminPass), HttpStatus.OK );
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/resetAllProgress")
    public ResponseEntity<?> resetProgress(){
        try {
            return new ResponseEntity<>(adminService.resetProgressForAllUsers(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
