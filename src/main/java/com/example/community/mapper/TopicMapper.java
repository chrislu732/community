package com.example.community.mapper;

import com.example.community.model.Topic;
import com.example.community.model.User;
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

    @Select("select * from topic where id = #{id}")
    Topic findByID(@Param("id") Integer id);

    @Select("select * from topic limit #{offset}, #{size}")
    List<Topic> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from topic where author = #{id} limit #{offset}, #{size}")
    List<Topic> listById(@Param("id") Integer id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from topic")
    Integer count();

    @Select("select count(1) from topic where author = #{id}")
    Integer countById(@Param("id") Integer id);

}
