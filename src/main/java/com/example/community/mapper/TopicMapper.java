package com.example.community.mapper;

import com.example.community.model.Topic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TopicMapper {
    @Insert("insert into topic (title, description, gmt_create, gmt_modified, author, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{author}, #{tag})")
    void create(Topic topic);

    @Select("select * from topic limit #{offset}, #{size}")
    List<Topic> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from topic where author = #{id} limit #{offset}, #{size}")
    List<Topic> listById(@Param("id") Integer id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from topic")
    Integer count();

    @Select("select count(1) from topic where author = #{id}")
    Integer countById(@Param("id") Integer id);

}
