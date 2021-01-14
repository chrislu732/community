package com.example.community.controller;

import com.example.community.dto.CommentDTO;
import com.example.community.dto.TopicDTO;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.service.CommentService;
import com.example.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// topic detail
@Controller
public class TopicController {
    @Autowired
    TopicService topicService;
    @Autowired
    CommentService commentService;

    @GetMapping("/topic/{id}")
    public String topic(@PathVariable(name = "id") Long id,
                        Model model) {
        TopicDTO topicDTO = topicService.getTopicDTO(id);
        topicService.incViewCount(id);
        List<CommentDTO> commentDTOS = commentService.getCommentByTopic(id, CommentTypeEnum.TOPIC);
        List<TopicDTO> topicDTOS = topicService.getRelatedTopic(topicDTO);
        model.addAttribute("topic", topicDTO);
        model.addAttribute("comments", commentDTOS);
        model.addAttribute("relatedTopics", topicDTOS);
        return "topic";
    }
}
