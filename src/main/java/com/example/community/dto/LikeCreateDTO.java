package com.example.community.dto;

import lombok.Data;

//like information from the client
@Data
public class LikeCreateDTO {
    private Long liked;
    private Integer type;
}
