package com.example.community.dto;

import lombok.Data;

@Data
public class PageDTO {
    private Integer page;
    private Integer totalPage;
    private Integer offset;

    public void setProperties(Integer page, Integer size, Integer totalCount) {
        if (totalCount == 0) {
            totalPage = 1;
        }else if (totalCount % size == 0) {
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }
        // check if page is valid
        if (page < 1) {
            this.page = 1;
        }else if (page > totalPage) {
            this.page = totalPage;
        }else {
            this.page = page;
        }
        offset = size * (page - 1);
    }
}
