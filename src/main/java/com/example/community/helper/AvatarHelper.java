package com.example.community.helper;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AvatarHelper {

    public String randomAvatar() {
        Random random = new Random();
        String path = "/image";
        return path + "/" + (random.nextInt(20) + 1) + ".png";
    }



}
