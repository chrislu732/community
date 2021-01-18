package com.example.community.controller;

import com.example.community.exception.WarningMessage;
import com.example.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SignUpController {
    @Autowired
    UserService userService;

    @GetMapping("/sign_up")
    public String getSignUp() {
        return "sign_up";
    }

    @PostMapping("/sign_up")
    public String postSignUp(@RequestParam(value = "username", required = false) String username,
                             @RequestParam(value = "pw", required = false) String pw,
                             @RequestParam(value = "re_pw", required = false) String pw2,
                             @RequestParam(value = "bio", required = false) String bio,
                             Model model) {
        // keep template information
        model.addAttribute("username", username);
        model.addAttribute("bio", bio);
        // verify the user name and password
        WarningMessage warningMessage = userService.userVerify(username, pw, pw2, bio);
        if (warningMessage != null) {
            model.addAttribute("error", warningMessage.getMessage());
            return "sign_up";
        }
        // create new user
        userService.createUser(username, pw, bio);
        return "redirect:/sign_in";
    }
}
