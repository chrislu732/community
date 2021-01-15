package com.example.community.mapper;

import com.example.community.model.Notification;
import com.example.community.model.Topic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NotificationMapper {
    @Insert("insert into notification (notifier, receiver, outer_id, type, gmt_create, notifier_name, outer_title) values (#{notifier}, #{receiver}, #{outerId}, #{type}, #{gmtCreate}, #{notifierName}, #{outerTitle})")
    void create(Notification notification);

    @Update("update notification set status = #{status} where id = #{id}")
    Integer updateStatus(Notification notification);

    @Select("select * from notification where id = #{id}")
    Notification findByID(@Param("id") Long id);

    @Select("select id from notification where receiver = #{receiver} order by gmt_create desc limit #{offset}, #{size}")
    List<Long> ListByReceiver(@Param("receiver") Long receiver, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from notification where receiver = #{receiver}")
    Integer countByReceiver(@Param("receiver") Long receiver);

    @Select("select count(1) from notification where receiver = #{receiver} and status = #{status}")
    Integer countUnreadByReceiver(@Param("receiver") Long receiver, @Param("status") Integer status);
}
