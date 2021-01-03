package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

// connect topic and user
@Data
public class TopicDTO {
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
