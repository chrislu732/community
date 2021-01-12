package com.example.community.service;

import com.example.community.dto.CommentCreateDTO;
import com.example.community.dto.CommentDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.dto.TopicDTO;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.TopicMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Comment;
import com.example.community.model.Topic;
import com.example.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    UserMapper userMapper;

    // insert comment into the database
    public ResultDTO commentPost(CommentCreateDTO commentCreateDTO, User user) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        return insertComment(comment);
    }

    @Transactional
    public ResultDTO insertComment(Comment comment) {
        // check parent
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            return ResultDTO.errorOf(CustomizeErrorCode.PARENT_NOT_FOUND);
        }
        // check content
        if (StringUtils.isBlank(comment.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_NULL);
        }
        // check the type of the comment
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            return ResultDTO.errorOf(CustomizeErrorCode.TYPE_NOT_FOUND);
        }
        // comment of the topic
        if (comment.getType() == CommentTypeEnum.TOPIC.getType()) {
            Topic topic = topicMapper.findByID(comment.getParentId());
            // check if the topic is available
            if (topic == null) {
                return ResultDTO.errorOf(CustomizeErrorCode.TOPIC_NOT_FOUND);
            }
            commentMapper.create(comment);
            topicMapper.updateCommentCount(topic.getId());
        // comment of the other comment
        }else {
            Comment dbComment = commentMapper.findByID(comment.getParentId());
            // check if the comment is available
            if (dbComment == null) {
                return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.create(comment);
        }
        return null;
    }

    //list all comments under the topic
    public List<CommentDTO> getCommentByTopic(Long id) {
        //get all comments
        List<Comment> comments = commentMapper.findByParentAndType(id, 1);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // get all user and distinct them
        List<Long> userId = comments.stream()
                .map(comment -> comment.getCommentator())
                .distinct()
                .collect(Collectors.toList());
        // get all user id, then develop a map for users
        List<User> users = userMapper.findByIDList(userId);
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(user -> user.getId(), user -> user));
        // develop commentDTO
        List<CommentDTO> commentDTOS = comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    BeanUtils.copyProperties(comment, commentDTO);
                    commentDTO.setUser(userMap.get(comment.getCommentator()));
                    return commentDTO;
                }).collect(Collectors.toList());
        return commentDTOS;
    }
}
