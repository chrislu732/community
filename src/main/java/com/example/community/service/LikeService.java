package com.example.community.service;

import com.example.community.dto.LikeCreateDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.enums.NotificationTypeEnum;
import com.example.community.enums.TypeEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.LikeMapper;
import com.example.community.mapper.TopicMapper;
import com.example.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    NotificationService notificationService;

    // check if like is valid and insert it
    public ResultDTO likePost(LikeCreateDTO likeCreateDTO, User user) {
        Like like = new Like();
        BeanUtils.copyProperties(likeCreateDTO, like);
        like.setUserId(user.getId());
        like.setGmtCreate(System.currentTimeMillis());
        return insertLike(like, user);
    }

    @Transactional
    public ResultDTO insertLike(Like like ,User user) {
        List<Like> hasLike = likeMapper.findByEverything(like);
        // the same user cannot like the same topic/comment more than once
        if (hasLike != null && hasLike.size() > 0) {
            return ResultDTO.errorOf(CustomizeErrorCode.LIKE_MORE_THAN_ONCE);
        }
        // check other properties
        if (like.getParentId() == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.PARENT_NOT_FOUND);
        }
        // check if the type is valid
        Integer type = like.getType();
        if (!TypeEnum.isExist(type)) {
            return ResultDTO.errorOf(CustomizeErrorCode.TYPE_NOT_FOUND);
        }
        // increase the like count
        if (type.equals(TypeEnum.TOPIC.getType())) {
            Topic topic = topicMapper.findByID(like.getParentId());
            // check if the topic is available
            if (topic == null) {
                return ResultDTO.errorOf(CustomizeErrorCode.TOPIC_NOT_FOUND);
            }
            // add the like into database
            likeMapper.create(like);
            // update the like count
            topicMapper.updateLikeCount(like.getParentId());
            // create new notification
            notificationService.createNotification(user, topic.getAuthor(), topic, NotificationTypeEnum.LIKE_TOPIC.getType());
        }else {
            Comment comment = commentMapper.findByID(like.getParentId());
            // check if the comment is available
            if (comment == null) {
                return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Topic topic = topicMapper.findByID(comment.getParentId());
            // check if the topic is available
            if (topic == null) {
                return ResultDTO.errorOf(CustomizeErrorCode.TOPIC_NOT_FOUND);
            }
            // add the like into database
            likeMapper.create(like);
            // update like count
            commentMapper.updateLikeCount(like.getParentId());
            // create new notification
            notificationService.createNotification(user, comment.getCommentator(), topic, NotificationTypeEnum.LIKE_COMMENT.getType());

        }
        return ResultDTO.okOf();
    }
}
