package com.example.community.schedule;


import com.example.community.cache.HotTopicCache;
import com.example.community.dto.TopicTitleDTO;
import com.example.community.mapper.TopicMapper;
import com.example.community.model.Topic;
import com.example.community.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTopicTask {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    TopicService topicService;
    @Autowired
    HotTopicCache hotTopicCache;

    // calculate the topic rank per hour
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void hotTopicSchedule() {
        log.info("hotTopicSchedule start");
        int top = 10; // the size of top topics
        int offset = 0;
        int size = 10;
        List<Long> ids = new ArrayList<>();
        PriorityQueue<TopicTitleDTO> priorityQueue = new PriorityQueue<>(top);
        for (; offset == 0 || ids.size() == size; offset += size) {
            ids = topicMapper.list(offset, size);
            for (Long id : ids) {
                Topic topic = topicMapper.findByID(id);
                // calculate the score of each topic for ranking
                Integer score = topic.getViewCount() + topic.getCommentCount() + topic.getLikeCount();
                // user priority queue to order the topics
                priorityQueue.offer(topicService.getTopicTitleDTO(id, score));
                if (priorityQueue.size() > top) {
                    priorityQueue.poll();
                }
            }
        }
        // put these topics into hot topic cache
        List<TopicTitleDTO> hotTopics = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            hotTopics.add(0, priorityQueue.poll());
        }
        hotTopicCache.setHotTopics(hotTopics);
    }
}
