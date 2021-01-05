package com.example.community.controller;

import com.example.community.dto.TopicDTO;
import com.example.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// topic detail
@Controller
public class TopicController {
    @Autowired
    TopicService topicService;

    @GetMapping("/topic/{id}")
    public String topic(@PathVariable(name = "id") Integer id,
                        Model model) {
        TopicDTO topicDTO = topicService.getTopicDTO(id);
        model.addAttribute("topic", topicDTO);
        return "topic";
    }
}