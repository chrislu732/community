package com.example.community.service;

import com.example.community.dto.GithubUser;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.exception.WarningMessage;
import com.example.community.helper.AvatarHelper;
import com.example.community.mapper.UserMapper;
import com.example.community.mapper.UserPasswordMapper;
import com.example.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    AvatarHelper avatarHelper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserPasswordMapper userPasswordMapper;

    // create or update github user
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
            existUser.setGmtModified(System.currentTimeMillis());
            int updated = userMapper.update(existUser);
            if (updated == 0) {
                throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
            }
        }
        return token;
    }

    // verify normal user
    public WarningMessage verifyUser(String userName, String password) {
        // if there's no user name, submission fails
        if (StringUtils.isBlank(userName)) {
            return WarningMessage.NO_USER;
        }
        // if there's no password, submission fails
        if (StringUtils.isBlank(password)) {
            return WarningMessage.NO_PASSWORD;
        }
        // check if the user name is exist
        User existUser = userMapper.findByName(userName);
        if (existUser == null) {
            return WarningMessage.WRONG_USER_OR_PW;
        }
        // check the correctness of password
        Long userId = existUser.getId();
        String md5password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5password.equals(userPasswordMapper.getUserPassword(userId))) {
            return WarningMessage.WRONG_USER_OR_PW;
        }
        return null;
    }

    // get user token
    public String getUserToken(String userName) {
        User existUser = userMapper.findByName(userName);
        return existUser.getToken();
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
