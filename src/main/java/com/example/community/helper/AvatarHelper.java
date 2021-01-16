package com.example.community.helper;

import org.springframework.stereotype.Component;

import java.util.Random;

// get random avatar local url
@Component
public class AvatarHelper {

    public String randomAvatar() {
        Random random = new Random();
        String path = "/avatars";
        return path + "/" + (random.nextInt(20) + 1) + ".png";
    }



}
