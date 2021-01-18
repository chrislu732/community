package com.example.community.mapper;

import com.example.community.model.Like;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LikeMapper {
    @Insert("insert into like_list (user_id, parent_id, type, gmt_create) values (#{userId}, #{parentId}, #{type}, #{gmtCreate})")
    void create(Like like);

    @Select("select * from like_list where user_id = #{userId} and parent_id = #{parentId} and type = #{type}")
    Like findByEverything(Like like);
}
