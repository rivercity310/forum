package com.example.forum.dto;

import com.example.forum.domain.UserAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class UserDto {

    public record SignUp(@NotNull String userId, @NotNull String userPassword, @Email String email, String nickname, String memo) {
        @Builder
        public SignUp(String userId, String userPassword, String email, String nickname, String memo) {
            this.userId = userId;
            this.userPassword = userPassword;
            this.email = email;
            this.nickname = nickname;
            this.memo = memo;
        }

        public UserAccount toEntity() {
            return UserAccount.of(userId, userPassword, email, nickname, memo);
        }
    }

    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserGetReq {
        @Email private String email;

        public UserGetReq(String email) { this.email = email; }
    }

    public record UserGetRes(String userId, String email, String nickname, String memo) {
        @Builder
        public UserGetRes(String userId, String email, String nickname, String memo) {
            this.userId = userId;
            this.email = email;
            this.nickname = nickname;
            this.memo = memo;
        }

        public static UserGetRes from(UserAccount entity) {
            return new UserGetRes(
                    entity.getUserId(),
                    entity.getEmail(),
                    entity.getNickname(),
                    entity.getMemo()
            );
        }
    }
}
