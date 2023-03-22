package com.example.forum.exception;

import lombok.Getter;

@Getter
public class UserPasswordNotEqualsException extends RuntimeException {
    private String userPassword;

    public UserPasswordNotEqualsException(String userPassword) {
        this.userPassword = userPassword;
    }
}
