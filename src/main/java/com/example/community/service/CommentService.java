package com.example.community.service;

import com.example.community.dto.CommentDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.TopicMapper;
import com.example.community.model.Comment;
import com.example.community.model.Topic;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    TopicMapper topicMapper;

    // insert comment into the database
    public ResultDTO commentPost(CommentDTO commentDTO, User user) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
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
        if (comment.getContent() == null || comment.getContent().equals("")) {
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
}
