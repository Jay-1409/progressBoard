package com.example.progressBoard.controller;

import com.example.progressBoard.service.AuthService;
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
}
