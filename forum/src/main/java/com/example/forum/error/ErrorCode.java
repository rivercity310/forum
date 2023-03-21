package com.example.forum.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_ALREADY_EXISTS("???", "이미 존재하는 이메일입니다", 400),
    USER_NOT_FOUND("???", "존재하지 않는 유저입니다", 400),
    INPUT_VALUE_INVALID("???", "필드 유효성 검사에 실패하였습니다", 400)
    ;

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
