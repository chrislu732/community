package com.example.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// main page
@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

}
