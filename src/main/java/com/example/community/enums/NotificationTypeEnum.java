package com.example.community.enums;

public enum NotificationTypeEnum {
    REPLY_TOPIC(1, "reply your topic"),
    REPLY_COMMENT(2, "reply your comment"),
    LIKE_TOPIC(3, "like your topic"),
    LIKE_COMMENT(4, "like your comment");

    private Integer type;
    private String name;

    NotificationTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String nameOfType(Integer type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType().equals(type)) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }

    public static boolean isExist(Integer type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
