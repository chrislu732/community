package com.example.community.mapper;

import com.example.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

// database operations

@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into community_user (name, account_id, token, gmt_create, gmt_modified) values (#{name}, #{accountID}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);

    @Select("select * from community_user where token = #{token}")
    User findByToken(@Param("token") String token);
}
