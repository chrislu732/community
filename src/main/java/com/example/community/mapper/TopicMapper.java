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
    Integer update(Topic topic);

    @Update("update topic set view_count = view_count + 1 where id = #{id}")
    Integer updateViewCount(@Param("id") Long id);

    @Update("update topic set comment_count = comment_count + 1 where id = #{id}")
    Integer updateCommentCount(@Param("id") Long id);

    @Update("update topic set like_count = like_count + 1 where id = #{id}")
    Integer updateLikeCount(@Param("id") Long id);

    @Select("select * from topic where id = #{id}")
    Topic findByID(@Param("id") Long id);

    @Select("select id from topic order by view_count desc, id limit #{offset}, #{size}")
    List<Long> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select id from topic where author = #{author} order by view_count desc, id limit #{offset}, #{size}")
    List<Long> listByAuthor(@Param("author") Long author, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select id from topic where title regexp #{titles} order by view_count desc, id limit #{offset}, #{size}")
    List<Long> listBySearch(@Param("titles") String titles, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from topic")
    Integer count();

    @Select("select count(1) from topic where author = #{author}")
    Integer countByAuthor(@Param("author") Long author);

    @Select("select count(1) from topic where title regexp #{titles}")
    Integer countBySearch(@Param("titles") String titles);

    @Select("select id from topic where id != #{id} and tag regexp #{tags} limit #{size}")
    List<Long> findRelated(@Param("id") Long id, @Param("tags") String tags, @Param("size") Integer size);
}
