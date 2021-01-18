package com.example.community.helper;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MD5Helper {
    private final String salt = "fwef87wef78wey";

    public String getMd5(String message) {
        String base = message + '/' + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
