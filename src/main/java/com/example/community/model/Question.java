package com.example.community.model;

import lombok.Data;

// question information from database
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer author;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}
