package com.example.forum.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private final String userId;

    public UserAlreadyExistsException(String userId) {
        this.userId = userId;
    }
}
