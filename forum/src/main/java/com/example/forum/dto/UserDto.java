package com.example.forum.dto;

import com.example.forum.domain.UserAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class UserDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUp {
        @NotNull private String userId;
        @NotNull @Setter private String userPassword;
        @Email private String email;
        @Setter private String nickname;
        @Setter private String memo;
        @Setter private String role;

        @Builder
        public SignUp(String userId, String userPassword, String email, String nickname, String memo, String role) {
            this.userId = userId;
            this.userPassword = userPassword;
            this.email = email;
            this.nickname = nickname;
            this.memo = memo;
            this.role = role;
        }

        public UserAccount toEntity() {
            return UserAccount.of(userId, userPassword, email, nickname, memo, role);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserGetReq {
        @NotEmpty private String userId;

        public UserGetReq(String userId, String role) {
            this.userId = userId;
        }
    }

    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserLoginReq {
        @NotEmpty private String userId;
        @NotEmpty private String password;
    }

    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChangeMemoReq {
        @NotEmpty private String userId;
        @NotEmpty private String memo;
    }

    @Getter
    public static class UserGetRes {
        private final String userId;
        private final String email;
        private final String nickname;
        private final String memo;

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
