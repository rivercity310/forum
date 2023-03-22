package com.example.forum.controller;

import com.example.forum.dto.UserDto;
import com.example.forum.jwt.TokenInfo;
import com.example.forum.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto.UserGetRes getUser(@Valid @RequestBody UserDto.UserGetReq dto) {
        System.out.println(dto.getUserId());
        return userService.getUser(dto.getUserId());
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createUser(@Valid @RequestBody UserDto.SignUp dto) {
        return new ResponseEntity<Long>(userService.createUser(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody UserDto.UserLoginReq dto) {
        String userId = dto.getUserId();
        String password = dto.getPassword();
        TokenInfo tokenInfo = userService.login(userId, password);
        return tokenInfo;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
