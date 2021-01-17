package com.example.community.service;

import com.example.community.dto.NotificationDTO;
import com.example.community.dto.PageDTO;
import com.example.community.dto.PaginationDTO;
import com.example.community.enums.NotificationStatusEnum;
import com.example.community.enums.NotificationTypeEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.NotificationMapper;
import com.example.community.model.Notification;
import com.example.community.model.Topic;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;

    // create new notification
    public void createNotification(User user, Long receiver, Topic topic, Integer type) {
        Notification notification = new Notification();
        notification.setNotifier(user.getId());
        notification.setNotifierName(user.getName());
        notification.setReceiver(receiver);
        notification.setOuterId(topic.getId());
        notification.setOuterTitle(topic.getTitle());
        notification.setType(type);
        notification.setGmtCreate(System.currentTimeMillis());
        notificationMapper.create(notification);
    }

    // get notification dto instance
    public NotificationDTO getNotificationDTO(Long id) {
        Notification notification = notificationMapper.findByID(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }

    // get pagination for the website
    public PaginationDTO<NotificationDTO> getPaginationDTO(Long id, Integer page, Integer size) {
        // count the number of pages
        Integer totalCount = notificationMapper.countByReceiver(id);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setProperties(page, size, totalCount);
        // set properties for pagination dto
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        paginationDTO.setPagination(pageDTO.getPage(), pageDTO.getTotalPage());
        // get notification dto
        List<Long> notificationIds = notificationMapper.ListByReceiver(id, pageDTO.getOffset(), size);
        List<NotificationDTO> notificationDTOS = notificationIds.stream()
                .map(this::getNotificationDTO)
                .collect(Collectors.toList());
        paginationDTO.setElementDTOs(notificationDTOS);
        return paginationDTO;
    }

    // count the number of unread notification
    public Integer unReadCount(Long id) {
        return notificationMapper.countUnreadByReceiver(id, NotificationStatusEnum.UNREAD.getStatus());
    }

    // update the status of notification
    public Long read(Long id, User user) {
        Notification notification = notificationMapper.findByID(id);
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.UNMATCHED_USER);
        }
        if (!NotificationTypeEnum.isExist(notification.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_NOT_FOUND);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateStatus(notification);
        return notification.getOuterId();
    }


}
