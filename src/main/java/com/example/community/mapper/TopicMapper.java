package com.example.community.mapper;

import com.example.community.model.Topic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TopicMapper {
    @Insert("insert into topic (title, description, gmt_create, gmt_modified, author, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{author}, #{tag})")
    void create(Topic topic);

    @Update("update topic set title = #{title}, description = #{description}, tag = #{tag}, gmt_modified = #{gmtModified} where id = #{id}")
    int update(Topic topic);

    @Update("update topic set view_count = view_count + 1 where id = #{id}")
    int updateViewCount(@Param("id") Long id);

    @Update("update topic set comment_count = comment_count + 1 where id = #{id}")
    int updateCommentCount(@Param("id") Long id);

    @Select("select * from topic where id = #{id}")
    Topic findByID(@Param("id") Long id);

    @Select("select id from topic limit #{offset}, #{size}")
    List<Long> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select id from topic where author = #{id} limit #{offset}, #{size}")
    List<Long> listById(@Param("id") Long id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from topic")
    Integer count();

    @Select("select count(1) from topic where author = #{id}")
    Integer countById(@Param("id") Long id);

}
