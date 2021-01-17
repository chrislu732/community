package com.example.community.controller;

import com.example.community.dto.CommentDTO;
import com.example.community.dto.TopicDTO;
import com.example.community.dto.TopicTitleDTO;
import com.example.community.enums.TypeEnum;
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
        List<CommentDTO> commentDTOS = commentService.getCommentByTopic(id, TypeEnum.TOPIC);
        List<TopicTitleDTO> topicTitleDTOS = topicService.getRelatedTopic(topicDTO, 10);
        model.addAttribute("topic", topicDTO);
        model.addAttribute("comments", commentDTOS);
        model.addAttribute("relatedTopics", topicTitleDTOS);
        return "topic";
    }
}
