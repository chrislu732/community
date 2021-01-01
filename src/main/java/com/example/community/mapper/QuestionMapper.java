package com.example.community.mapper;

import com.example.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, gmt_create, gmt_modified, author, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{author}, #{tag})")
    void create(Question question);
}
