package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


// the page after signing in
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) throws Exception {
        // set the information of the oauth app
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);
        // get github access token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        // get user information
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            // put user information into the database
            String token;
            User tempUser = userMapper.findByAccountID(githubUser.getId());
            if (tempUser == null) {
                User user = new User();
                token = UUID.randomUUID().toString();
                user.setName(githubUser.getLogin());
                user.setAccountID(String.valueOf(githubUser.getId()));
                user.setToken(token);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setBio(githubUser.getBio());
                user.setAvatarUrl(githubUser.getAvatar_url());
                userMapper.insert(user);
            }else {
                token = tempUser.getToken();
            }
            // add a "token" cookie
            response.addCookie(new Cookie("token", token));
        }
        return "redirect:/";
    }

}