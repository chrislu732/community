package com.example.community.model;

import lombok.Data;

// user information from the database
@Data
public class User {
    private Integer id;
    private String name;
    private String bio;
    private String accountID;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
