package com.example.forum.dto;

import com.example.forum.domain.UserAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SignUp {
        @NotNull private String userId;
        @NotNull private String userPassword;
        @Email private String email;
        private String nickname;
        private String memo;

        public static SignUp of(String userId, String userPassword, String email, String nickname, String memo) {
            return new SignUp(userId, userPassword, email, nickname, memo);
        }

        public UserAccount toEntity() {
            return UserAccount.of(userId, userPassword, email, nickname, memo);
        }
    }
}
