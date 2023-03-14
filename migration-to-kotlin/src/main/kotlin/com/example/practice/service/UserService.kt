package com.example.practice.service

import com.example.practice.dto.UserDto
import com.example.practice.exception.UserAlreadyExistsException
import com.example.practice.exception.UserNotFoundException
import com.example.practice.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(private val userRepository: UserRepository) {
    internal fun createUser(dto : UserDto.SignUp) : Long {
        if (userRepository.findByUserId(dto.userId) != null)
            throw UserAlreadyExistsException(dto.userId)

        userRepository.save(dto.toEntity())
        return 1L
    }

    @Transactional(readOnly = true)
    internal fun findById(userId : String) : UserDto.Res {
        val user = userRepository.findByUserId(userId) ?: throw UserNotFoundException(userId)
        return UserDto.Res(user)
    }

    @Transactional(readOnly = true)
    internal fun findUsers(pageable: Pageable) : Page<UserDto.Res> {
        return userRepository.findAll(pageable).map{ UserDto.Res(it) }
    }
}