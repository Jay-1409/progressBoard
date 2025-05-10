package com.example.progressBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {
    @GetMapping("/")
    public String homePage() {
        return "index"; // loads notice.html
    }
    @GetMapping("/forms")
    public String formsPage() {return "forms";}
}
