package com.example.community.enums;

public enum TypeEnum {
    TOPIC(1),
    COMMENT(2);

    private Integer type;

    TypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type) {
        for (TypeEnum typeEnum : TypeEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
