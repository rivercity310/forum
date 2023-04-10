package com.example.forum.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "userId"),
        @Index(columnList = "email"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserAccount extends AuditingFields implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false, length = 50, unique = true)
    private String userId;

    @Setter @Column(nullable = false)
    private String userPassword;

    @Setter @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Setter @Column(length = 100)
    private String nickname;

    @Setter
    private String memo;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

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
            String memo,
            String role
    ) {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                memo,
                null,
                role
        );
    }


    public static UserAccount of(
            String userId,
            String userPassword,
            String email,
            String nickname,
            String memo,
            String createdBy,
            String role
    ) {
        UserAccount userAccount = new UserAccount(
                userId,
                userPassword,
                email,
                nickname,
                memo,
                createdBy
        );

        userAccount.getRoles().add(role);
        return userAccount;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
