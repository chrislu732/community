package com.example.community.controller;

import com.example.community.dto.PaginationDTO;
import com.example.community.helper.AvatarHelper;
import com.example.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// main page
@Controller
public class IndexController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private AvatarHelper avatarHelper;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        // collect topic list from service
        PaginationDTO pagination = topicService.getPaginationDTO(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }

}
