package com.example.community.controller;

import com.example.community.dto.CommentCreateDTO;
import com.example.community.dto.CommentDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.model.User;
import com.example.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// handle the comments
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResultDTO postComment(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request) {
        // get user information from the session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.USER_NOT_FOUND);
        }

        return commentService.commentPost(commentCreateDTO, user);
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public List<CommentDTO> postSubComment(@PathVariable(name = "id") Long id) {
        // get user information from the session
        return commentService.getCommentByTopic(id, CommentTypeEnum.COMMENT);
    }
}
