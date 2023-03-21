package com.example.forum.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "email"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserAccount extends AuditingFields {
    @Id @Column(length = 50, nullable = false)
    private String userId;

    @Setter @Column(nullable = false)
    private String userPassword;
    @Setter @Column(length = 100, nullable = false, unique = true)
    private String email;
    @Setter @Column(length = 100)
    private String nickname;
    @Setter
    private String memo;

    private UserAccount(
            String userId,
            String userPassword,
            String email,
            String nickname,
            String memo,
            String createdBy
    ) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public static UserAccount of(
            String userId,
            String userPassword,
            String email,
            String nickname,
            String memo
    ) {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                memo,
                null
        );
    }

    public static UserAccount of(
            String userId,
            String userPassword,
            String email,
            String nickname,
            String memo,
            String createdBy
    ) {
        return new UserAccount(
                userId,
                userPassword,
                email,
                nickname,
                memo,
                createdBy
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
