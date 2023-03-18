package com.example.practice.controller

import com.example.practice.common.PageRequest
import com.example.practice.dto.UserDto
import com.example.practice.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
class UserController(private val userService: UserService) {
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    internal fun createUser(@RequestBody @Valid dto : UserDto.SignUp) : Long {
        return userService.createUser(dto)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    internal fun findUser(@RequestParam("userId") userId : String) : UserDto.Res {
        return userService.findById(userId)
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    internal fun findUsers(pageRequest: PageRequest) : Page<UserDto.Res> {
        return userService.findUsers(pageRequest.of())
    }
}