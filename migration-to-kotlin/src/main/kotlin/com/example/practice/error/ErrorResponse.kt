package com.example.practice.error


data class ErrorResponse(
    val message : String,
    val code : String,
    val status : Int,
    val errors : List<FieldError> = mutableListOf()
) {
    data class FieldError(
        val field : String,
        val value : String,
        val reason : String
    )
}