package com.example.community.dto;

import lombok.Data;

// the topic dto used to show on the page and redirect
@Data
public class TopicTitleDTO implements Comparable {
    private Long topicId;
    private String title;
    private Integer score;

    @Override
    public int compareTo(Object o) {
        return this.getScore() - ((TopicTitleDTO) o).getScore();
    }
}
