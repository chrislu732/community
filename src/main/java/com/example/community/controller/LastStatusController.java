package com.example.community.controller;

import com.example.community.dto.LastStatusDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.exception.CustomizeErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

// save last page that the user browsed
@RestController
public class LastStatusController {
    @RequestMapping(value = "/last_status", method = RequestMethod.POST)
    public ResultDTO postLastStatus(@RequestBody LastStatusDTO lastStatusDTO,
                          HttpServletRequest request) {
        if (lastStatusDTO == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.SIGN_IN_ERROR);
        }
        if (StringUtils.isBlank(lastStatusDTO.getPreUrl())) {
            return ResultDTO.errorOf(CustomizeErrorCode.URL_ERROR);
        }
        // save the url into session
        request.getSession().setAttribute("preUrl", lastStatusDTO.getPreUrl());
        return ResultDTO.okOf();
    }
}
