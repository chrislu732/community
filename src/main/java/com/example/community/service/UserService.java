package com.example.community.service;

import com.example.community.dto.GithubUser;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.helper.AvatarHelper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    AvatarHelper avatarHelper;
    @Autowired
    UserMapper userMapper;

    // create github user
    public String createOrUpdateUser(GithubUser githubUser) {
        String token;
        User existUser = userMapper.findByAccountId(String.valueOf(githubUser.getId()));
        if (existUser == null) {
            token = UUID.randomUUID().toString();
            User user = new User();
            user.setName(githubUser.getLogin());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setBio(githubUser.getBio());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);
        }else {
            token = existUser.getToken();
            existUser.setName(githubUser.getLogin());
            existUser.setGmtModified(System.currentTimeMillis());
            existUser.setBio(githubUser.getBio());
            existUser.setAvatarUrl(githubUser.getAvatarUrl());
            int updated = userMapper.update(existUser);
            if (updated == 0) {
                throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
            }
        }
        return token;
    }

    // create normal user
    public String createOrVerifyUser(String userName) {
        String token;
        User existUser = userMapper.findByName(userName);
        if (existUser == null) {
            token = UUID.randomUUID().toString();;
            User user = new User();
            user.setName(userName);
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(avatarHelper.randomAvatar());
            userMapper.insert(user);
        }else {
            token = existUser.getToken();
        }
        return token;
    }

    // update normal user
    public  String updateUser(Long id, String name, String avatarUrl, String bio) {
        User existUser = userMapper.findByID(id);
        if (existUser == null) return null;
        existUser.setName(name);
        existUser.setAvatarUrl(avatarUrl);
        existUser.setBio(bio);
        existUser.setGmtModified(System.currentTimeMillis());
        return existUser.getToken();
    }


}
