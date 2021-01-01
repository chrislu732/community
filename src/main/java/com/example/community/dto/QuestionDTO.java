package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

// connect question and user
@Data
public class QuestionDTO {
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
    private User user;
}
