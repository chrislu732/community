package com.example.community.service;

import com.example.community.dto.PaginationDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// service for questions
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    // get pagination dto
    public PaginationDTO getPaginationDTO(Integer page, Integer size) {
        // count the number of pages
        Integer totalCount = questionMapper.count();
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }
        // check if page is valid
        if (page < 1) {
            page = 1;
        }else if (page > totalPage) {
            page = totalPage;
        }
        // set properties for pagination dto
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(page, totalPage);
        // get question dto
        Integer offset = size * (page - 1);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questions = questionMapper.list(offset, size);
        for (Question question : questions) {
            User user = userMapper.findByID(question.getAuthor());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        return paginationDTO;
    }
}
