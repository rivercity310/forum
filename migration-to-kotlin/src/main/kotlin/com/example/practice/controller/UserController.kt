package com.example.practice.controller

import com.example.practice.dto.UserDto
import com.example.practice.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
class UserController(private val userService: UserService) {
    @PostMapping
    internal fun create(@RequestBody @Valid dto : UserDto.UserSignUp) : Long {
        return userService.create(dto)
    }
}