package com.example.practice.error

enum class ErrorCode(
    val code : String,
    val message : String,
    val status : Int
) {
    INPUT_VALUE_INVALID("???", "입력값이 올바르지 않습니다", 400),
    UID_DUPLICATION("???", "중복된 유저 아이디입니다", 400),
    USER_NOT_FOUND("???", "해당 유저를 찾을 수 없습니다", 400)
}