package com.example.community.service;

import com.example.community.dto.PageDTO;
import com.example.community.dto.PaginationDTO;
import com.example.community.dto.TopicDTO;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.TopicMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Topic;
import com.example.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                throw new CustomizeException(CustomizeErrorCode.TOPIC_NOT_FOUND);
            }
        }
    }

    // get topic dto instance
    public TopicDTO getTopicDTO(Long id) {
        Topic topic = topicMapper.findByID(id);
        if (topic == null) {
            throw new CustomizeException(CustomizeErrorCode.TOPIC_NOT_FOUND);
        }
        User user = userMapper.findByID(topic.getAuthor());
        TopicDTO topicDTO = new TopicDTO();
        BeanUtils.copyProperties(topic, topicDTO);
        topicDTO.setUser(user);
        return topicDTO;
    }

    // get pagination for the website
    public PaginationDTO<TopicDTO> getPaginationDTO(Long id, Integer page, Integer size, String search) {
        PaginationDTO<TopicDTO> paginationDTO = new PaginationDTO<>();
        List<Long> topicIds;
        // no search
        if (StringUtils.isBlank(search)) {
            // get page  properties
            Integer totalCount = id == null ? topicMapper.count() : topicMapper.countByAuthor(id);
            PageDTO pageDTO = new PageDTO();
            pageDTO.setProperties(page, size, totalCount);
            // set properties for pagination dto
            paginationDTO.setPagination(pageDTO.getPage(), pageDTO.getTotalPage());
            // get topic dto
            topicIds = id == null ? topicMapper.list(pageDTO.getOffset(), size) : topicMapper.listByAuthor(id, pageDTO.getOffset(), size);
            // user is searching
        } else {
            // edit the titles for search
            String[] titlesArray = StringUtils.split(search);
            String titles = Arrays.stream(titlesArray)
                    .map(StringUtils::strip)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
            // get page  properties
            Integer totalCount = topicMapper.countBySearch(titles);
            PageDTO pageDTO = new PageDTO();
            pageDTO.setProperties(page, size, totalCount);
            // set properties for pagination dto
            paginationDTO.setPagination(pageDTO.getPage(), pageDTO.getTotalPage());
            // get topic dto
            topicIds = topicMapper.listBySearch(titles, pageDTO.getOffset(), size);
        }
        // get the list of topics
        List<TopicDTO> topicDTOS = topicIds.stream()
                .map(this::getTopicDTO)
                .collect(Collectors.toList());
        paginationDTO.setElementDTOs(topicDTOS);
        return paginationDTO;
    }

    // count the time of view
    public void incViewCount(Long id) {
        int updated = topicMapper.updateViewCount(id);
        if (updated == 0) {
            throw new CustomizeException(CustomizeErrorCode.TOPIC_NOT_FOUND);
        }
    }

    // get the list of related topics
    public List<TopicDTO> getRelatedTopic(TopicDTO topicDTO) {
        if (StringUtils.isBlank(topicDTO.getTag())) {
            return new ArrayList<>();
        }
        // edit the tags for search
        String[] tagsArray = StringUtils.split(topicDTO.getTag(), ',');
        String tags = Arrays.stream(tagsArray)
                .map(StringUtils::strip)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        List<Long> topicIds = topicMapper.findRelated(topicDTO.getId(), tags);
        List<TopicDTO> topicDTOS = topicIds.stream()
                .map(this::getTopicDTO)
                .collect(Collectors.toList());
        return topicDTOS;
    }
}
