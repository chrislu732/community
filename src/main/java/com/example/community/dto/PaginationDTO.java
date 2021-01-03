package com.example.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<TopicDTO> topicDTOS;
    private boolean showPrevious;
    private boolean showNext;
    private boolean firstPage;
    private boolean lastPage;
    private Integer page;
    private List<Integer> pages;
    private Integer totalPage;

    public void setPagination(Integer page, Integer totalPage) {
        this.totalPage = totalPage;
        this.page = page;

        // set the limitations of page controller
        pages = new ArrayList<>();
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        showPrevious = !page.equals(1);
        showNext = !page.equals(totalPage);
        firstPage = !pages.contains(1);
        lastPage = !pages.contains(totalPage);
    }
}
