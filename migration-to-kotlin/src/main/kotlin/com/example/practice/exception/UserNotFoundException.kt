package com.example.practice.exception

class UserNotFoundException(val userId : String) : RuntimeException()