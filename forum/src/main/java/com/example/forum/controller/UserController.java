package com.example.forum.controller;

import com.example.forum.dto.UserDto;
import com.example.forum.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto.UserGetRes getUser(@Valid @RequestBody UserDto.UserGetReq dto) {
        System.out.println(dto.getEmail());
        return userService.getUser(dto.getEmail());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Long createUser(@Valid @RequestBody UserDto.SignUp dto) {
        return userService.createUser(dto);
    }
}
