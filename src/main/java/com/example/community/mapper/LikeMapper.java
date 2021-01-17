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
    @Insert("insert into like_list (user_id, liked, type, gmt_create) values (#{userId}, #{liked}, #{type}, #{gmtCreate})")
    void create(Like like);

    @Select("select * from like_list where user_id = #{userId} and liked = #{liked} and type = #{type}")
    List<Like> findByEverything(Like like);
}
