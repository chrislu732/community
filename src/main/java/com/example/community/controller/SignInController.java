package com.example.community.controller;

import com.example.community.exception.WarningMessage;
import com.example.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInController {
    @Autowired
    UserService userService;

    @GetMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }

    @PostMapping("/sign_in")
    public String doPublish(@RequestParam(value = "user_name", required = false) String userName,
                            @RequestParam(value = "user_pw", required = false) String password,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            Model model) {
        // keep template information
        model.addAttribute("userName", userName);

        // verify the user name and password
        WarningMessage warningMessage = userService.userVerify(userName, password);
        if (warningMessage != null) {
            model.addAttribute("error", warningMessage.getMessage());
            return "/sign_in";
        }

        // get or create token
        String token = userService.getUserToken(userName);
        // add a "token" cookie
        response.addCookie(new Cookie("token", token));

        // check the last page before signing in
        String preUrl = (String) request.getSession().getAttribute("preUrl");
        if (preUrl != null) {
            return "redirect:" + preUrl;
        }

        // return main page
        return "redirect:/";
    }
}
