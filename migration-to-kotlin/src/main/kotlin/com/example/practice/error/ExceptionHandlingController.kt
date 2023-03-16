package com.example.practice.error

import com.example.practice.exception.UserAlreadyExistsException
import com.example.practice.exception.UserNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlingController {
    private val log : Logger = LoggerFactory.getLogger(ExceptionHandlingController::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException) : ErrorResponse {
        log.error("Input value invalidation")
        val fieldErrors : List<ErrorResponse.FieldError> = getFieldErrors(e.bindingResult)
        return buildFieldErrors(ErrorCode.INPUT_VALUE_INVALID, fieldErrors)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleUserAlreadyExistsException(e: UserAlreadyExistsException) : ErrorResponse {
        val errorCode = ErrorCode.UID_DUPLICATION
        log.error("User ID Duplication : ${e.userId}")
        return buildError(errorCode)
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleUserNotFoundException(e: UserNotFoundException) : ErrorResponse {
        log.error("User Not Found : ${e.userId}")
        return buildError(ErrorCode.USER_NOT_FOUND)
    }

    private fun getFieldErrors(bindingResult: BindingResult) : List<ErrorResponse.FieldError> {
        val fieldErrors : List<FieldError> = bindingResult.fieldErrors
        return fieldErrors.parallelStream()
            .map { ErrorResponse.FieldError(
                field = it.field,
                value = it.rejectedValue as String,
                reason = it.defaultMessage!!
            ) }
            .toList()
    }

    private fun buildFieldErrors(errorCode: ErrorCode, fieldErrors: List<ErrorResponse.FieldError>) : ErrorResponse {
        return ErrorResponse(
            code = errorCode.code,
            status = errorCode.status,
            message = errorCode.message,
            errors = fieldErrors
        )
    }

    private fun buildError(errorCode: ErrorCode) : ErrorResponse {
        return ErrorResponse(
            code = errorCode.code,
            status = errorCode.status,
            message = errorCode.message
        )
    }
}