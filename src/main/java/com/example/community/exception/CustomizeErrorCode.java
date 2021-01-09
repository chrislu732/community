package com.example.community.exception;

// custom error code
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("Question doesn't exist"),
    USER_NOT_FOUND("User doesn't exist");

    private String message;
    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
