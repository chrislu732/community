package com.example.community.dto;

import lombok.Data;

// user information from github
@Data
public class GithubUser {
    private String login;
    private Long id;
    private String bio;
    private String avatarUrl;
}
