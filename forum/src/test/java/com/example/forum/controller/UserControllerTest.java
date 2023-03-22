package com.example.forum.controller;

import com.example.forum.config.SecurityConfig;
import com.example.forum.jwt.JwtTokenProvider;
import com.example.forum.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private UserService userService;
    @MockBean private JwtTokenProvider jwtTokenProvider;

    @Test
    public void createTest() throws Exception {
        // given
        Map<String, String> info = new LinkedHashMap<>();
        info.put("userId", "111") ;
        info.put("userPassword", "222");
        info.put("email", "h970126@gmail.com");
        info.put("nickname", "asdasd");
        info.put("memo", "asdasd");

        given(userService.createUser(any())).willReturn(1L);

        // when & then
        mvc.perform(post("/user/create")
                .content(objectMapper.writeValueAsString(info))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        then(userService).should().createUser(any());
    }

    @Test
    public void getUserTest() throws Exception {
        // given
        Map<String, String> info = new LinkedHashMap<>();
        info.put("userId", "h970126");

        given(userService.getUser(any())).willReturn(any());

        // when & then
        mvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(info))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + anyString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        then(userService).should().getUser(any());
    }
}