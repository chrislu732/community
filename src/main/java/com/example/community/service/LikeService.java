package com.example.community.service;

import com.example.community.dto.LikeCreateDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.LikeMapper;
import com.example.community.mapper.TopicMapper;
import com.example.community.model.Like;
import com.example.community.model.User;
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

    // check if like is valid and isnert it
    @Transactional
    public ResultDTO addLike(LikeCreateDTO likeCreateDTO, User user) {
        Like like = new Like();
        BeanUtils.copyProperties(likeCreateDTO, like);
        like.setUserId(user.getId());
        like.setGmtCreate(System.currentTimeMillis());
        List<Like> hasLike = likeMapper.findByEverything(like);
        // the same user cannot like the same topic/comment more than once
        if (hasLike != null && hasLike.size() > 0) {
            return ResultDTO.errorOf(CustomizeErrorCode.LIKE_MORE_THAN_ONCE);
        }
        // add the like into database
        likeMapper.create(like);
        Integer type = like.getType();
        // check if the type is valid
        if (!CommentTypeEnum.isExist(type)) {
            return ResultDTO.errorOf(CustomizeErrorCode.TYPE_NOT_FOUND);
        }
        // increase the like count
        if (type.equals(CommentTypeEnum.TOPIC.getType())) {
            topicMapper.updateLikeCount(like.getLiked());
        }else {
            commentMapper.updateLikeCount(like.getLiked());
        }
        return ResultDTO.okOf();
    }
}
