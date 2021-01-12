package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import com.example.community.provider.GithubProvider;
import com.example.community.service.GithubService;
import com.example.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// the page after signing in
@Controller
public class AuthorizeController {
    @Autowired
    private GithubService githubService;
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpServletRequest request) throws Exception {
        // set the information of the oauth app
        AccessTokenDTO accessTokenDTO = githubService.getAccessTokenDTO(code, state);
        // get github access token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        // get user information
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            // put user information into the database
            String token = userService.createOrUpdateUser(githubUser);
            // add a "token" cookie
            response.addCookie(new Cookie("token", token));
        }

        // check the last page before signing in
        String preUrl = (String) request.getSession().getAttribute("preUrl");
        if (preUrl != null) {
            return "redirect:" + preUrl;
        }

        return "redirect:/";
    }

}