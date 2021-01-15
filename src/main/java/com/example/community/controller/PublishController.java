package com.example.community.controller;

import com.example.community.dto.TopicDTO;
import com.example.community.helper.TagHelper;
import com.example.community.model.User;
import com.example.community.service.TopicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class PublishController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private TagHelper tagHelper;

    // new topic
    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", tagHelper.getTags());
        return "publish";
    }

    // edit topic
    @GetMapping("/publish/{id}")
    public String topicEdit(@PathVariable(name = "id") Long id,
                            Model model) {
        TopicDTO topicDTO = topicService.getTopicDTO(id);
        model.addAttribute("title", topicDTO.getTitle());
        model.addAttribute("description", topicDTO.getDescription());
        model.addAttribute("tag", topicDTO.getTag());
        model.addAttribute("id", topicDTO.getId());
        model.addAttribute("tags", tagHelper.getTags());
        return "publish";
    }

    //insert topic to database
    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title", required = false) String title,
                            @RequestParam(name = "description", required = false) String description,
                            @RequestParam(name = "tag", required = false) String tag,
                            @RequestParam(name = "id", required = false) Long id,
                            HttpServletRequest request,
                            Model model) {
        // keep template information
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tagHelper.getTags());

        // if there's no title name, submission fails
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "no title name");
            return "publish";
        }
        // if there's no description, submission fails
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "no description");
            return "publish";
        }
        // if there's no tag, submission fails
        if (StringUtils.isBlank(tag)) {
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
        topicService.addOrUpdateTopic(title, description, tag, user, id);
        // return main page
        return "redirect:/";
    }
}
