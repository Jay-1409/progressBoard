package com.example.progressBoard.controller;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/health")
public class HealthCheck{
    @GetMapping()
    public String health(){
        return "System is healthy";
    }
}
