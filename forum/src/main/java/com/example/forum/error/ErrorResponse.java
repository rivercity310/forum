package com.example.forum.error;


import java.util.List;

public record ErrorResponse(String message, String code, int status, List<FieldError> errors) {

    public record FieldError(String field, String value, String reason) {
    }
}
