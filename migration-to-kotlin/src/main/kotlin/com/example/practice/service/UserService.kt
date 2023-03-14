package com.example.practice.service

import com.example.practice.dto.UserDto
import com.example.practice.exception.UserAlreadyExistsException
import com.example.practice.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(private val userRepository: UserRepository) {
    internal fun create(dto : UserDto.UserSignUp) : Long {
        if (userRepository.findByUserId(dto.userId) != null)
            throw UserAlreadyExistsException(dto.userId)

        userRepository.save(dto.toEntity())
        return 1L
    }
}