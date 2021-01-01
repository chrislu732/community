package com.example.community.dto;

import lombok.Data;

// the header used to request access token
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;
}
