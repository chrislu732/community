package com.example.community.exception;

// custom error code
public enum CustomizeErrorCode {
    TOPIC_NOT_FOUND(2001, "Topic doesn't exist"),
    PARENT_NOT_FOUND(2002, "No topic or comment found"),
    USER_NOT_FOUND(2003, "User doesn't exist or hasn't sign in"),
    SYSTEM_ERROR(2004, "Unexpected Error"),
    TYPE_NOT_FOUND(2005, "Comment type is invalid"),
    COMMENT_NOT_FOUND(2006, "Comment doesn't exist"),
    CONTENT_IS_NULL(2007, "Content cannot be null"),
    SIGN_IN_ERROR(2008, "sign in failed"),
    URL_ERROR(2009, "wrong url");

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
