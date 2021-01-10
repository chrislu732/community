package com.example.community.service;

import com.example.community.dto.PaginationDTO;
import com.example.community.dto.TopicDTO;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.TopicMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Topic;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// service for topics
@Service
public class TopicService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private UserMapper userMapper;

    //add or update topic
    public void addOrUpdateTopic(String title, String description, String tag, User user, Long id) {
        Topic topic;
        if (id == null) {
            topic = new Topic();
            topic.setTitle(title);
            topic.setDescription(description);
            topic.setTag(tag);
            topic.setAuthor(user.getId());
            topic.setGmtCreate(System.currentTimeMillis());
            topic.setGmtModified(topic.getGmtCreate());
            topicMapper.create(topic);
        }else {
            topic = topicMapper.findByID(id);
            topic.setTitle(title);
            topic.setDescription(description);
            topic.setTag(tag);
            topic.setGmtModified(System.currentTimeMillis());
            int updated = topicMapper.update(topic);
            if (updated == 0) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    // get topic dto instance
    public TopicDTO getTopicDTO(Long id) {
        Topic topic = topicMapper.findByID(id);
        if (topic == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findByID(topic.getAuthor());
        TopicDTO topicDTO = new TopicDTO();
        BeanUtils.copyProperties(topic, topicDTO);
        topicDTO.setUser(user);
        return topicDTO;
    }

    // get pagination dto
    public PaginationDTO getPaginationDTO(Integer page, Integer size) {
        // count the number of pages
        Integer totalCount = topicMapper.count();
        Integer totalPage;
        if (totalCount == 0) {
            totalPage = 1;
        }else if (totalCount % size == 0) {
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }
        // check if page is valid
        if (page < 1) {
            page = 1;
        }else if (page > totalPage) {
            page = totalPage;
        }
        // set properties for pagination dto
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(page, totalPage);
        // get topic dto
        Integer offset = size * (page - 1);
        List<TopicDTO> topicDTOS = new ArrayList<>();
        List<Topic> topics = topicMapper.list(offset, size);
        for (Topic topic : topics) {
            User user = userMapper.findByID(topic.getAuthor());
            TopicDTO topicDTO = new TopicDTO();
            BeanUtils.copyProperties(topic, topicDTO);
            topicDTO.setUser(user);
            topicDTOS.add(topicDTO);
        }
        paginationDTO.setTopicDTOS(topicDTOS);
        return paginationDTO;
    }

    public PaginationDTO getPaginationDTO(Long id, Integer page, Integer size) {
        // count the number of pages
        Integer totalCount = topicMapper.countById(id);
        Integer totalPage;
        if (totalCount == 0) {
            totalPage = 1;
        }else if (totalCount % size == 0) {
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }
        // check if page is valid
        if (page < 1) {
            page = 1;
        }else if (page > totalPage) {
            page = totalPage;
        }
        // set properties for pagination dto
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(page, totalPage);
        // get topic dto
        Integer offset = size * (page - 1);
        List<TopicDTO> topicDTOS = new ArrayList<>();
        List<Topic> topics = topicMapper.listById(id, offset, size);
        for (Topic topic : topics) {
            User user = userMapper.findByID(topic.getAuthor());
            TopicDTO topicDTO = new TopicDTO();
            BeanUtils.copyProperties(topic, topicDTO);
            topicDTO.setUser(user);
            topicDTOS.add(topicDTO);
        }
        paginationDTO.setTopicDTOS(topicDTOS);
        return paginationDTO;
    }

    public void incViewCount(Long id) {
        int updated = topicMapper.updateViewCount(id);
        if (updated == 0) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }
}
