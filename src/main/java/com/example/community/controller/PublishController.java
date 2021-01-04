package com.example.community.controller;

import com.example.community.model.User;
import com.example.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private TopicService topicService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model) {
        // keep template information
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        // if there's no title name, submission fails
        if (title == null || title == "") {
            model.addAttribute("error", "no title name");
            return "publish";
        }
        // if there's no description, submission fails
        if (description == null || description == "") {
            model.addAttribute("error", "no description");
            return "publish";
        }
        // if there's no tag, submission fails
        if (tag == null || tag == "") {
            model.addAttribute("error", "no tag");
            return "publish";
        }
        // get user information from the session
        User user = (User) request.getSession().getAttribute("user");
        // if there's no user, submission fails
        if (user == null) {
            model.addAttribute("error", "user doesn't log in");
            return "publish";
        }
        // add topic information into the database
        topicService.addTopic(title, description, tag, user);
        // return main page
        return "redirect:/";
    }
}
