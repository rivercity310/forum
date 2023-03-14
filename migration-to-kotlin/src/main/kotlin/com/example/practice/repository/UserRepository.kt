package com.example.practice.repository

import com.example.practice.domain.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RestResource

@RestResource
interface UserRepository : JpaRepository<UserAccount, Long> {
    fun findByUserId(userId : String) : UserAccount?
}