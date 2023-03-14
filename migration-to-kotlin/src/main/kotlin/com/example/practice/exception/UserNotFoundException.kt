package com.example.practice.exception

class UserNotFoundException(private val userId : String) : RuntimeException()