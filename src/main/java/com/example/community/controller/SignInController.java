package com.example.community.controller;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class SignInController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }

    @PostMapping("/sign_in")
    public String doPublish(@RequestParam("user_name") String userName,
                            HttpServletResponse response,
                            Model model) {
        // keep template information
        model.addAttribute("userName", userName);

        // if there's no user name, submission fails
        if (userName == null || userName == "") {
            model.addAttribute("error", "no user name");
            return "sign_in";
        }

        String token;
        User tempUser = userMapper.findByName(userName);
        if (tempUser == null) {
            token = UUID.randomUUID().toString();
            User user = userService.createUser(userName, token);
            userMapper.insert(user);
        }else {
            token = tempUser.getToken();
        }
        // add a "token" cookie
        response.addCookie(new Cookie("token", token));

        // return main page
        return "redirect:/";
    }
}
