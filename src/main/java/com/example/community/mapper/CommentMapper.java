package com.example.community.mapper;

import com.example.community.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id, type, commentator, gmt_create, gmt_modified, content) values (#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified}, #{content})")
    void create(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment findByID(@Param("id") Long id);
}
