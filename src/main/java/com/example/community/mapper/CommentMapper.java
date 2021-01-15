package com.example.community.mapper;

import com.example.community.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id, type, commentator, gmt_create, gmt_modified, content) values (#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified}, #{content})")
    void create(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment findByID(@Param("id") Long id);

    @Select("select * from comment where parent_id = #{parentId} and type = #{type} order by gmt_create desc")
    List<Comment> findByParentAndType(@Param("parentId") Long parentId, @Param("type") Integer type);

    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    Integer updateCommentCount(@Param("id") Long id);
}
