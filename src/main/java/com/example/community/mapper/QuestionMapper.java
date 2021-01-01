package com.example.community.mapper;

import com.example.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, gmt_create, gmt_modified, author, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{author}, #{tag})")
    void create(Question question);

    @Select("select * from question")
    List<Question> list();

}
