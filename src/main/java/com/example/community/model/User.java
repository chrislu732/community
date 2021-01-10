package com.example.community.model;

import lombok.Data;

// user information from the database
@Data
public class User {
    private Long id;
    private String name;
    private String bio;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
