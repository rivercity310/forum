package com.example.forum.exception;

public class UserNotFoundException extends RuntimeException {
    private final String email;

    public UserNotFoundException(String email) {
        this.email = email;
    }
}
