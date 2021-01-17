package com.example.community.cache;

import com.example.community.dto.TopicTitleDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


// the cache used to record the current rank of topics
@Component
@Data
public class HotTopicCache {
    private List<TopicTitleDTO> hotTopics = new ArrayList<>();
}
