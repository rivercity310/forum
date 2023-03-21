package com.example.forum.service;

import com.example.forum.domain.UserAccount;
import com.example.forum.dto.UserDto;
import com.example.forum.exception.UserAlreadyExistsException;
import com.example.forum.exception.UserNotFoundException;
import com.example.forum.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
    private final UserAccountRepository userAccountRepository;

    public UserService(@Autowired UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public Long createUser(UserDto.SignUp dto) {
        if (userAccountRepository.findByUserId(dto.userId()).isPresent())
            throw new UserAlreadyExistsException(dto.userId());

        userAccountRepository.save(dto.toEntity());
        return 1L;
    }

    @Transactional(readOnly = true)
    public UserDto.UserGetRes getUser(String email) {
        UserAccount user = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return UserDto.UserGetRes.from(user);
    }
}
