package com.example.community.controller;

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
    public String doPublish(@RequestParam("user_name") String userName,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            Model model) {
        // keep template information
        model.addAttribute("userName", userName);

        // if there's no user name, submission fails
        if (StringUtils.isBlank(userName)) {
            model.addAttribute("error", "no user name");
            return "sign_in";
        }

        // get or create token
        String token = userService.createOrVerifyUser(userName);
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
