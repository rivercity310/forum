package com.example.forum.error;

import lombok.Getter;

import java.util.List;

@Getter
public record ErrorResponse(String message, String code, int status, List<FieldError> errors) {

    @Getter
    public record FieldError(String field, String value, String reason) {
    }
}
