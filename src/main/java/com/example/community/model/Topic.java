package com.example.community.model;

import lombok.Data;

// topic information from database
@Data
public class Topic {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long author;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}
