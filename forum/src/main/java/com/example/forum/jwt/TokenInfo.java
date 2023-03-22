package com.example.forum.jwt;

import lombok.*;

@Getter @Setter
@Builder
@RequiredArgsConstructor
public class TokenInfo {
    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
}
