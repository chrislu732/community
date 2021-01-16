package com.example.community.controller;

import com.example.community.dto.NotificationDTO;
import com.example.community.dto.PaginationDTO;
import com.example.community.dto.TopicDTO;
import com.example.community.model.User;
import com.example.community.service.NotificationService;
import com.example.community.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// profile page
@Controller
@Slf4j
public class ProfileController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model) {
        // get user information from the session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            log.error("fail to get user");
            return "redirect:/";
        }

        // get the section of the profile
        if ("topics".equals(action)) {
            // topics section
            model.addAttribute("section", "topics");
            model.addAttribute("sectionName", "My Topics");
            // collect topic list from service
            PaginationDTO<TopicDTO> pagination = topicService.getPaginationDTO(user.getId(), page, size, "");
            model.addAttribute("pagination", pagination);
        }else if ("comments".equals(action)) {
            // comments section
            model.addAttribute("section", "comments");
            model.addAttribute("sectionName", "My Comments");
            // collect notification list from service
            PaginationDTO<NotificationDTO> pagination = notificationService.getPaginationDTO(user.getId(), page, size);
            model.addAttribute("pagination", pagination);
        }else if ("sign_out".equals(action)) {
            // delete sessions and cookies
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            request.getSession().removeAttribute("user");
            String preUrl = (String) request.getSession().getAttribute("preUrl");
            if (preUrl != null) {
                return "redirect:" + preUrl;
            }
            log.error("unexpected action");
            return "redirect:/";
        }
        return "profile";
    }
}
