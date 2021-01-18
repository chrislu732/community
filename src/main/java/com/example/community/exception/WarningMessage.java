package com.example.community.exception;

public enum WarningMessage {
    NO_USER("user name is empty"),
    NO_PASSWORD("password is empty"),
    GUEST("user doesn't sign in"),
    WRONG_USER_OR_PW("user name or password is incorrect"),
    UNMATCHED_PASSWORD("password is unmatched"),
    DUPLICATED_USER("username is exist"),
    NO_TITLE("title is empty"),
    NO_DESCRIPTION("description is empty"),
    NO_TAG("tag is empty");

    private String message;

    WarningMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
