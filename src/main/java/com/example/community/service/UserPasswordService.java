package com.example.community.service;

import com.example.community.helper.MD5Helper;
import com.example.community.mapper.UserPasswordMapper;
import com.example.community.model.UserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordService {
    @Autowired
    UserPasswordMapper userPasswordMapper;
    @Autowired
    MD5Helper md5Helper;

    // check the correctness of password
    public boolean isPasswordCorrect(Long userId, String password) {
        if (userId == null) {
            return false;
        }
        String md5password = md5Helper.getMd5(password);
        return md5password.equals(userPasswordMapper.getUserPassword(userId));
    }

    // create new user
    public void addNewUser(Long userId, String password) {
        String md5password = md5Helper.getMd5(password);
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(userId);
        userPassword.setPassword(md5password);
        userPasswordMapper.insert(userPassword);
    }
}
