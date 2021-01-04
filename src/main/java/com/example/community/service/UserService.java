package com.example.community.service;

import com.example.community.dto.GithubUser;
import com.example.community.helper.AvatarHelper;
import com.example.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    AvatarHelper avatarHelper;

    public User createUser(String token, GithubUser githubUser) {
        User user = new User();
        user.setName(githubUser.getLogin());
        user.setAccountID(String.valueOf(githubUser.getId()));
        user.setToken(token);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setBio(githubUser.getBio());
        user.setAvatarUrl(githubUser.getAvatarUrl());
        return user;
    }

    public User createUser(String userName, String token) {
        User user = new User();
        user.setName(userName);
        user.setToken(token);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setAvatarUrl(avatarHelper.randomAvatar());
        return user;
    }

}
