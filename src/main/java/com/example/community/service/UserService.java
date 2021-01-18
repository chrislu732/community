package com.example.community.service;

import com.example.community.dto.GithubUser;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.exception.WarningMessage;
import com.example.community.helper.AvatarHelper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    AvatarHelper avatarHelper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserPasswordService userPasswordService;

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

    // verify normal user for sign in
    public WarningMessage userVerify(String userName, String password) {
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
        if (!userPasswordService.isPasswordCorrect(userId, password)) {
            return WarningMessage.WRONG_USER_OR_PW;
        }
        return null;
    }

    // get normal user token
    public String getUserToken(String userName) {
        User existUser = userMapper.findByName(userName);
        return existUser.getToken();
    }

    // verify normal user for sign up
    public WarningMessage userVerify(String userName, String pw, String pw2, String bio) {
        // if there's no user name, submission fails
        if (StringUtils.isBlank(userName)) {
            return WarningMessage.NO_USER;
        }
        // if there's no password, submission fails
        if (StringUtils.isBlank(pw) || StringUtils.isBlank(pw2)) {
            return WarningMessage.NO_PASSWORD;
        }
        // check if the passwords match each other
        if (!pw.equals(pw2)) {
            return WarningMessage.UNMATCHED_PASSWORD;
        }
        // check if the user name is exist
        if (userMapper.findByName(userName) != null) {
            return WarningMessage.DUPLICATED_USER;
        }
        return null;
    }

    // create new normal user
    @Transactional
    public void createUser(String userName, String pw, String bio) {
        User user = new User();
        user.setName(userName);
        user.setToken(UUID.randomUUID().toString());
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setAvatarUrl(avatarHelper.randomAvatar());
        if (!StringUtils.isBlank(bio)) {
            user.setBio(bio);
        }
        userMapper.insert(user);
        User tempUser = userMapper.findByName(userName);
        userPasswordService.addNewUser(tempUser.getId(), pw);
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
