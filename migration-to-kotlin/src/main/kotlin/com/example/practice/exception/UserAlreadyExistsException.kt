package com.example.practice.exception

class UserAlreadyExistsException(val userId : String) : RuntimeException()