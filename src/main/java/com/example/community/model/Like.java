package com.example.community.model;

import lombok.Data;

@Data
public class Like {
    private Long userId;
    private Long liked;
    private Integer type;
    private Long gmtCreate;
}
