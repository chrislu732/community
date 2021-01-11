package com.example.community.controller;

import com.example.community.dto.CommentDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.model.User;
import com.example.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

// handle the comments
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResultDTO post(@RequestBody CommentDTO commentDTO,
                             HttpServletRequest request) {
        // get user information from the session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.USER_NOT_FOUND);
        }

        ResultDTO resultDTO = commentService.commentPost(commentDTO, user);
        return resultDTO == null ? ResultDTO.okOf() : resultDTO;
    }
}
