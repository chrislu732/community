package com.example.community.controller;

import com.example.community.dto.LikeCreateDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.model.User;
import com.example.community.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

// get like request
@RestController
public class LikeController {
    @Autowired
    LikeService likeService;

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public ResultDTO postComment(@RequestBody LikeCreateDTO likeCreateDTO,
                                 HttpServletRequest request) {
        // get user information from the session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.USER_NOT_FOUND);
        }
        return likeService.likePost(likeCreateDTO, user);
    }
}
