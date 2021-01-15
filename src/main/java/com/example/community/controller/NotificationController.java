package com.example.community.controller;

import com.example.community.model.User;
import com.example.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

// handle the get request of notification
@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String getNotification(@PathVariable(name = "id") Long id,
                                  HttpServletRequest request) {
        // get user information from the session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        // get the topic id
        Long outerId = notificationService.read(id, user);
        return "redirect:/topic/" + outerId;
    }
}
