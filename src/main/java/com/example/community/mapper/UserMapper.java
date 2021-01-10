package com.example.community.mapper;

import com.example.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

// database operations

@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into community_user (name, account_id, token, gmt_create, gmt_modified, bio, avatar_url) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{bio}, #{avatarUrl})")
    void insert(User user);

    @Update("update community_user set name = #{name}, gmt_modified = #{gmtModified}, bio = #{bio}, avatar_url = #{avatarUrl} where id = #{id}")
    int update(User user);

    @Select("select * from community_user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from community_user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Select("select * from community_user where name = #{name}")
    User findByName(@Param("name") String name);

    @Select("select * from community_user where id = #{id}")
    User findByID(@Param("id") Long id);
}
