package com.example.community.controller;

import com.example.community.dto.PaginationDTO;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

// profile page
@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request,
                          Model model) {
        // get user information from the session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        // get the section of the profile
        if ("topics".equals(action)) {
            // topics section
            model.addAttribute("section", "topics");
            model.addAttribute("sectionName", "My Topics");
        }else if ("comments".equals(action)) {
            // comments section
            model.addAttribute("section", "comments");
            model.addAttribute("sectionName", "My Comments");
        }

        // collect question list from service
        PaginationDTO pagination = questionService.getPaginationDTO(user.getId(), page, size);
        model.addAttribute("pagination", pagination);
        return "profile";
    }
}
