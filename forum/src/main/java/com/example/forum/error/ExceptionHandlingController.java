package com.example.forum.error;

import com.example.forum.exception.UserAlreadyExistsException;
import com.example.forum.exception.UserNotFoundException;
import com.example.forum.exception.UserPasswordNotEqualsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlingController {
    private final Logger log = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("input value invalidation");
        List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildFieldErrors(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ErrorCode errorCode = ErrorCode.USER_ALREADY_EXISTS;
        log.error("User Id Duplication : " + e.getUserId());
        return buildError(errorCode);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;
        return buildError(errorCode);
    }

    @ExceptionHandler(UserPasswordNotEqualsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleUserPasswordNotEqualsException(UserPasswordNotEqualsException e) {
        ErrorCode errorCode = ErrorCode.USER_PASSWORD_FAIL;
        return buildError(errorCode);
    }

    private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.parallelStream()
                .map(err -> new ErrorResponse.FieldError(err.getField(), (String) err.getRejectedValue(), err.getDefaultMessage()
                ))
                .collect(Collectors.toList());
    }

    private ErrorResponse buildFieldErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> fieldErrors) {
        return new ErrorResponse(errorCode.getMessage(), errorCode.getCode(), errorCode.getStatus(), fieldErrors);
    }

    private ErrorResponse buildError(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getMessage(), errorCode.getCode(), errorCode.getStatus(), List.of());
    }
}
