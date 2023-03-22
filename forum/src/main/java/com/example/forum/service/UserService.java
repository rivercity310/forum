package com.example.forum.service;

import com.example.forum.domain.UserAccount;
import com.example.forum.dto.UserAccountDto;
import com.example.forum.dto.UserDto;
import com.example.forum.exception.UserAlreadyExistsException;
import com.example.forum.exception.UserNotFoundException;
import com.example.forum.exception.UserPasswordNotEqualsException;
import com.example.forum.jwt.JwtTokenProvider;
import com.example.forum.jwt.TokenInfo;
import com.example.forum.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserAccountRepository userAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public Long createUser(UserDto.SignUp dto) {
        if (userAccountRepository.findByUserId(dto.getUserId()).isPresent())
            throw new UserAlreadyExistsException(dto.getUserId());

        String hashpw = BCrypt.hashpw(dto.getUserPassword(), BCrypt.gensalt());
        dto.setUserPassword(hashpw);

        userAccountRepository.save(dto.toEntity());
        return 1L;
    }

    @Transactional(readOnly = true)
    public UserDto.UserGetRes getUser(String userId) {
        UserAccount user = userAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        System.out.println(user.getUserPassword());
        return UserDto.UserGetRes.from(user);
    }

    public TokenInfo login(String userId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UserAccount userAccount = userAccountRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UserAccountDto dto = UserAccountDto.from(userAccount);

        if (!BCrypt.checkpw(password, dto.userPassword()))
            throw new UserPasswordNotEqualsException(password);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userId, dto.userPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 jwt 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }
}
