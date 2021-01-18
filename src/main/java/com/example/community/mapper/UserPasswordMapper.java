package com.example.community.mapper;

import com.example.community.model.UserPassword;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserPasswordMapper {
    @Insert("insert into user_password (user_id, password) values (#{userId}, #{password})")
    void insert(UserPassword userPassword);

    @Select("select password from user_password where user_id = #{userId}")
    String getUserPassword(@Param("userId") Long userId);
}
