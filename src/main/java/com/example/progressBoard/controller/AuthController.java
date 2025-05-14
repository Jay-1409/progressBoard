package com.example.progressBoard.controller;

import com.example.progressBoard.service.AuthService;
import com.example.progressBoard.service.EmailService;
import com.example.progressBoard.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @GetMapping("/validate")
    public ResponseEntity<?> checkUser(@RequestParam String email, @RequestParam String inPass) {
        try {
            return new ResponseEntity<> (authService.validateUser(email , inPass), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/obId")
    public ResponseEntity<?> getId(@RequestParam String email, @RequestParam String inPass) {
        try {
            return new ResponseEntity<>(authService.getUserObId(email, inPass).toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    @Autowired
    EmailService emailService;
    @PostMapping("/Send-Otp-Mail")
    public ResponseEntity<?> sendOtp(@RequestParam String to) {
        try {
            return new ResponseEntity<>(emailService.sendOtpMail(to), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/validate-email")
    public ResponseEntity<?> getEmailValidation(@RequestParam String receiverMail, @RequestParam int enteredOtp) {
        try {
            return new ResponseEntity<>(authService.validateOtp(receiverMail, enteredOtp), HttpStatus.OK);
        } catch(Exception e)  {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
